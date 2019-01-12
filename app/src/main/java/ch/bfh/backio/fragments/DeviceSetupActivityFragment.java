package ch.bfh.backio.fragments;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.bfh.backio.R;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;

// TODO: Auto-generated Javadoc
/**
 * A placeholder fragment containing a simple view.
 */
public class DeviceSetupActivityFragment extends Fragment implements ServiceConnection {
	
	/**
	 * The Interface FragmentSettings.
	 */
	public interface FragmentSettings {
		
		/**
		 * Gets the bt device.
		 *
		 * @return the bt device
		 */
		BluetoothDevice getBtDevice();
	}

	/** The metawear. */
	private MetaWearBoard metawear = null;
	
	/** The settings. */
	private FragmentSettings settings;

	/**
	 * Instantiates a new device setup activity fragment.
	 */
	public DeviceSetupActivityFragment() {
	}

	/**
	 * On create.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Activity owner = getActivity();
		if (!(owner instanceof FragmentSettings)) {
			throw new ClassCastException("Owning activity must implement the FragmentSettings interface");
		}

		settings = (FragmentSettings) owner;
		owner.getApplicationContext().bindService(new Intent(owner, BtleService.class), this, Context.BIND_AUTO_CREATE);
	}

	/**
	 * On destroy.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().getApplicationContext().unbindService(this);
	}

	/**
	 * On create view.
	 *
	 * @param inflater the inflater
	 * @param container the container
	 * @param savedInstanceState the saved instance state
	 * @return the view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRetainInstance(true);
		return inflater.inflate(R.layout.fragment_device_setup, container, false);
	}

	/**
	 * On service connected.
	 *
	 * @param name the name
	 * @param service the service
	 */
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		metawear = ((BtleService.LocalBinder) service).getMetaWearBoard(settings.getBtDevice());
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
	 * Called when the app has reconnected to the board.
	 */
	public void reconnected() {
	}
}
