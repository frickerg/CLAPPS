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

/**
 * TODO: The Class JSONAdapter.
 *
 * Description here...
 */
public class JSONAdapter extends RecyclerView.Adapter<JSONAdapter.JSONAdapterViewHolder> {
	private final JSONAdapterOnClickHandler click;
	private ArrayList<String> jsonData = new ArrayList<>();
	private ArrayList<String> jsonList = new ArrayList<>();

	/**
	 * TODO: The Interface JSONAdapterOnClickHandler.
	 * <p>
	 * Description here...
	 */
	public interface JSONAdapterOnClickHandler {

		/**
		 * TODO: On click.
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
	 * TODO: Sets the JSON data.
	 *
	 * @param jsonData the new JSON data
	 */
	public void setJSONData(String jsonData) {
		this.jsonData.add(jsonData);
	}

	/**
	 * TODO: The Class JSONAdapterViewHolder.
	 * <p>
	 * Description here...
	 */
	public class JSONAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		/**
		 * TODO: The json view.
		 */
		public final TextView jsonView;

		/**
		 * TODO: Instantiates a new JSON adapter view holder.
		 *
		 * @param view the view
		 */
		public JSONAdapterViewHolder(View view) {
			super(view);
			jsonView = (TextView) view.findViewById(R.id.json_view);
			view.setOnClickListener(this);
		}

		/**
		 * Called when a view has been clicked.
		 *
		 * @param v The view that was clicked.
		 */
		@Override
		public void onClick(View v) {
			int adapterPosition = getAdapterPosition();
			String jsonSelected = jsonData.get(adapterPosition);
			click.onClick(jsonSelected);
		}
	}

	/**
	 * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
	 *
	 * @param viewGroup The ViewGroup into which the new View will be added after it is bound to
	 *                  an adapter position.
	 * @param viewType  The view type of the new View.
	 * @return A new ViewHolder that holds a View of the given view type.
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
	 * Called by RecyclerView to display the data at the specified position.
	 *
	 * @param jsonAdapterViewHolder The ViewHolder which should be updated to represent the contents of the
	 *                              item at the given position in the data set.
	 * @param position              The position of the item within the adapter's data set.
	 */
	@Override
	public void onBindViewHolder(JSONAdapterViewHolder jsonAdapterViewHolder, int position) {
		String jsonSelected = jsonData.get(position);
		jsonAdapterViewHolder.jsonView.setText(jsonSelected);
	}

	/**
	 * Returns the total number of items in the data set held by the adapter.
	 *
	 * @return The total number of items in this adapter.
	 */
	@Override
	public int getItemCount() {
		if (null == jsonData) return 0;
		return jsonData.size();
	}

	/**
	 * TODO: Read JSON.
	 *
	 * @param context   the context
	 * @param url       the url
	 * @param titleJSON the title JSON
	 * @param name      the name
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
