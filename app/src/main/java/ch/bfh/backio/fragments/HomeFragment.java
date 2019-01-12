package ch.bfh.backio.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import ch.bfh.backio.R;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeFragment.
 */
public class HomeFragment extends Fragment {
	
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
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	/**
	 * On view created.
	 *
	 * @param view the view
	 * @param savedInstanceState the saved instance state
	 */
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		final Button sensorConnectButton = getView().findViewById(R.id.btn_connectSensor);

		sensorConnectButton.setOnClickListener((v -> {
			//TODO: connection required here?
		} ));
	}

}
