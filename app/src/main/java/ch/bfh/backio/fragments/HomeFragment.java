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
import android.widget.ImageView;
import ch.bfh.backio.R;

/**
 * TODO: The Class HomeFragment.
 *
 * Description here...
 */
public class HomeFragment extends Fragment {

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
		return inflater.inflate(R.layout.fragment_home, container, false);
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
	}

}
