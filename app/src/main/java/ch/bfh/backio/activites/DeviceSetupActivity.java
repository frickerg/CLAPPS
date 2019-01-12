package ch.bfh.backio.activites;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ch.bfh.backio.fragments.DeviceSetupActivityFragment;
import ch.bfh.backio.R;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;
import ch.bfh.backio.fragments.DeviceSetupActivityFragment.FragmentSettings;

import bolts.Continuation;

import static android.content.DialogInterface.*;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceSetupActivity.
 */
public class DeviceSetupActivity extends AppCompatActivity implements ServiceConnection, FragmentSettings {
	
	/** The Constant EXTRA_BT_DEVICE. */
	public final static String EXTRA_BT_DEVICE = "com.mbientlab.metawear.starter.DeviceSetupActivity.EXTRA_BT_DEVICE";

	/**
	 * The Class ReconnectDialogFragment.
	 */
	public static class ReconnectDialogFragment extends DialogFragment implements ServiceConnection {
		
		/** The Constant KEY_BLUETOOTH_DEVICE. */
		private static final String KEY_BLUETOOTH_DEVICE = "com.mbientlab.metawear.starter.DeviceSetupActivity.ReconnectDialogFragment.KEY_BLUETOOTH_DEVICE";

		/** The reconnect dialog. */
		private ProgressDialog reconnectDialog = null;
		
		/** The bt device. */
		private BluetoothDevice btDevice = null;
		
		/** The current mw board. */
		private MetaWearBoard currentMwBoard = null;

		/**
		 * New instance.
		 *
		 * @param btDevice the bt device
		 * @return the reconnect dialog fragment
		 */
		public static ReconnectDialogFragment newInstance(BluetoothDevice btDevice) {
			Bundle args = new Bundle();
			args.putParcelable(KEY_BLUETOOTH_DEVICE, btDevice);

			ReconnectDialogFragment newFragment = new ReconnectDialogFragment();
			newFragment.setArguments(args);

			return newFragment;
		}

		/**
		 * On create dialog.
		 *
		 * @param savedInstanceState the saved instance state
		 * @return the dialog
		 */
		@NonNull
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			btDevice = getArguments().getParcelable(KEY_BLUETOOTH_DEVICE);
			getActivity().getApplicationContext().bindService(new Intent(getActivity(), BtleService.class), this, BIND_AUTO_CREATE);

			reconnectDialog = new ProgressDialog(getActivity());
			reconnectDialog.setTitle(getString(R.string.title_reconnect_attempt));
			reconnectDialog.setMessage(getString(R.string.message_wait));
			reconnectDialog.setCancelable(false);
			reconnectDialog.setCanceledOnTouchOutside(false);
			reconnectDialog.setIndeterminate(true);
			reconnectDialog.setButton(BUTTON_NEGATIVE, getString(android.R.string.cancel), (dialogInterface, i) -> {
				currentMwBoard.disconnectAsync();
				getActivity().finish();
			});

			return reconnectDialog;
		}

		/**
		 * On service connected.
		 *
		 * @param name the name
		 * @param service the service
		 */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			currentMwBoard = ((BtleService.LocalBinder) service).getMetaWearBoard(btDevice);
		}

		/**
		 * On service disconnected.
		 *
		 * @param name the name
		 */
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	}

	/** The bt device. */
	private BluetoothDevice btDevice;
	
	/** The metawear. */
	private MetaWearBoard metawear;

	/** The reconnect dialog tag. */
	private final String RECONNECT_DIALOG_TAG = "reconnect_dialog_tag";

	/**
	 * On create.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_setup);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		btDevice = getIntent().getParcelableExtra(EXTRA_BT_DEVICE);
		getApplicationContext().bindService(new Intent(this, BtleService.class), this, BIND_AUTO_CREATE);
	}

	/**
	 * On create options menu.
	 *
	 * @param menu the menu
	 * @return true, if successful
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_device_setup, menu);
		return true;
	}

	/**
	 * On options item selected.
	 *
	 * @param item the item
	 * @return true, if successful
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_disconnect:
				metawear.disconnectAsync();
				finish();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * On back pressed.
	 */
	@Override
	public void onBackPressed() {
		metawear.disconnectAsync();
		super.onBackPressed();
	}

	/**
	 * On service connected.
	 *
	 * @param name the name
	 * @param service the service
	 */
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		metawear = ((BtleService.LocalBinder) service).getMetaWearBoard(btDevice);
		metawear.onUnexpectedDisconnect(status -> {
			ReconnectDialogFragment dialogFragment = ReconnectDialogFragment.newInstance(btDevice);
			dialogFragment.show(getSupportFragmentManager(), RECONNECT_DIALOG_TAG);

			metawear.connectAsync().continueWithTask(task -> task.isCancelled() || !task.isFaulted() ? task : MainActivity.reconnect(metawear))
				.continueWith((Continuation<Void, Void>) task -> {
					if (!task.isCancelled()) {
						runOnUiThread(() -> {
							((DialogFragment) getSupportFragmentManager().findFragmentByTag(RECONNECT_DIALOG_TAG)).dismiss();
							((DeviceSetupActivityFragment) getSupportFragmentManager().findFragmentById(R.id.device_setup_fragment)).reconnected();
						});
					} else {
						finish();
					}

					return null;
				});
		});
	}

	/**
	 * On service disconnected.
	 *
	 * @param name the name
	 */
	@Override
	public void onServiceDisconnected(ComponentName name) {
	}

	/* (non-Javadoc)
	 * @see ch.bfh.backio.fragments.DeviceSetupActivityFragment.FragmentSettings#getBtDevice()
	 */
	@Override
	public BluetoothDevice getBtDevice() {
		return btDevice;
	}
}
