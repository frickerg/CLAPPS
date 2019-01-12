package ch.bfh.backio.fragments;

import android.app.Fragment;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import ch.bfh.backio.R;
import ch.bfh.backio.services.SensorService;

public class HomeFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		final Button sensorConnectButton = getView().findViewById(R.id.btn_connectSensor);
		final ImageView img_posture = getView().findViewById(R.id.img_posture);
		final AnimatedVectorDrawable img_posture_drawable = (AnimatedVectorDrawable) ContextCompat.getDrawable(getContext(), R.drawable.haltung_posa_to_posb);

		img_posture.setImageDrawable(img_posture_drawable);
		img_posture_drawable.registerAnimationCallback(new Animatable2.AnimationCallback() {
			@Override
			public void onAnimationEnd(Drawable drawable) {
				img_posture_drawable.start();
			}
		});

		img_posture_drawable.start();

		sensorConnectButton.setOnClickListener((v -> {

			// initialize new sensor object
			// use getActivity instead of this
			SensorService metaSensor = new SensorService(getActivity());

			// metaSensor.disconnectSensor();

		} ));




	}

}
