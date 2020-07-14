package com.example.app_egg_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.widget.SeekBar;
import android.widget.TextView;
import  android.view.View;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    SeekBar seekbar;
    TextView text;
    Button press;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        text.setText("0:30");
        seekbar.setProgress(30);
        countDownTimer.cancel();
        press.setText("Go!!");
        seekbar.setEnabled(true);
        counterIsActive = false;
    }

    public void updateTimer(int secondsLeft){
        int min = (int) secondsLeft/60;
        int sec = secondsLeft - min*60;
        String secondString = Integer.toString(sec);

        if(sec <= 9)
        {
            secondString = "0" + secondString;
        }
        text.setText(Integer.toString(min) + ":" + secondString);
    }
    public void press(View view)
    {
        if(counterIsActive == false) {
            counterIsActive = true;
            seekbar.setEnabled(false);
            press.setText("Stop");

           countDownTimer = new CountDownTimer(seekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mplayer.start();
                }
            }.start();
        } else {
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar = (SeekBar) findViewById(R.id.seekbar);
        text = (TextView) findViewById(R.id.text);
        press = (Button) findViewById(R.id.button);

        seekbar.setMax(600);
        seekbar.setProgress(30);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
