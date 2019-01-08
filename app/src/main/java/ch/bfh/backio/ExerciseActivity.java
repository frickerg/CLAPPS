package ch.bfh.backio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity implements JSONAdapter.JSONAdapterOnClickHandler{
	private ArrayList<String> exerciseList = new ArrayList<>();
	private RecyclerView recyclerView = findViewById(R.id.rv_exercise);
	private JSONAdapter JSONAdapter = new JSONAdapter(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		Context context = ExerciseActivity.this;

		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(JSONAdapter);

		exerciseList = JSONAdapter.readJSON(context, "exercise.json", "exercise", "title");

		for(int i = 0; i<exerciseList.size(); i++){
			JSONAdapter.setJSONData(exerciseList.get(i));
		}
	}

	@Override
	public void onClick(String exerciseTitel) {
		Context context = this;
		Toast.makeText(context, exerciseTitel, Toast.LENGTH_SHORT).show();
		Class destinationClass = ExerciseDetailActivity.class;
		Intent intentToStartDetailActivity = new Intent(context, destinationClass);
		intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, exerciseTitel);
		startActivity(intentToStartDetailActivity);
	}

}
