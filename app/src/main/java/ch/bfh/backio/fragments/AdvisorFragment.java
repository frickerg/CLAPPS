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

/**
 * TODO: The Class AdvisorFragment.
 *
 * Description here...
 */
public class AdvisorFragment extends Fragment {

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
		return inflater.inflate(R.layout.fragment_advisor, container, false);
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
