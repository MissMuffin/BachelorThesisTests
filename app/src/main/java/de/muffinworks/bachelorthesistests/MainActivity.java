package de.muffinworks.bachelorthesistests;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

/*
*https://examples.javacodegeeks.com/android/core/os/handler/android-timer-example/
 */
public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button resetButton;
    private TextView timerValue;
    private long startTime = 0L;
    private Handler mHandler = new Handler();

    private Button addSecondsButton;
    private Button addMinutesButton;
    private Button addHourButton;
    private Button addHoursButton;

    private boolean isRunning = false;

    long timeInMillis = 0L;
    long timeSwapBuffer = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        startButton = (Button) findViewById(R.id.startButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        timerValue = (TextView) findViewById(R.id.timerValue);
        addSecondsButton = (Button) findViewById(R.id.addSecondsButton);
        addMinutesButton = (Button) findViewById(R.id.addMinutesButton);
        addHourButton = (Button) findViewById(R.id.addHourButton);
        addHoursButton = (Button) findViewById(R.id.addHoursButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTimer();
                } else {
                    pauseTimer();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    pauseTimer();
                }
                timeInMillis = 0L;
                timeSwapBuffer = 0L;
                updatedTime = 0L;
                startTime = 0L;
                timerValue.setText(R.string.timerVal);
            }
        });

        addSecondsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuffer += 10000;
            }
        });
        addMinutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuffer += 600000;
            }
        });
        addHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuffer += 3600000;
            }
        });
        addHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuffer += 36000000;
            }
        });
    }

    private void pauseTimer() {
        timeSwapBuffer += timeInMillis;
        mHandler.removeCallbacks(updatedTimerThread);
        isRunning = false;
        startButton.setText(R.string.startButtonLabel);
    }

    private void startTimer() {
        startTime = SystemClock.uptimeMillis();
        mHandler.postDelayed(updatedTimerThread, 0);
        isRunning = true;
        startButton.setText(R.string.startButtonLabelRunning);
    }

    private Runnable updatedTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMillis = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuffer + timeInMillis;

            int secs = (int) (updatedTime/1000);
            int min = secs/60;
            int hours = min/60;
            min = min%60;
            secs = secs % 60;
            int millis = (int) (updatedTime%1000);

            timerValue.setText(String.format(getResources().getString(R.string.timerValueMold) ,hours, min, secs, millis));
            mHandler.postDelayed(this, 0);
        }
    };
}
