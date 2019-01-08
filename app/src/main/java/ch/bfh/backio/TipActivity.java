package ch.bfh.backio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TipActivity extends AppCompatActivity implements JSONAdapter.JSONAdapterOnClickHandler {
	private ArrayList<String> tipList = new ArrayList<>();
	private RecyclerView recyclerView = findViewById(R.id.rv_tip);
	private JSONAdapter JSONAdapter = new JSONAdapter(this);;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		getTipJSON();

		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(JSONAdapter);

		for(int i = 0; i<tipList.size(); i++){
			JSONAdapter.setJSONData(tipList.get(i));
		}

		Context context = TipActivity.this;
		//this.initListeners(context);
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

	private void getTipJSON(){
		String tip;
		try {
			InputStream is = getAssets().open("tip.json");
			int size = is.available();
			byte[] buffer = new byte[size];

			is.read(buffer);
			is.close();

			tip = new String(buffer, "UTF-8");
			JSONObject obj = new JSONObject(tip);
			JSONArray tipArray = obj.getJSONArray("tip");

			for(int i = 0; i<tipArray.length(); i++){
				JSONObject obj2 = tipArray.getJSONObject(i);
				tipList.add(obj2.getString("title"));
			}

		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
}
