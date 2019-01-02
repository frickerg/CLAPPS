package ch.bfh.backio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

	private Button diaryButton;
	private Button tipsButton;
	private Button sensorButton;
	private Button homeButton;
	private TextView viewExercise;
	private ArrayList<String> exerciseList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);


		diaryButton = (Button) findViewById(R.id.btn_diary);
		tipsButton = (Button) findViewById(R.id.btn_tips);
		sensorButton = (Button) findViewById(R.id.btn_sensor);
		homeButton = (Button) findViewById(R.id.btn_home);
		viewExercise = (TextView) findViewById(R.id.exercise_view);

		getExerciseJSON();
		for(int i = 0; i<exerciseList.size(); i++){
			viewExercise.append((exerciseList.get(i))+"\n\n");
		}
		Context context = ExerciseActivity.this;
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

	private void getExerciseJSON(){
		String tip;
		try {
			InputStream is = getAssets().open("exercise.json");
			int size = is.available();
			byte[] buffer = new byte[size];

			is.read(buffer);
			is.close();

			tip = new String(buffer, "UTF-8");
			JSONObject obj = new JSONObject(tip);
			JSONArray tipArray = obj.getJSONArray("exercise");

			for(int i = 0; i<tipArray.length(); i++){
				JSONObject obj2 = tipArray.getJSONObject(i);
				exerciseList.add(obj2.getString("title"));
				String message = exerciseList.get(i);
				Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
