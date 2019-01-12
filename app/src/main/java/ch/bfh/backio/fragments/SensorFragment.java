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

// TODO: Auto-generated Javadoc
/**
 * The Class SensorFragment.
 */
public class SensorFragment extends Fragment {
	
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
		return inflater.inflate(R.layout.fragment_sensor, container, false);
	}
}
