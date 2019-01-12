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
import ch.bfh.backio.fragments.HomeFragment;
import ch.bfh.backio.R;
import ch.bfh.backio.fragments.SensorFragment;
import ch.bfh.backio.fragments.AdvisorFragment;
import ch.bfh.backio.fragments.DiaryFragment;
import com.mbientlab.bletoolbox.scanner.BleScannerFragment;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;

import java.util.UUID;

import static ch.bfh.backio.R.drawable.ic_import_contacts_black_24dp;
import static ch.bfh.backio.R.drawable.ic_home_black_24dp;
import static ch.bfh.backio.R.drawable.ic_content_paste_black_24dp;
import static ch.bfh.backio.R.drawable.ic_bluetooth_black_24dp;
import static ch.bfh.backio.R.drawable.ic_import_contacts_green_24dp;
import static ch.bfh.backio.R.drawable.ic_home_green_24dp;
import static ch.bfh.backio.R.drawable.ic_content_paste_green_24dp;
import static ch.bfh.backio.R.drawable.ic_bluetooth_green_24dp;
import static ch.bfh.backio.R.id.*;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends AppCompatActivity implements BleScannerFragment.ScannerCommunicationBus, ServiceConnection {
	
	/** The Constant REQUEST_START_APP. */
	public static final int REQUEST_START_APP = 1;
	
	/** The service binder. */
	private BtleService.LocalBinder serviceBinder;
	
	/** The metawear. */
	private MetaWearBoard metawear;

	/**
	 * On create.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getApplicationContext().bindService(new Intent(this, BtleService.class), this, BIND_AUTO_CREATE);

		Context context = MainActivity.this;
		findViewById(btn_diary).setOnClickListener(v -> {
			String message = "Button clicked!\nTagebuch";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new DiaryFragment()).commit();
			int id = ic_import_contacts_green_24dp;
			setButton((ImageButton) findViewById(btn_diary_image), id);
		});

		findViewById(R.id.btn_tips).setOnClickListener((v -> {
			String message = "Button clicked!\nTipps";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new AdvisorFragment()).commit();
			int id = ic_content_paste_green_24dp;
			setButton((ImageButton) findViewById(btn_tips_image), id);
		}));

		findViewById(R.id.btn_sensor).setOnClickListener((v -> {
			String message = "Button clicked!\nSensor";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new SensorFragment()).commit();
			int id = ic_bluetooth_green_24dp;
			setButton((ImageButton) findViewById(btn_sensor_image), id);
		}));

		findViewById(R.id.btn_home).setOnClickListener((v -> {
			String message = "Button clicked!\nHome";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new HomeFragment()).commit();
			int id = ic_home_green_24dp;
			setButton((ImageButton) findViewById(btn_home_image), id);
		}));


	}

	/**
	 * On destroy.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();

		///< Unbind the service when the activity is destroyed
		getApplicationContext().unbindService(this);
	}

	/**
	 * On activity result.
	 *
	 * @param requestCode the request code
	 * @param resultCode the result code
	 * @param data the data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_START_APP:
				//((BleScannerFagment) getFragmentManager().findFragmentById(R.id.scanner_fragment)).startBleScan();

				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	/**
	 * Sets the button.
	 *
	 * @param clickedButton the clicked button
	 * @param newImage the new image
	 */
	public void setButton(ImageButton clickedButton, int newImage) {

		ImageButton btnDiary = (ImageButton) findViewById(btn_diary_image);
		btnDiary.setImageResource(ic_import_contacts_black_24dp);

		ImageButton btnHome = (ImageButton) findViewById(R.id.btn_home_image);
		btnHome.setImageResource(ic_home_black_24dp);

		ImageButton btnSensor = (ImageButton) findViewById(R.id.btn_sensor_image);
		btnSensor.setImageResource(ic_bluetooth_black_24dp);

		ImageButton btnTips = (ImageButton) findViewById(R.id.btn_tips_image);
		btnTips.setImageResource(ic_content_paste_black_24dp);

		clickedButton.setImageResource(newImage);

	}

	/**
	 * Gets the filter service uuids.
	 *
	 * @return the filter service uuids
	 */
	@Override
	public UUID[] getFilterServiceUuids() {
		return new UUID[]{MetaWearBoard.METAWEAR_GATT_SERVICE};
	}

	/**
	 * Gets the scan duration.
	 *
	 * @return the scan duration
	 */
	@Override
	public long getScanDuration() {
		return 10000L;
	}

	/**
	 * On device selected.
	 *
	 * @param device the device
	 */
	@Override
	public void onDeviceSelected(final BluetoothDevice device) {
		metawear = serviceBinder.getMetaWearBoard(device);

		final ProgressDialog connectDialog = new ProgressDialog(this);
		connectDialog.setTitle(getString(R.string.title_connecting));
		connectDialog.setMessage(getString(R.string.message_wait));
		connectDialog.setCancelable(false);
		connectDialog.setCanceledOnTouchOutside(false);
		connectDialog.setIndeterminate(true);
		connectDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), (dialogInterface, i) -> metawear.disconnectAsync());
		connectDialog.show();

		metawear.connectAsync().continueWithTask(task -> task.isCancelled() || !task.isFaulted() ? task : reconnect(metawear))
			.continueWith(task -> {
				if (!task.isCancelled()) {
					runOnUiThread(connectDialog::dismiss);
				}
				return null;
			});
	}

	/**
	 * On service connected.
	 *
	 * @param name the name
	 * @param service the service
	 */
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		serviceBinder = (BtleService.LocalBinder) service;
	}

	/**
	 * On service disconnected.
	 *
	 * @param name the name
	 */
	@Override
	public void onServiceDisconnected(ComponentName name) {

	}

	/**
	 * Reconnect.
	 *
	 * @param board the board
	 * @return the task
	 */
	public static Task<Void> reconnect(final MetaWearBoard board) {
		return board.connectAsync().continueWithTask(task -> task.isFaulted() ? reconnect(board) : task);
	}

}
