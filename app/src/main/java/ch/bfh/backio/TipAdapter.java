package ch.bfh.backio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipAdapterViewHolder> {
	private ArrayList<String> tipData = new ArrayList<>();
	private final TipAdapterOnClickHandler click;


	public interface TipAdapterOnClickHandler {
		void onClick(String tip);
	}

	public TipAdapter( TipAdapterOnClickHandler clickHandler) {
		click = clickHandler;
	}

	public void setTipData(String tipsData){
		tipData.add(tipsData);
	}

	public class TipAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public final TextView tipView;

		public TipAdapterViewHolder(View view) {
			super(view);
			tipView = (TextView) view.findViewById(R.id.tip_view);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			int adapterPosition = getAdapterPosition();
			String tipSelected = tipData.get(adapterPosition);
			click.onClick(tipSelected);
		}
	}

	@Override
	public TipAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		Context context = viewGroup.getContext();
		int layoutIdForListItem = R.layout.tip_list_item;
		LayoutInflater inflater = LayoutInflater.from(context);
		boolean shouldAttachToParentImmediately = false;

		View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
		return new TipAdapterViewHolder(view);
	}

	@Override
	public void onBindViewHolder(TipAdapterViewHolder TipAdapterViewHolder, int position) {
		String tipSelected = tipData.get(position);
		TipAdapterViewHolder.tipView.setText(tipSelected);
	}

	@Override
	public int getItemCount() {
		if (null == tipData) return 0;
		return tipData.size();
	}

}
