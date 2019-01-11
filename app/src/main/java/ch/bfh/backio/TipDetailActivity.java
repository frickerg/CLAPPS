package ch.bfh.backio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class TipDetailActivity extends AppCompatActivity {
	private TextView tipTitle;
	private TextView tipText;
	private TextView tipSubtitle;
	private TextView tipText2;
	private String tipTitleText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_detail);

		tipTitle = findViewById(R.id.display_tip_title);
		tipText = findViewById(R.id.display_tip_text);
		tipSubtitle = findViewById(R.id.display_tip_subtitle);
		tipText2 = findViewById(R.id.display_tip_text2);

		Intent intentThatStartedThisActivity = getIntent();
		if (intentThatStartedThisActivity != null) {
			if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
				tipTitleText = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
				tipTitle.setText(tipTitleText);
			}
		}

		getJSONText();
	}

	private void getJSONText() {
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

			for (int i = 0; i < tipArray.length(); i++) {
				JSONObject obj2 = tipArray.getJSONObject(i);
				if (tipTitleText.equals(obj2.getString("title"))) {
					tipText.setText(obj2.getString("text"));
					tipSubtitle.setText(obj2.getString("subtitle"));
					tipText2.setText(obj2.getString("text2"));
				}
			}

		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
}
