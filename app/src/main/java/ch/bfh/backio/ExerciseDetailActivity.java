package ch.bfh.backio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class ExerciseDetailActivity extends AppCompatActivity {
	private TextView exerciseTitle = findViewById(R.id.display_exercise_title);
	private TextView exerciseText = findViewById(R.id.display_exercise_text);
	//private TextView exerciseText2 = findViewById(R.id.display_exercise_text2);
	private String exerciseTitleText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_detail);

		Intent intentThatStartedThisActivity = getIntent();
		if (intentThatStartedThisActivity != null) {
			if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
				exerciseTitleText = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
				exerciseTitle.setText(exerciseTitleText);
			}
		}

		getJSONText();
		Context context = ExerciseDetailActivity.this;
		//this.initListeners(context);
	}

	private void getJSONText(){
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
				if(tipArray.getJSONObject(i).getString("title").equals(exerciseTitleText)){
					exerciseText.setText("Test");
					exerciseTitle.setText("Hallo");
				}
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
}
