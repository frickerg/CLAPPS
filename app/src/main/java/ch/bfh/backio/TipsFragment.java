package ch.bfh.backio;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class TipsFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tips, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		final Button tipButton =  getView().findViewById(R.id.btn_tip);
		final Button exerciseButton = getView().findViewById(R.id.btn_exercise);
		final Button awarenessButton = getView().findViewById(R.id.btn_awarness);

		tipButton.setOnClickListener((v -> {
			Class destinationActivity = TipActivity.class;
			Intent startTipActivityIntent = new Intent(this.getContext(), destinationActivity);
			startActivity(startTipActivityIntent);
			String message = "Button clicked!\nTip";
			Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
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
