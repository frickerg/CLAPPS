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

public class JSONAdapter extends RecyclerView.Adapter<JSONAdapter.JSONAdapterViewHolder> {
	private ArrayList<String> jsonData = new ArrayList<>();
	private final JSONAdapterOnClickHandler click;
	private ArrayList<String> jsonList = new ArrayList<>();

	public interface JSONAdapterOnClickHandler {
		void onClick(String json);
	}

	public JSONAdapter(JSONAdapterOnClickHandler clickHandler) {
		click = clickHandler;
	}
	public void setJSONData(String jsonData){
		this.jsonData.add(jsonData);
	}

	public class JSONAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public final TextView jsonView;

		public JSONAdapterViewHolder(View view) {
			super(view);
			jsonView = (TextView) view.findViewById(R.id.json_view);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			int adapterPosition = getAdapterPosition();
			String jsonSelected = jsonData.get(adapterPosition);
			click.onClick(jsonSelected);
		}
	}

	@Override
	public JSONAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		Context context = viewGroup.getContext();
		int layoutIdForListItem = R.layout.json_list_item;
		LayoutInflater inflater = LayoutInflater.from(context);
		boolean shouldAttachToParentImmediately = false;

		View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
		return new JSONAdapterViewHolder(view);
	}

	@Override
	public void onBindViewHolder(JSONAdapterViewHolder JSONAdapterViewHolder, int position) {
		String jsonSelected = jsonData.get(position);
		JSONAdapterViewHolder.jsonView.setText(jsonSelected);
	}

	@Override
	public int getItemCount() {
		if (null == jsonData) return 0;
		return jsonData.size();
	}

	public ArrayList<String> readJSON(Context context, String url, String titleJSON, String name){
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

			for(int i = 0; i<tipArray.length(); i++){
				JSONObject obj2 = tipArray.getJSONObject(i);
				jsonList.add(obj2.getString(name));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonList;
	}

}
