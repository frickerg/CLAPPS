package ch.bfh.backio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity implements JSONAdapter.JSONAdapterOnClickHandler{

	private LinearLayout diaryButton;
	private LinearLayout tipsButton;
	private LinearLayout sensorButton;
	private LinearLayout homeButton;
	private ArrayList<String> exerciseList = new ArrayList<>();
	private RecyclerView recyclerView;
	private JSONAdapter JSONAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		Context context = ExerciseActivity.this;

		diaryButton = (LinearLayout) findViewById(R.id.btn_diary);
		tipsButton = (LinearLayout) findViewById(R.id.btn_tips);
		sensorButton = (LinearLayout) findViewById(R.id.btn_sensor);
		homeButton = (LinearLayout) findViewById(R.id.btn_home);
		recyclerView = (RecyclerView) findViewById(R.id.rv_exercise);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		JSONAdapter = new JSONAdapter(this);
		recyclerView.setAdapter(JSONAdapter);

		exerciseList = JSONAdapter.readJSON(context, "exercise.json", "exercise", "title");

		for(int i = 0; i<exerciseList.size(); i++){
			JSONAdapter.setJSONData(exerciseList.get(i));
		}




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
	public void onClick(String exerciseTitel) {
		Context context = this;
		Toast.makeText(context, exerciseTitel, Toast.LENGTH_SHORT).show();
		Class destinationClass = ExerciseDetailActivity.class;
		Intent intentToStartDetailActivity = new Intent(context, destinationClass);
		intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, exerciseTitel);
		startActivity(intentToStartDetailActivity);
	}

}
