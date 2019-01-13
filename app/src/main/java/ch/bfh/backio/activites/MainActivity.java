package ch.bfh.backio.activites;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.Toast;
import bolts.Task;
import ch.bfh.backio.fragments.*;
import ch.bfh.backio.R;
import ch.bfh.backio.services.SensorServiceSingleton;
import com.mbientlab.bletoolbox.scanner.BleScannerFragment;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;

import java.util.UUID;

/**
 * TODO: The Class MainActivity.
 *
 * Description here...
 */
public class MainActivity extends AppCompatActivity implements BleScannerFragment.ScannerCommunicationBus, ServiceConnection {
	/**
	 * Fragment Tags which are used to prevent the MainActivity from creating too many Fragments
	 */
	private static final String DIARY_FRAGMENT = "DIARY_FRAGMENT";
	private static final String ADVISOR_FRAGMENT = "ADVISOR_FRAGMENT";
	private static final String HOME_FRAGMENT = "HOME_FRAGMENT";
	private static final String SENSOR_FRAGMENT = "SENSOR_FRAGMENT";
	private static final String SENSOR_CONNECTED_FRAGMENT = "SENSOR_CONNECTED_FRAGMENT";

	private SensorServiceSingleton sensorServiceSingleton = SensorServiceSingleton.getInstance();
	private static BtleService.LocalBinder serviceBinder;
	private MetaWearBoard metawear;

	/**
	 * Called when the activity is starting.
	 *
	 * @param savedInstanceState Bundle in which the saved state is saved
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getApplicationContext().bindService(new Intent(this, BtleService.class), this, BIND_AUTO_CREATE);

		Context context = MainActivity.this;
		sensorServiceSingleton.initializeServiceWithContext(context);

		findViewById(R.id.btn_diary).setOnClickListener(v -> {
			DiaryFragment diaryFragment = (DiaryFragment) getFragmentManager().findFragmentByTag(DIARY_FRAGMENT);
			if (diaryFragment == null || !diaryFragment.isVisible()) {
				String message = "Button clicked!\nTagebuch";
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(android.R.id.content, new DiaryFragment(), DIARY_FRAGMENT).commit();
				setButton(findViewById(R.id.btn_diary_image), R.drawable.ic_import_contacts_green_24dp);
			}
		});

		findViewById(R.id.btn_tips).setOnClickListener((v -> {
			AdvisorFragment advisorFragment = (AdvisorFragment) getFragmentManager().findFragmentByTag(ADVISOR_FRAGMENT);
			if (advisorFragment == null || !advisorFragment.isVisible()) {
				String message = "Button clicked!\nTipps";
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(android.R.id.content, new AdvisorFragment(), ADVISOR_FRAGMENT).commit();
				setButton(findViewById(R.id.btn_tips_image), R.drawable.ic_content_paste_green_24dp);
			}
		}));

		findViewById(R.id.btn_sensor).setOnClickListener((v -> {
			SensorFragment sensorFragment = (SensorFragment) getFragmentManager().findFragmentByTag(SENSOR_FRAGMENT);
			SensorConnectedFragment sensorConnectedFragment = (SensorConnectedFragment) getFragmentManager().findFragmentByTag(SENSOR_CONNECTED_FRAGMENT);

			final boolean isSensorFragmentUnfinished = ((sensorFragment == null || !sensorFragment.isVisible()) && !isMetawearConnected());
			final boolean isSensorConnectedFragmentUnfinished = ((sensorConnectedFragment == null || !sensorConnectedFragment.isVisible()) && isMetawearConnected());

			if (isSensorFragmentUnfinished || isSensorConnectedFragmentUnfinished) {
				String message = "Button clicked!\nSensor";
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				if (isSensorFragmentUnfinished) {
					ft.replace(android.R.id.content, new SensorFragment(), SENSOR_FRAGMENT).commit();
				} else {
					ft.replace(android.R.id.content, new SensorConnectedFragment(), SENSOR_CONNECTED_FRAGMENT).commit();
				}
				setButton(findViewById(R.id.btn_sensor_image), R.drawable.ic_bluetooth_green_24dp);
			}
		}));

		findViewById(R.id.btn_home).setOnClickListener((v -> {
			HomeFragment homeFragment = (HomeFragment) getFragmentManager().findFragmentByTag(HOME_FRAGMENT);
			if (homeFragment == null || !homeFragment.isVisible()) {
				String message = "Button clicked!\nHome";
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(android.R.id.content, new HomeFragment(), HOME_FRAGMENT).commit();
				setButton(findViewById(R.id.btn_home_image), R.drawable.ic_home_green_24dp);
				//DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(this));
			}
		}));
	}

	/**
	 * Perform any final cleanup before an activity is destroyed.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		// Unbind the service when the activity is destroyed
		getApplicationContext().unbindService(this);
	}

	/**
	 * TODO: Sets the button.
	 *
	 * @param clickedButton the clicked button
	 * @param newImage      the new image
	 */
	private void setButton(ImageButton clickedButton, int newImage) {
		ImageButton btnDiary = findViewById(R.id.btn_diary_image);
		btnDiary.setImageResource(R.drawable.ic_import_contacts_black_24dp);

		ImageButton btnHome = findViewById(R.id.btn_home_image);
		btnHome.setImageResource(R.drawable.ic_home_black_24dp);

		ImageButton btnSensor = findViewById(R.id.btn_sensor_image);
		btnSensor.setImageResource(R.drawable.ic_bluetooth_black_24dp);

		ImageButton btnTips = findViewById(R.id.btn_tips_image);
		btnTips.setImageResource(R.drawable.ic_content_paste_black_24dp);

		clickedButton.setImageResource(newImage);
	}

