package ch.bfh.backio.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ch.bfh.backio.R;
import ch.bfh.backio.services.SensorServiceSingleton;

/**
 * TODO: The Class SensorFragment.
 *
 * Description here...
 */
public class SensorConnectedFragment extends Fragment {
	private SensorServiceSingleton sensorServiceSingleton = SensorServiceSingleton.getInstance();

	/**
	 * Called to have the fragment instantiate its user interface view.
	 *
	 * @param inflater           The LayoutInflater object that can be used to inflate
	 *                           any views in the fragment,
	 * @param container          If non-null, this is the parent view that the fragment's
	 *                           UI should be attached to.  The fragment should not add the view itself,
	 *                           but this can be used to generate the LayoutParams of the view.
	 * @param savedInstanceState If non-null, this fragment is being re-constructed
	 *                           from a previous saved state as given here.
	 * @return Return the View for the fragment's UI, or null.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_sensor_connected, container, false);
	}

	/**
	 * Called immediately after onCreateView has returned,
	 * but before any saved state has been restored in to the view.
	 * This gives subclasses a chance to initialize themselves
	 * once they know their view hierarchy has been completely created.
	 * The fragment's view hierarchy is not however attached to its parent at this point.
	 *
	 * @param view               The View returned by onCreateView
	 * @param savedInstanceState If non-null, this fragment is being re-constructed
	 *                           from a previous saved state as given here.
	 */
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
			final ProgressDialog disconnectDialog = createDisconnectDialog();
			disconnectDialog.show();

			sensorServiceSingleton.disconnectSensor().onSuccess(task -> {
				getActivity().runOnUiThread(disconnectDialog::dismiss);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(android.R.id.content, new SensorFragment()).commit();
				return null;
			});
		}));
	}

	/**
	 * Creates the progress dialog when disconnecting from the metawear device.
	 *
	 * @return the created progress dialog
	 */
	private ProgressDialog createDisconnectDialog() {
		ProgressDialog disconnectDialog = new ProgressDialog(getContext());
		disconnectDialog.setTitle(getString(R.string.title_disconnecting));
		disconnectDialog.setMessage(getString(R.string.message_wait));
		disconnectDialog.setCancelable(false);
		disconnectDialog.setCanceledOnTouchOutside(false);
		disconnectDialog.setIndeterminate(true);
		return disconnectDialog;
	}
}
