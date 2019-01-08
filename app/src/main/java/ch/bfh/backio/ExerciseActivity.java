package ch.bfh.backio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity implements JSONAdapter.JSONAdapterOnClickHandler{
	private ArrayList<String> exerciseList = new ArrayList<>();
	private RecyclerView recyclerView = findViewById(R.id.rv_exercise);;
	private JSONAdapter JSONAdapter = new JSONAdapter(this);;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		getExerciseJSON();

		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(JSONAdapter);

		for(int i = 0; i<exerciseList.size(); i++){
			JSONAdapter.setJSONData(exerciseList.get(i));
		}

		Context context = ExerciseActivity.this;
		//this.initListeners(context);
	}

	@Override
	public void onClick(String exerciseTitel) {
		Context context = this;
		Toast.makeText(context, exerciseTitel, Toast.LENGTH_SHORT).show();
		Class destinationClass = TipDetailActivity.class;
		Intent intentToStartDetailActivity = new Intent(context, destinationClass);
		intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, exerciseTitel);
		startActivity(intentToStartDetailActivity);
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
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
