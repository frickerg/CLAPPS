package ch.bfh.backio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class DiaryActivity extends AppCompatActivity {

	private Button diaryButton;
	private Button tipsButton;
	private Button sensorButton;
	private Button homeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary);

		diaryButton = (Button) findViewById(R.id.btn_diary);
		tipsButton = (Button) findViewById(R.id.btn_tips);
		sensorButton = (Button) findViewById(R.id.btn_sensor);
		homeButton = (Button) findViewById(R.id.btn_home);

		Context context = DiaryActivity.this;
		diaryButton.setOnClickListener((v -> {
			String message = "Button clicked!\nyou are here!";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		tipsButton.setOnClickListener((v -> {
			/*Class destinationActivity = TipsActivity.class;
			Intent startDiaryActivityIntent = new Intent(context, destinationActivity);
			startActivity(startDiaryActivityIntent);*/
			String message = "Button clicked!\nTipps";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		sensorButton.setOnClickListener((v -> {
			/*Class destinationActivity = Sensor.class;
			Intent startDiaryActivityIntent = new Intent(context, destinationActivity);
			startActivity(startDiaryActivityIntent);*/
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
}