	/**
	 * Retrieve an array of allowed service UUIDs.  If no filtering should be done, return null.
	 *
	 * @return Service UUIDs to scan for, null if all discovered devices should be shown
	 */
	@Override
	public UUID[] getFilterServiceUuids() {
		return new UUID[]{MetaWearBoard.METAWEAR_GATT_SERVICE};
	}

	/**
	 * Value that can be returned if users don't want to set their own scan duration value.
	 *
	 * @return the scan duration
	 */
	@Override
	public long getScanDuration() {
		return 10000L;
	}

	/**
	 * Called when the user has selected a Bluetooth device from the device list.
	 *
	 * @param device Device the user selected
	 */
	@Override
	public void onDeviceSelected(final BluetoothDevice device) {
		final ProgressDialog connectDialog = createConnectDialog();
		connectDialog.show();

		metawear = serviceBinder.getMetaWearBoard(device);
		metawear.connectAsync().continueWithTask(task -> task.isCancelled() || !task.isFaulted() ? task : reconnect(metawear))
			.continueWith(task -> {
				if (!task.isCancelled()) {
					runOnUiThread(connectDialog::dismiss);
				}
				return task;
			}).onSuccessTask(task -> {
			System.out.println("success task");
			metawear = sensorServiceSingleton.retrieveBoard(device, serviceBinder);

			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new SensorConnectedFragment(), SENSOR_CONNECTED_FRAGMENT).commit();

			return task;
		});
	}

	/**
	 * Called when a connection to the Service has been established,
	 * with the IBinder of the communication channel to the Service.
	 *
	 * @param name    The concrete component name of the service
	 * @param service The IBinder of the Service's communication channel
	 */
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		serviceBinder = (BtleService.LocalBinder) service;
	}

	/**
	 * Called when a connection to the Service has been lost.
	 *
	 * @param name the name
	 */
	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO: write disconnect handling method
	}

	/**
	 * Creates the progress dialog when connecting to the metawear device.
	 *
	 * @return the created progress dialog
	 */
	private ProgressDialog createConnectDialog() {
		ProgressDialog connectDialog = new ProgressDialog(this);
		connectDialog.setTitle(getString(R.string.title_connecting));
		connectDialog.setMessage(getString(R.string.message_wait));
		connectDialog.setCancelable(false);
		connectDialog.setCanceledOnTouchOutside(false);
		connectDialog.setIndeterminate(true);
		connectDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel),
			(dialogInterface, i) -> metawear.disconnectAsync());
		return connectDialog;
	}

	/**
	 * TODO: Reconnect.
	 *
	 * @param board the board
	 * @return the task
	 */
	private static Task<Void> reconnect(final MetaWearBoard board) {
		return board.connectAsync().continueWithTask(task -> task.isFaulted() ? reconnect(board) : task);
	}

	/**
	 * Checks if the metawear device is connected.
	 *
	 * @return true, if metawear is initialized and connected
	 */
	private boolean isMetawearConnected() {
		return (metawear != null && metawear.isConnected());
	}
}
