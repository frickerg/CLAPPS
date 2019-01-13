package ch.bfh.backio.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ch.bfh.backio.R;
import ch.bfh.backio.services.SensorServiceSingleton;

// TODO: Auto-generated Javadoc

/**
 * The Class SensorFragment.
 */
public class SensorConnectedFragment extends Fragment {
	private SensorServiceSingleton sensorServiceSingleton = SensorServiceSingleton.getInstance();

	/**
	 * On create view.
	 *
	 * @param inflater           the inflater
	 * @param container          the container
	 * @param savedInstanceState the saved instance state
	 * @return the view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_sensor_connected, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		final TextView macAddressTextView = getActivity().findViewById(R.id.value_mac_address);
		final TextView sensorNameTextView = getActivity().findViewById(R.id.value_device_name);
		final TextView connectionTextView = getActivity().findViewById(R.id.value_status_connect);

		macAddressTextView.setText(sensorServiceSingleton.getDevice().getAddress());
		sensorNameTextView.setText(sensorServiceSingleton.getDevice().getName());
		connectionTextView.setText(R.string.status_connected);

		final Button disconnectButton = getView().findViewById(R.id.btn_disconnect);

		disconnectButton.setOnClickListener((v -> {
			String message = sensorServiceSingleton.getDevice().getAddress();

			sensorServiceSingleton.disconnectSensor();
			Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new SensorFragment()).commit();
		}));
	}

}
