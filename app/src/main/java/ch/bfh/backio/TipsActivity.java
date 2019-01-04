package ch.bfh.backio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TipsActivity extends AppCompatActivity {

	private LinearLayout diaryButton;
	private LinearLayout tipsButton;
	private LinearLayout sensorButton;
	private LinearLayout homeButton;
	private Button tipButton;
	private Button exerciseButton;
	private Button awarnessButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tips);

		diaryButton = (LinearLayout) findViewById(R.id.btn_diary);
		tipsButton = (LinearLayout) findViewById(R.id.btn_tips);
		sensorButton = (LinearLayout) findViewById(R.id.btn_sensor);
		homeButton = (LinearLayout) findViewById(R.id.btn_home);
		tipButton = (Button) findViewById(R.id.btn_tip);
		exerciseButton = (Button) findViewById(R.id.btn_exercise);
		awarnessButton = (Button) findViewById(R.id.btn_awarness);

		Context context = TipsActivity.this;
		diaryButton.setOnClickListener((v -> {

			Class destinationActivity = DiaryActivity.class;
			Intent startDiaryActivityIntent = new Intent(context, destinationActivity);
			startActivity(startDiaryActivityIntent);
			String message = "Button clicked!\nTagebuch";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		tipsButton.setOnClickListener((v -> {
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

		tipButton.setOnClickListener((v -> {
			Class destinationActivity = TipActivity.class;
			Intent startTipActivityIntent = new Intent(context, destinationActivity);
			startActivity(startTipActivityIntent);
			String message = "Button clicked!\nTip";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		exerciseButton.setOnClickListener((v -> {
			Class destinationActivity = ExerciseActivity.class;
			Intent startExerciseActivityIntent = new Intent(context, destinationActivity);
			startActivity(startExerciseActivityIntent);
			String message = "Button clicked!\nÜbungen";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		awarnessButton.setOnClickListener((v -> {
			Class destinationActivity = AwarnessActivity.class;
			Intent startAwarnessActivityIntent = new Intent(context, destinationActivity);
			startActivity(startAwarnessActivityIntent);
			String message = "Button clicked!\nAufklärung";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));
	}
}
