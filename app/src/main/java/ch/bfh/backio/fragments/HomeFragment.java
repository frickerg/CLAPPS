package ch.bfh.backio.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import ch.bfh.backio.R;
import ch.bfh.backio.services.SensorService;

public class HomeFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		final Button sensorConnectButton = getView().findViewById(R.id.btn_connectSensor);

		sensorConnectButton.setOnClickListener((v -> {

			// initialize new sensor object
			// use getActivity instead of this
			SensorService.setService(getActivity());

			// metaSensor.disconnectSensor();

		} ));
	}

}
