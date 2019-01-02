package ch.bfh.backio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TipActivity extends AppCompatActivity implements JSONAdapter.JSONAdapterOnClickHandler {
	private Button diaryButton;
	private Button tipsButton;
	private Button sensorButton;
	private Button homeButton;
	private ArrayList<String> tipList = new ArrayList<>();
	private RecyclerView recyclerView;
	private JSONAdapter JSONAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		getTipJSON();

		diaryButton = (Button) findViewById(R.id.btn_diary);
		tipsButton = (Button) findViewById(R.id.btn_tips);
		sensorButton = (Button) findViewById(R.id.btn_sensor);
		homeButton = (Button) findViewById(R.id.btn_home);
		recyclerView = (RecyclerView) findViewById(R.id.rv_tip);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		JSONAdapter = new JSONAdapter(this);
		recyclerView.setAdapter(JSONAdapter);

		for(int i = 0; i<tipList.size(); i++){
			JSONAdapter.setJSONData(tipList.get(i));
		}

		Context context = TipActivity.this;
		diaryButton.setOnClickListener((v -> {

			Class destinationActivity = DiaryActivity.class;
			Intent startDiaryActivityIntent = new Intent(context, destinationActivity);
			startActivity(startDiaryActivityIntent);
			String message = "Button clicked!\nTagebuch";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		tipsButton.setOnClickListener((v -> {
			Class tipsnActivity = TipsActivity.class;
			Intent startTipsActivityIntent = new Intent(context, tipsnActivity);
			startActivity(startTipsActivityIntent);
			String message = "Button clicked!\nTipps";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		sensorButton.setOnClickListener((v -> {
			Class destinationActivity = SensorActivity.class;
			Intent startSensorActivityIntent = new Intent(context, destinationActivity);
			startActivity(startSensorActivityIntent);
			String message = "Button clicked!\nSensor";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		homeButton.setOnClickListener((v -> {
			Class destinationActivity = MainActivity.class;
			Intent startMainActivityIntent = new Intent(context, destinationActivity);
			startActivity(startMainActivityIntent);
			String message = "Button clicked!\nHome";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

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
				String message = tipList.get(i);
				Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
