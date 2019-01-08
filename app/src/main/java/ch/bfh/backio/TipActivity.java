package ch.bfh.backio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;

public class TipActivity extends AppCompatActivity implements JSONAdapter.JSONAdapterOnClickHandler {
	private ArrayList<String> tipList = new ArrayList<>();
	private RecyclerView recyclerView;
	private JSONAdapter JSONAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		Context context = TipActivity.this;

		recyclerView = findViewById(R.id.rv_tip);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		JSONAdapter = new JSONAdapter(this);
		recyclerView.setAdapter(JSONAdapter);

		tipList = JSONAdapter.readJSON(context, "tip.json", "tip", "title");

		for(int i = 0; i<tipList.size(); i++){
			JSONAdapter.setJSONData(tipList.get(i));
		}
	}

	@Override
	public void onClick(String tipTitel) {
		Context context = this;
		Toast.makeText(context, tipTitel, Toast.LENGTH_SHORT).show();
		Class destinationClass = TipDetailActivity.class;
		Intent intentToStartDetailActivity = new Intent(context, destinationClass);
		intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, tipTitel);
		startActivity(intentToStartDetailActivity);
	}

}
