package de.muffinworks.bachelorthesistests;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button pauseButton;
    private TextView timerValue;
    private long startTime = 0L;
    private Handler mHandler = new Handler();

    long timeInMillis = 0L;
    long timeSwapBuffer = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        startButton = (Button) findViewById(R.id.startButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        timerValue = (TextView) findViewById(R.id.timerValue);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                mHandler.postDelayed(updatedTimerThread, 0);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuffer += timeInMillis;
                mHandler.removeCallbacks(updatedTimerThread, 0);
            }
        });
    }

    private Runnable updatedTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMillis = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuffer + timeInMillis;

            int secs = (int) (updatedTime/1000);
            int min = secs/60;
            secs = secs % 60;
            int millis = (int) (updatedTime%1000);

            timerValue.setText("" + min + ":" + String.format("%02d", secs) + ":" + String.format("%03d", millis));
            mHandler.postDelayed(this, 0);
        }
    };
}
