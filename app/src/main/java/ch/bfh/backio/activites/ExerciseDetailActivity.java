package ch.bfh.backio.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ch.bfh.backio.R;
import ch.bfh.backio.services.JSONBroker;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ExerciseDetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_detail);

		TextView exerciseTitle = findViewById(R.id.display_exercise_title);
		TextView exerciseText = findViewById(R.id.display_exercise_text);
		TextView exerciseText2 = findViewById(R.id.display_exercise_text2);

		String extraText = JSONBroker.retrieveIntentExtraTextString(getIntent());
		exerciseTitle.setText(extraText);
		try {
			JSONObject jsonObject = JSONBroker.retrieveJsonObject(getAssets(), "exercise.json", "exercise", extraText);
			exerciseText.setText(jsonObject.getString("text"));
			exerciseText2.setText(jsonObject.getString("text2"));
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
}
