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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.bfh.backio.R;
import ch.bfh.backio.services.SensorService;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;

/**
 * A placeholder fragment containing a simple view.
 */
public class DeviceSetupActivityFragment extends Fragment implements ServiceConnection {
	public interface FragmentSettings {
		BluetoothDevice getBtDevice();
	}

	private MetaWearBoard metawear = null;
	private FragmentSettings settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SensorService.setService(getActivity());

		Activity owner = getActivity();
		if (!(owner instanceof FragmentSettings)) {
			throw new ClassCastException("Owning activity must implement the FragmentSettings interface");
		}

		settings = (FragmentSettings) owner;
		owner.getApplicationContext().bindService(new Intent(owner, BtleService.class), this, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().getApplicationContext().unbindService(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRetainInstance(true);
		return inflater.inflate(R.layout.fragment_device_setup, container, false);
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		metawear = ((BtleService.LocalBinder) service).getMetaWearBoard(settings.getBtDevice());
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
	}

	/**
	 * Called when the app has reconnected to the board
	 */
	public void reconnected() {
	}
}