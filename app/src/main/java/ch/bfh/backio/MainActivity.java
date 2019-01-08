package ch.bfh.backio;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.*;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import bolts.Task;
import com.mbientlab.bletoolbox.scanner.BleScannerFragment;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements BleScannerFragment.ScannerCommunicationBus, ServiceConnection {
	private static final int REQUEST_START_APP = 1;
	private BtleService.LocalBinder serviceBinder;
	private MetaWearBoard metawear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getApplicationContext().bindService(new Intent(this, BtleService.class), this, BIND_AUTO_CREATE);

		Context context = MainActivity.this;
		findViewById(R.id.btn_diary).setOnClickListener((v -> {
			String message = "Button clicked!\nTagebuch";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new DiaryFragment()).commit();
		}));

		findViewById(R.id.btn_tips).setOnClickListener((v -> {
			String message = "Button clicked!\nTipps";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new TipsFragment()).commit();
		}));

		findViewById(R.id.btn_sensor).setOnClickListener((v -> {
			String message = "Button clicked!\nSensor";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new SensorFragment()).commit();
		}));

		findViewById(R.id.btn_home).setOnClickListener((v -> {
			String message = "Button clicked!\nHome";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new HomeFragment()).commit();
		}));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		///< Unbind the service when the activity is destroyed
		getApplicationContext().unbindService(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_START_APP) {
			((BleScannerFragment) getFragmentManager().findFragmentById(R.id.scanner_fragment)).startBleScan();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public UUID[] getFilterServiceUuids() {
		return new UUID[] {MetaWearBoard.METAWEAR_GATT_SERVICE};
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
				return null;
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
