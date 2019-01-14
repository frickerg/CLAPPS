package ch.bfh.backio.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ch.bfh.backio.R;
import ch.bfh.backio.activites.ExerciseDetailActivity;
import ch.bfh.backio.activites.TipDetailActivity;
import ch.bfh.backio.services.JSONBroker;

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

		TextView dailyTipText = getView().findViewById(R.id.daily_tip_text);
		TextView dailyExerciseText = getView().findViewById(R.id.daily_exercise_text);
		ImageView dailyTipImg = getView().findViewById(R.id.daily_tip_img);
		ImageView dailyExerciseImg = getView().findViewById(R.id.daily_exercise_img);
		LinearLayout dailyTip = getView().findViewById(R.id.daily_tip);
		LinearLayout dailyExercise = getView().findViewById(R.id.daily_exercise);

		dailyTip.setOnClickListener((v -> {
			String text = "Stuhl einstellen";
			try {
				JSONObject jsonObject = JSONBroker.retrieveJsonObject(getActivity().getAssets(), "tip.json", "tip", text);
				dailyTipText.setText(text);
				Bitmap img1 = BitmapFactory.decodeStream(getActivity().getAssets().open(jsonObject.getString("img")));
				dailyTipImg.setImageBitmap(img1);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
			}

			Class destinationClass = TipDetailActivity.class;
			Intent intentToStartDetailActivity = new Intent(getActivity(), destinationClass);
			intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, text);
			startActivity(intentToStartDetailActivity);
		}));

		dailyExercise.setOnClickListener((v -> {
			String text = "Drehsitz";
			try {
				JSONObject jsonObject = JSONBroker.retrieveJsonObject(getActivity().getAssets(), "exercise.json", "exercise", text);
				dailyExerciseText.setText(text);
				Bitmap img1 = BitmapFactory.decodeStream(getActivity().getAssets().open(jsonObject.getString("img")));
				dailyExerciseImg.setImageBitmap(img1);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
			}

			Class destinationClass = ExerciseDetailActivity.class;
			Intent intentToStartDetailActivity = new Intent(getActivity(), destinationClass);
			intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, text);
			startActivity(intentToStartDetailActivity);
		}));


		//Context context = HomeFragment.this;
	}

}
