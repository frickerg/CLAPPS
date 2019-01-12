package ch.bfh.backio.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import ch.bfh.backio.R;

// TODO: Auto-generated Javadoc
/**
 * The Class AdvisorFragment.
 */
public class AdvisorFragment extends Fragment {
	
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
		return inflater.inflate(R.layout.fragment_advisor, container, false);
	}

	/**
	 * On view created.
	 *
	 * @param view the view
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		final Button tipButton = getView().findViewById(R.id.btn_tip);
		final Button exerciseButton = getView().findViewById(R.id.btn_exercise);
		final Button awarenessButton = getView().findViewById(R.id.btn_awarness);

		tipButton.setOnClickListener((v -> {
			String message = "Button clicked!\nTip";
			Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new TipFragment()).commit();
		}));

		exerciseButton.setOnClickListener((v -> {
			String message = "Button clicked!\nÜbungen";
			Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new ExerciseFragment()).commit();
		}));

		awarenessButton.setOnClickListener((v -> {
			String message = "Button clicked!\nAufklärung";
			Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new AwarenessFragment()).commit();
		}));
	}
}
