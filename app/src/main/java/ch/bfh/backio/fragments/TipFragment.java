package ch.bfh.backio.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import ch.bfh.backio.services.JSONAdapter;
import ch.bfh.backio.R;
import ch.bfh.backio.activites.TipDetailActivity;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class TipFragment.
 */
public class TipFragment extends Fragment implements ch.bfh.backio.services.JSONAdapter.JSONAdapterOnClickHandler {
	
	/** The JSON adapter. */
	private JSONAdapter JSONAdapter = new JSONAdapter(this);

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
		View rootView = inflater.inflate(R.layout.fragment_tip, container, false);
		RecyclerView recyclerView = rootView.findViewById(R.id.rv_tip);

		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(JSONAdapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		ArrayList<String> tipList = JSONAdapter.readJSON(getContext(), "tip.json", "tip", "title");

		for (String tip : tipList) {
			JSONAdapter.setJSONData(tip);
		}
		return rootView;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.backio.services.JSONAdapter.JSONAdapterOnClickHandler#onClick(java.lang.String)
	 */
	@Override
	public void onClick(String tipTitle) {
		Toast.makeText(getContext(), tipTitle, Toast.LENGTH_SHORT).show();
		Class destinationClass = TipDetailActivity.class;
		Intent intentToStartDetailActivity = new Intent(getActivity(), destinationClass);
		intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, tipTitle);
		startActivity(intentToStartDetailActivity);
	}

}
