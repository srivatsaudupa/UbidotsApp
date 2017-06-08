package edu.scu.ubidotsapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.ubidots.ApiClient;
import com.ubidots.Variable;

public class MainActivity extends Activity {
    private static final String BATTERY_LEVEL = "level";
    private TextView mBatteryLevel;
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BATTERY_LEVEL, 0);

            mBatteryLevel.setText(Integer.toString(level) + "%");
            new ApiUbidots().execute(level);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBatteryLevel = (TextView) findViewById(R.id.batteryLevel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mBatteryReceiver);
        super.onStop();
    }

    public class ApiUbidots extends AsyncTask<Integer, Void, Void> {
        private final String API_KEY = "";
        private final String VARIABLE_ID = "";

        @Override
        protected Void doInBackground(Integer... params) {
            ApiClient apiClient = new ApiClient(API_KEY);
            Variable batteryLevel = apiClient.getVariable(VARIABLE_ID);
            batteryLevel.saveValue(params[0]);
            return null;
        }
    }
}