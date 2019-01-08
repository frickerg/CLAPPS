package ch.bfh.backio;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mbientlab.bletoolbox.scanner.BleScannerFragment;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;
import bolts.Task;

public class MainActivity extends AppCompatActivity implements ch.bfh.backio.JSONAdapter.JSONAdapterOnClickHandler {//implements BleScannerFragment.ScannerCommunicationBus, ServiceConnection {
    public static final int REQUEST_START_APP= 1;

    private BtleService.LocalBinder serviceBinder;
  //  private MetaWearBoard metawear;
    private LinearLayout diaryButton;
    private LinearLayout tipsButton;
    private LinearLayout sensorButton;
    private LinearLayout homeButton;
    private Button sensorConnectButton;
    private ImageView postureImage;
    private ArrayList<String> tipList = new ArrayList<>();
    private ArrayList<String> exerciseList = new ArrayList<>();
    private ArrayList<String> jsonList = new ArrayList<>();
	private RecyclerView recyclerView;
	private JSONAdapter JSONAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // getApplicationContext().bindService(new Intent(this, BtleService.class), this, BIND_AUTO_CREATE);

		diaryButton = (LinearLayout) findViewById(R.id.btn_diary);
		tipsButton = (LinearLayout) findViewById(R.id.btn_tips);
		sensorButton = (LinearLayout) findViewById(R.id.btn_sensor);
		homeButton = (LinearLayout) findViewById(R.id.btn_home);
		recyclerView = (RecyclerView) findViewById(R.id.rv_dailyTip);
		sensorConnectButton = (Button) findViewById(R.id.btn_connectSensor);
		postureImage = (ImageView) findViewById(R.id.img_posture);
		//displayDailyTip = (TextView) findViewById(R.id.daily_tip);
		//displayDailyExercise = (TextView) findViewById(R.id.daily_exercise);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		JSONAdapter = new JSONAdapter(this);
		recyclerView.setAdapter(JSONAdapter);

		Context context = MainActivity.this;


		exerciseList = JSONAdapter.readJSON(context, "exercise.json", "exercise", "title");

		showImage();
		int dailytip = (int) Math.random()*tipList.size();
		int dailyexercise = (int) Math.random()*exerciseList.size();

		JSONAdapter.setJSONData(exerciseList.get(3));

		tipList = JSONAdapter.readJSON(context, "tip.json", "tip", "title");
		JSONAdapter.setJSONData(tipList.get(3));


		diaryButton.setOnClickListener((v -> {

			Class destinationActivity = DiaryActivity.class;
			Intent startDiaryActivityIntent = new Intent(context, destinationActivity);
			startActivity(startDiaryActivityIntent);
			String message = "Button clicked!\nTagebuch";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		tipsButton.setOnClickListener((v -> {
			Class tipsnActivity = TipsActivity.class;
			Intent startTipsActivityIntent = new Intent(context, tipsnActivity);
			startActivity(startTipsActivityIntent);
			String message = "Button clicked!\nTipps";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		sensorButton.setOnClickListener((v -> {
			Class destinationActivity = SensorActivity.class;
			Intent startSensorActivityIntent = new Intent(context, destinationActivity);
			startActivity(startSensorActivityIntent);
			String message = "Button clicked!\nSensor";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		homeButton.setOnClickListener((v -> {
			String message = "Button clicked!\nHome";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));

		sensorConnectButton.setOnClickListener((v -> {
			String message = "Button clicked!\nConnectSensor";
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}));
    }


	@Override
	public void onClick(String json) {
		Context context = this;
		Toast.makeText(context, json, Toast.LENGTH_SHORT).show();
		Class destinationClass = ExerciseDetailActivity.class;
		Intent intentToStartDetailActivity = new Intent(context, destinationClass);
		intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, json);
		startActivity(intentToStartDetailActivity);
	}

	private void showButton(){
    	postureImage.setVisibility(View.INVISIBLE);
    	sensorConnectButton.setVisibility(View.VISIBLE);
	}

	private  void showImage(){
    	postureImage.setVisibility(View.VISIBLE);
    	sensorConnectButton.setVisibility(View.INVISIBLE);
	}

   /* @Override
    public void onDestroy() {
        super.onDestroy();

        ///< Unbind the service when the activity is destroyed
        getApplicationContext().unbindService(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_START_APP:
                ((BleScannerFragment) getFragmentManager().findFragmentById(R.id.scanner_fragment)).startBleScan();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public UUID[] getFilterServiceUuids() {
        return new UUID[] {MetaWearBoard.METAWEAR_GATT_SERVICE};
    }

    @Override
    public long getScanDuration() {
        return 10000L;
    }

  /*  @Override
    public void onDeviceSelected(final BluetoothDevice device) {
      //  metawear = serviceBinder.getMetaWearBoard(device);

        final ProgressDialog connectDialog = new ProgressDialog(this);
        connectDialog.setTitle(getString(R.string.title_connecting));
        connectDialog.setMessage(getString(R.string.message_wait));
        connectDialog.setCancelable(false);
        connectDialog.setCanceledOnTouchOutside(false);
        connectDialog.setIndeterminate(true);
        connectDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), (dialogInterface, i) -> metawear.disconnectAsync());
        connectDialog.show();

      /*  metawear.connectAsync().continueWithTask(task -> task.isCancelled() || !task.isFaulted() ? task : reconnect(metawear))
                .continueWith(task -> {
                    if (!task.isCancelled()) {
                        runOnUiThread(connectDialog::dismiss);
                        Intent navActivityIntent = new Intent(MainActivity.this, DeviceSetupActivity.class);
                        navActivityIntent.putExtra(DeviceSetupActivity.EXTRA_BT_DEVICE, device);
                        startActivityForResult(navActivityIntent, REQUEST_START_APP);
                    }

                    return null;
                });
    }*/

	/*  @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        serviceBinder = (BtleService.LocalBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public static Task<Void> reconnect(final MetaWearBoard board) {
        return board.connectAsync().continueWithTask(task -> task.isFaulted() ? reconnect(board) : task);
    }*/
}
