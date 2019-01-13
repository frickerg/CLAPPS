package ch.bfh.backio.services;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import bolts.Continuation;
import bolts.Task;
import ch.bfh.backio.services.persistence.database.AppDatabase;
import ch.bfh.backio.services.persistence.entity.Value;
import com.mbientlab.metawear.Data;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.Route;
import com.mbientlab.metawear.Subscriber;
import com.mbientlab.metawear.android.BtleService;
import com.mbientlab.metawear.builder.RouteBuilder;
import com.mbientlab.metawear.builder.RouteComponent;
import com.mbientlab.metawear.data.Acceleration;
import com.mbientlab.metawear.module.AccelerometerBmi160;
import com.mbientlab.metawear.module.AccelerometerBmi160.OutputDataRate;
import com.mbientlab.metawear.module.AccelerometerBosch;
import com.mbientlab.metawear.module.Haptic;
import com.mbientlab.metawear.module.Led;
import com.mbientlab.metawear.module.AccelerometerBosch.NoMotionDataProducer;


import java.util.Date;
import java.util.List;

import static ch.bfh.backio.services.persistence.utils.Converters.dateToTimestamp;

// TODO: Auto-generated Javadoc

/**
 * The Sensor Service offers all methods to interact with the mbientlab sensor.
 */
public class SensorServiceSingleton {
	private static final SensorServiceSingleton instance = new SensorServiceSingleton();
	private MetaWearBoard board;
	private BluetoothDevice device;
	private AccelerometerBmi160 acc;
	private AppDatabase dbValues;
	private int evaluateCounter = 0;
	private boolean initPosture = true;
	private float xThreshold;

	/**
	 * Instantiates a new sensor service.
	 *
	 * @param ctxt the ctxt
	 */
	public void initializeServiceWithContext(Context ctxt) {
		dbValues = AppDatabase.getAppDatabase(ctxt);
	}

	private SensorServiceSingleton() {
	}

	public static SensorServiceSingleton getInstance() {
		return instance;
	}

	/**
	 * Tries to build up a connection to the given MAC-Address of the mbientlab sensor via Bluetooth.
	 * When the connection is open, the gyroscope send with a frequency of 200 Hz data of the sensor.
	 *
	 * @param btDevice      the bt device
	 * @param serviceBinder the service binder
	 * @return the meta wear board
	 */
	public MetaWearBoard retrieveBoard(BluetoothDevice btDevice, BtleService.LocalBinder serviceBinder) {
		//final BluetoothManager btManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
		this.device = btDevice;
		// the other one is in MainActivity and should absolutely stay there!
		board = serviceBinder.getMetaWearBoard(btDevice);
		board.connectAsync().onSuccessTask(connectAsyncTask -> {
			// get and configure BMI160 accelerometer
			acc = board.getModule(AccelerometerBmi160.class);
			acc.configure()
				.odr(OutputDataRate.ODR_50_HZ)
				.range(AccelerometerBosch.AccRange.AR_2G)
				.commit();

			// get and configure Bosch accelerometer
			AccelerometerBosch accBosch = board.getModule(AccelerometerBosch.class);
			final NoMotionDataProducer noMotion = accBosch.motion(NoMotionDataProducer.class);
			noMotion.configure()
				.duration(10000)
				.threshold(0.1f)
				.commit();

			// data route for Bosch accelerometer
			noMotion.addRouteAsync((RouteComponent source) -> source.stream((Data data, Object... env) -> {
				Log.i("MainActivity", "No motion detected");
				// write to db that no motion was detected
				// value 1 for "no motion"
				// parameter 1 is motion detection
				saveData(1, 1f);
			})).continueWith((Task<Route> routeTask) -> {
				noMotion.start();
				accBosch.start();
				return null;
			});
			// data route for BMI160 accelerometer
			return acc.acceleration().addRouteAsync(this::configure);
		}).continueWith((Task<Route> addedRouteTask) -> {
			if (addedRouteTask.isFaulted()) {
				Log.d("SensorServiceSingleton: ", "Failed to connect");
				return null;
			} else {
				acc.start();
				acc.acceleration().start();
				playLed();
				Log.d("SensorServiceSingleton: ", "Connected");
			}
			return null;
		});
		return board;
	}

	/**
	 * Close the connection to the mbientlab Sensor.
	 */
	public void disconnectSensor() {
		acc.stop();
		board.disconnectAsync().continueWith(task -> {
			Log.d("SensorServiceSingleton: ", "Disconnected");
			device = null;
			vibrate();
			return null;
		});
	}

	/**
	 * Gets the Led Module from the sensor and lights up green 3 times.
	 */
	private void playLed() {
		Led led = board.getModule(Led.class);
		led.editPattern(Led.Color.GREEN);
		led.play();
	}

	/**
	 * This method evaluates on every reaction of the sensor if the position changed by more than 25%.
	 *
	 * @param x the x
	 */
	private void evaluatePosition(float x) {
		if (evaluateCounter == 300) {
			vibrate();
			evaluateCounter = 0;
		} else {
			if (x < (x - xThreshold)) {
				evaluateCounter++;
			}
		}
	}

	/**
	 * Initializes the posture of the patient with the x axis.
	 *
	 * @param x - X-Axis
	 */
	private void initializePosture(float x) {
		initPosture = false;
		xThreshold = (x / 100) * 25;
	}

	/**
	 * Vibrate the board.
	 */
	private void vibrate() {
		board.getModule(Haptic.class).startBuzzer((short) 500);
	}

	/**
	 * On every sensor reaction the persist method hands the data to the DBController to persist the data in the RPL.
	 *
	 * @param parameter - The parameter
	 * @param value     - The value
	 */
	private void saveData(int parameter, float value) {
		boolean goodPosture;

		if (value < (value - xThreshold)) {
			goodPosture = false;
		} else {
			goodPosture = true;
		}

		new Thread(() -> {
			Value newValue = new Value();
			newValue.setParameter(parameter);
			newValue.setValue(value);
			newValue.setDatetime(dateToTimestamp(new Date()));
			// pd.setPosture(goodPosture);
			dbValues.valueDao().insertAll(newValue);
		}).start();
	}

	public BluetoothDevice getDevice() {
		return this.device;
	}

	private void configure(RouteComponent source) {
		source.stream((Data data, Object... env) -> {
			float x = data.value(Acceleration.class).x();
			String ts = data.formattedTimestamp();

			Log.d("SensorData: ", "X: " + x + " , Timestamp: " + ts);

			if (initPosture) {
				initializePosture(x);
			}
			evaluatePosition(x);

			// parameter 5 is acceleration x axis
			saveData(5, x);

			List<Value> valueList = dbValues.valueDao().getAll();
			Log.d("DBData: ", "Rows Count: " + valueList.size());
		});
	}
}
