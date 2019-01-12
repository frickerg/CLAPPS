package ch.bfh.backio.services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.bfh.backio.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class JSONAdapter.
 */
public class JSONAdapter extends RecyclerView.Adapter<JSONAdapter.JSONAdapterViewHolder> {
	
	/** The json data. */
	private ArrayList<String> jsonData = new ArrayList<>();
	
	/** The click. */
	private final JSONAdapterOnClickHandler click;
	
	/** The json list. */
	private ArrayList<String> jsonList = new ArrayList<>();

	/**
	 * The Interface JSONAdapterOnClickHandler.
	 */
	public interface JSONAdapterOnClickHandler {
		
		/**
		 * On click.
		 *
		 * @param json the json
		 */
		void onClick(String json);
	}

	/**
	 * Instantiates a new JSON adapter.
	 *
	 * @param clickHandler the click handler
	 */
	public JSONAdapter(JSONAdapterOnClickHandler clickHandler) {
		click = clickHandler;
	}

	/**
	 * Sets the JSON data.
	 *
	 * @param jsonData the new JSON data
	 */
	public void setJSONData(String jsonData) {
		this.jsonData.add(jsonData);
	}

	/**
	 * The Class JSONAdapterViewHolder.
	 */
	public class JSONAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		/** The json view. */
		public final TextView jsonView;

		/**
		 * Instantiates a new JSON adapter view holder.
		 *
		 * @param view the view
		 */
		public JSONAdapterViewHolder(View view) {
			super(view);
			jsonView = (TextView) view.findViewById(R.id.json_view);
			view.setOnClickListener(this);
		}

		/**
		 * On click.
		 *
		 * @param v the v
		 */
		@Override
		public void onClick(View v) {
			int adapterPosition = getAdapterPosition();
			String jsonSelected = jsonData.get(adapterPosition);
			click.onClick(jsonSelected);
		}
	}

	/**
	 * On create view holder.
	 *
	 * @param viewGroup the view group
	 * @param viewType the view type
	 * @return the JSON adapter view holder
	 */
	@Override
	public JSONAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		Context context = viewGroup.getContext();
		int layoutIdForListItem = R.layout.json_list_item;
		LayoutInflater inflater = LayoutInflater.from(context);
		boolean shouldAttachToParentImmediately = false;

		View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
		return new JSONAdapterViewHolder(view);
	}

	/**
	 * On bind view holder.
	 *
	 * @param JSONAdapterViewHolder the JSON adapter view holder
	 * @param position the position
	 */
	@Override
	public void onBindViewHolder(JSONAdapterViewHolder JSONAdapterViewHolder, int position) {
		String jsonSelected = jsonData.get(position);
		JSONAdapterViewHolder.jsonView.setText(jsonSelected);
	}

	/**
	 * Gets the item count.
	 *
	 * @return the item count
	 */
	@Override
	public int getItemCount() {
		if (null == jsonData) return 0;
		return jsonData.size();
	}

	/**
	 * Read JSON.
	 *
	 * @param context the context
	 * @param url the url
	 * @param titleJSON the title JSON
	 * @param name the name
	 * @return the array list
	 */
	public ArrayList<String> readJSON(Context context, String url, String titleJSON, String name) {
		String tip;
		try {
			InputStream is = context.getAssets().open(url);
			int size = is.available();
			byte[] buffer = new byte[size];

			is.read(buffer);
			is.close();

			tip = new String(buffer, "UTF-8");
			JSONObject obj = new JSONObject(tip);
			JSONArray tipArray = obj.getJSONArray(titleJSON);

			for (int i = 0; i < tipArray.length(); i++) {
				JSONObject obj2 = tipArray.getJSONObject(i);
				jsonList.add(obj2.getString(name));
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return jsonList;
	}

}
