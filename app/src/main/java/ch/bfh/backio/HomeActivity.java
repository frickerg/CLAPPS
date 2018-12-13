package ch.bfh.backio;

import android.os.Bundle;
import android.content.*;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.mbientlab.metawear.android.BtleService;

public class HomeActivity extends AppCompatActivity implements ServiceConnection {
	private BtleService.LocalBinder serviceBinder;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

		getApplicationContext().bindService(new Intent(this, BtleService.class),
			this, Context.BIND_AUTO_CREATE);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onDestroy() {
		super.onDestroy();

		///< Unbind the service when the activity is destroyed
		getApplicationContext().unbindService(this);
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		///< Typecast the binder to the service's LocalBinder class
		this.serviceBinder = (BtleService.LocalBinder) service;
	}

	@Override
	public void onServiceDisconnected(ComponentName componentName) { }
	
}
