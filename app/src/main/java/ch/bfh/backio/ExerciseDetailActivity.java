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

public class ExerciseDetailActivity extends AppCompatActivity {
	private TextView exerciseTitle;
	private TextView exerciseText;
	private String exerciseTitleText;
	private TextView exerciseText2;
	private Button diaryButton;
	private Button tipsButton;
	private Button sensorButton;
	private Button homeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_detail);

		exerciseTitle = (TextView) findViewById(R.id.display_exercise_title);
		exerciseText = (TextView) findViewById(R.id.display_exercise_text);
		exerciseText2 = (TextView) findViewById(R.id.display_exercise_text2);
		diaryButton = (Button) findViewById(R.id.btn_diary);
		tipsButton = (Button) findViewById(R.id.btn_tips);
		sensorButton = (Button) findViewById(R.id.btn_sensor);
		homeButton = (Button) findViewById(R.id.btn_home);

		Intent intentThatStartedThisActivity = getIntent();

		if (intentThatStartedThisActivity != null) {
			if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
				exerciseTitleText = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
				exerciseTitle.setText(exerciseTitleText);
			}
		}

		getJSONText();
		Context context = ExerciseDetailActivity.this;
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

	private void getJSONText(){
		String exercise;
		try {
			InputStream is = getAssets().open("exercise.json");
			int size = is.available();
			byte[] buffer = new byte[size];

			is.read(buffer);
			is.close();

			exercise = new String(buffer, "UTF-8");
			JSONObject obj = new JSONObject(exercise);
			JSONArray exerciseArray = obj.getJSONArray("exercise");

			for(int i = 0; i<exerciseArray.length(); i++){
				JSONObject obj2 = exerciseArray.getJSONObject(i);
				if(exerciseTitleText.equals(obj2.getString("title"))){
					exerciseText.setText(obj2.getString("text"));
					exerciseText2.setText(obj2.getString("text2"));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
