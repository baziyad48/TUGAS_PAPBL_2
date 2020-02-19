package com.example.android.latihan_sensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ConstraintLayout background;
    Button btn_proximity;
    Boolean isProximity = true;

    SensorManager sensorManager;
    Sensor acclerometer;
    Sensor proximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        background = findViewById(R.id.background);
        btn_proximity = findViewById(R.id.btn_proximity);
        background.setBackgroundColor(getResources().getColor(R.color.green));

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acclerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(listener, proximity, sensorManager.SENSOR_DELAY_NORMAL);

        btn_proximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(listener);
                sensorManager.registerListener(listener, (isProximity ? acclerometer : proximity), sensorManager.SENSOR_DELAY_NORMAL );
                isProximity = !isProximity;
            }
        });
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER && !isProximity){
                Log.v("Accelerometer: ", String.valueOf(sensorEvent.values[0]));
                textView.setText("x: " + sensorEvent.values[0]);
                if(sensorEvent.values[0] >= 0 && sensorEvent.values[0] <= 1){
                    background.setBackgroundColor(getResources().getColor(R.color.blue));
                } else {
                    background.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
            }

            if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY && isProximity) {
                Log.v("Proximity: ", String.valueOf(sensorEvent.values[0]));
                textView.setText("x: " + sensorEvent.values[0] + "\ny: " + sensorEvent.values[1] + "\nz: " + sensorEvent.values[2]);
                if(sensorEvent.values[0] == 0){
                    background.setBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    background.setBackgroundColor(getResources().getColor(R.color.green));
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
