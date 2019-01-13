package ch.bfh.backio.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ch.bfh.backio.R;
import ch.bfh.backio.services.JSONBroker;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class TipDetailActivity.
 */
public class TipDetailActivity extends AppCompatActivity {

	/**
	 * On create.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_detail);

		TextView tipTitle = findViewById(R.id.display_tip_title);
		TextView tipText = findViewById(R.id.display_tip_text);
		TextView tipSubtitle = findViewById(R.id.display_tip_subtitle);
		TextView tipText2 = findViewById(R.id.display_tip_text2);

		String extraText = JSONBroker.retrieveIntentExtraTextString(getIntent());
		tipTitle.setText(extraText);

		try {
			JSONObject jsonObject = JSONBroker.retrieveJsonObject(getAssets(), "tip.json", "tip", extraText);
			tipText.setText(jsonObject.getString("text"));
			tipSubtitle.setText(jsonObject.getString("subtitle"));
			tipText2.setText(jsonObject.getString("text2"));
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
}
