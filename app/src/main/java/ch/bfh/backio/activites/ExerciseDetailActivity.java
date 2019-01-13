package ch.bfh.backio.activites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ch.bfh.backio.R;
import ch.bfh.backio.services.JSONBroker;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class ExerciseDetailActivity.
 */
public class ExerciseDetailActivity extends AppCompatActivity {

	/**
	 * On create.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_detail);

		TextView exerciseTitle = findViewById(R.id.display_exercise_title);
		TextView exerciseText = findViewById(R.id.display_exercise_text);
		TextView exerciseText2 = findViewById(R.id.display_exercise_text2);
		ImageView exerciseImg1 = findViewById(R.id.display_exercise_img1);
		ImageView exerciseImg2 = findViewById(R.id.display_exercise_img2);

		String extraText = JSONBroker.retrieveIntentExtraTextString(getIntent());
		exerciseTitle.setText(extraText);
		try {
			JSONObject jsonObject = JSONBroker.retrieveJsonObject(getAssets(), "exercise.json", "exercise", extraText);
			exerciseText.setText(jsonObject.getString("text"));
			Bitmap img1 = BitmapFactory.decodeStream(getAssets().open(jsonObject.getString("img")));
			exerciseImg1.setImageBitmap(img1);
			exerciseText2.setText(jsonObject.getString("text2"));
			Bitmap img2 = BitmapFactory.decodeStream(getAssets().open(jsonObject.getString("img2")));
			exerciseImg2.setImageBitmap(img2);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
}
