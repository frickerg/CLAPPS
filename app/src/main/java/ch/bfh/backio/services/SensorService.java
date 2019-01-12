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

import java.util.Date;
import java.util.List;

import static ch.bfh.backio.services.persistence.utils.Converters.dateToTimestamp;

/**
 * The Sensor Service offers all methods to interact with the mbientlab sensor.
 */
public class SensorService {
	private MetaWearBoard board;
	private Led led;
	private AccelerometerBmi160 acc;

	// initialize database for sensor values
	private AppDatabase dbValues;

	//Posture evaluation
	private int evaluateCounter = 0;
	private boolean initPosture = true;
	private float initX;
	private float xTreshold;

	public SensorService(Context ctxt) {
		dbValues = AppDatabase.getAppDatabase(ctxt);
	}

	/**
	 * Tries to build up a connection to the given MAC-Address of the mbientlab sensor via Bluetooth.
	 * When the connection is open, the gyroscope send with a frequency of 200 Hz data of the sensor.
	 */
	public MetaWearBoard retrieveBoard(BluetoothDevice btDevice, BtleService.LocalBinder serviceBinder) {
		//final BluetoothManager btManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

		// the other one is in MainActivity and should absolutely stay there!
		board = serviceBinder.getMetaWearBoard(btDevice);
		board.connectAsync().onSuccessTask(task -> {
			acc = board.getModule(AccelerometerBmi160.class);
			acc.configure()
				.odr(OutputDataRate.ODR_50_HZ)
				.range(AccelerometerBosch.AccRange.AR_2G)
				.commit();

			return acc.acceleration().addRouteAsync(new RouteBuilder() {
				@Override
				public void configure(RouteComponent source) {
					source.stream(new Subscriber() {
						@Override
						public void apply(Data data, Object... env) {

							float x = data.value(Acceleration.class).x();
							String ts = data.formattedTimestamp();

							Log.d("SensorData: ", "X: " + x + " , Timestamp: " + ts);

							if (initPosture) {
								initializePosture(x);
							}
							evaluatePosition(x);

							saveData(5, x);

							List<Value> valueList = dbValues.valueDao().getAll();
							Log.d("DBData: ", "Rows Count: " + valueList.size());
						}
					});
				}
			});
		}).continueWith(new Continuation<Route, Void>() {
			@Override
			public Void then(Task<Route> task) throws Exception {
				if (task.isFaulted()) {
					Log.d("SensorService: ", "Failed to connect");
					return null;
				} else {
					acc.start();
					acc.acceleration().start();
					playLed();
					Log.d("SensorService: ", "Connected");
				}
				return null;
			}
		});
		return board;
	}

	/**
	 * Close the connection to the mbientlab Sensor
	 */
	public void disconnectSensor() {
		board.disconnectAsync().continueWith(task -> {
			Log.d("SensorService: ", "Disconnected");
			return null;
		});
	}

	/**
	 * Gets the Led Module from the sensor and lights up green 3 times.
	 */
	private void playLed() {
		led = board.getModule(Led.class);
		led.editPattern(Led.Color.GREEN);
		led.play();
	}

	/**
	 * This method evaluates on every reaction of the sensor if the position changed by more than 25%.
	 */
	private void evaluatePosition(float x) {
		if (evaluateCounter == 300) {
			vibrate();
			evaluateCounter = 0;
		} else {
			if (x < (x - xTreshold)) {
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
		initX = x;
		xTreshold = (initX / 100) * 25;
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

		if (value < (value - xTreshold)) {
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
}
