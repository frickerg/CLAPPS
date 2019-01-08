package ch.bfh.backio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class JSONAdapter extends RecyclerView.Adapter<JSONAdapter.JSONAdapterViewHolder> {
	private ArrayList<String> jsonData = new ArrayList<>();
	private final JSONAdapterOnClickHandler click;

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

}
