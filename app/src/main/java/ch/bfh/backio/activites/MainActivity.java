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
import ch.bfh.backio.services.persistence.database.AppDatabase;
import ch.bfh.backio.services.persistence.utils.DatabaseInitializer;
import ch.bfh.backio.services.SensorService;
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

public class MainActivity extends AppCompatActivity implements BleScannerFragment.ScannerCommunicationBus, ServiceConnection {
	private static final int REQUEST_START_APP = 1;
	private static BtleService.LocalBinder serviceBinder;
	private MetaWearBoard metawear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getApplicationContext().bindService(new Intent(this, BtleService.class), this, BIND_AUTO_CREATE);

		Context context = MainActivity.this;
		SensorService.setService(context);

		findViewById(btn_diary).setOnClickListener(v -> {
			String message = "Button clicked!\nTagebuch";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new DiaryFragment()).commit();
			setButton(findViewById(btn_diary_image), ic_import_contacts_green_24dp);
		});

		findViewById(R.id.btn_tips).setOnClickListener((v -> {
			String message = "Button clicked!\nTipps";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new AdvisorFragment()).commit();
			setButton(findViewById(btn_tips_image), ic_content_paste_green_24dp);
		}));

		findViewById(R.id.btn_sensor).setOnClickListener((v -> {
			String message = "Button clicked!\nSensor";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new SensorFragment()).commit();
			setButton(findViewById(btn_sensor_image), ic_bluetooth_green_24dp);
		}));

		findViewById(R.id.btn_home).setOnClickListener((v -> {
			String message = "Button clicked!\nHome";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new HomeFragment()).commit();
			setButton(findViewById(btn_home_image), ic_home_green_24dp);
			//DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(this));
		}));


	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Unbind the service when the activity is destroyed
		getApplicationContext().unbindService(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_START_APP:
				System.out.println("hoi");
				//((BleScannerFagment) getFragmentManager().findFragmentById(R.id.scanner_fragment)).startBleScan();
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	public void setButton(ImageButton clickedButton, int newImage) {
		ImageButton btnDiary = findViewById(btn_diary_image);
		btnDiary.setImageResource(ic_import_contacts_black_24dp);

		ImageButton btnHome = findViewById(R.id.btn_home_image);
		btnHome.setImageResource(ic_home_black_24dp);

		ImageButton btnSensor = findViewById(R.id.btn_sensor_image);
		btnSensor.setImageResource(ic_bluetooth_black_24dp);

		ImageButton btnTips = findViewById(R.id.btn_tips_image);
		btnTips.setImageResource(ic_content_paste_black_24dp);

		clickedButton.setImageResource(newImage);
	}

	@Override
	public UUID[] getFilterServiceUuids() {
		return new UUID[]{MetaWearBoard.METAWEAR_GATT_SERVICE};
	}

	@Override
	public long getScanDuration() {
		return 10000L;
	}

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
					Intent navActivityIntent = new Intent(MainActivity.this, DeviceSetupActivity.class);
					navActivityIntent.putExtra(DeviceSetupActivity.EXTRA_BT_DEVICE, device);
					startActivityForResult(navActivityIntent, REQUEST_START_APP);
				}
				return task;
			}).onSuccessTask(task -> {
			System.out.println("success task");
			metawear = SensorService.retrieveBoard(device, serviceBinder);
			return task;
		});
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		serviceBinder = (BtleService.LocalBinder) service;
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {

	}

	public static Task<Void> reconnect(final MetaWearBoard board) {
		return board.connectAsync().continueWithTask(task -> task.isFaulted() ? reconnect(board) : task);
	}
}