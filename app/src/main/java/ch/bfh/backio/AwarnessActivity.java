package ch.bfh.backio;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AwarnessActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_awarness);
		Context context = AwarnessActivity.this;
		//this.initListeners(context);
	}
}
