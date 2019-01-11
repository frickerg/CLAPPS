package ch.bfh.backio.services;

import android.content.Intent;
import android.content.res.AssetManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public abstract class JSONBroker {
	public static String retrieveIntentExtraTextString(Intent intentThatStartedThisActivity) {
		if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
			return intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
		}
		return "[empty]";
	}

	public static JSONObject retrieveJsonObject(AssetManager assetManager, String jsonFileName, String jsonArrayName, CharSequence title) throws JSONException, IOException {
		InputStream is = assetManager.open(jsonFileName);
		int size = is.available();
		byte[] buffer = new byte[size];

		is.read(buffer);
		is.close();

		String entity = new String(buffer, StandardCharsets.UTF_8);
		JSONObject jsonObject = new JSONObject(entity);
		JSONArray jsonArray = jsonObject.getJSONArray(jsonArrayName);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonEntry = jsonArray.getJSONObject(i);
			if (jsonArray.getJSONObject(i).getString("title").equals(title.toString())) {
				return jsonEntry;
			}
		}
		return new JSONObject();
	}
}
