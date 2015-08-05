package com.example.main;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jano.trening.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class CounterActivity extends Activity {

    private Button btnStart, btnStop;
    private SeekBar seekBarTraining, seekBarPause;
    private TextView textViewTotalTime, textViewTraining, textViewPause, textViewSeriesTime, textViewPauseTime;
    EditText editTextSeries;
    private SoundPool sounds;
    private int sndtick;
    MediaPlayer mediaPlayer = null;
    String soundTick = "tick";
    CounterClass timer;
    int totalTrainingTime, seriesTime, pauseTime, seriesCounter, roundCounter;
    TextToSpeech t1;
    boolean switcher = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        textViewTotalTime = (TextView) findViewById(R.id.textViewTotalTime);
        textViewSeriesTime = (TextView) findViewById(R.id.textViewSeriesTime);
        textViewPauseTime = (TextView) findViewById(R.id.textViewPauseTime);
        textViewTraining = (TextView) findViewById(R.id.textViewTraining);
        textViewPause = (TextView) findViewById(R.id.textViewPause);
        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sndtick = sounds.load(this, R.raw.ticks10s, 1);
        seekBarTraining = (SeekBar) findViewById(R.id.seekBarTraining);
        seekBarPause = (SeekBar) findViewById(R.id.seekBarPause);
        editTextSeries = (EditText) findViewById(R.id.editTextSeries);

        btnStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //total training time = (Training + pause) * series
                totalTrainingTime = (Integer.parseInt(textViewTraining.getText().toString()) + Integer.parseInt(textViewPause.getText().toString()))
                        * Integer.parseInt(editTextSeries.getText().toString());
                seriesTime = Integer.parseInt(textViewTraining.getText().toString());
                pauseTime = Integer.parseInt(textViewPause.getText().toString());
                textViewTotalTime.setText(textViewTraining.getText());
                textViewSeriesTime.setText(String.valueOf(seekBarTraining.getProgress()));
                textViewPause.setText(String.valueOf(seekBarPause.getProgress()));
                seriesCounter = Integer.parseInt(editTextSeries.getText().toString());
                roundCounter = seriesCounter * 2;

                if (timer != null) {
                    timer.cancel();
                }
                timer = new CounterClass(seriesTime, 1);
                timer.start();
                managerOfSound(soundTick);
            }
        });

        btnStop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                }
                mediaPlayer.stop();
            }
        });

        seekBarPause.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewPause.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarTraining.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewTraining.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

    }


    protected void managerOfSound(String theText) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        if (theText == soundTick) {
            mediaPlayer = MediaPlayer.create(this, R.raw.ticks10s);
        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.onetwo);
        }

        mediaPlayer.start();
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {


        public CounterClass(long secondsInFuture, long countDownInterval) {
            super(secondsInFuture * 1000, countDownInterval * 1000);
        }


        @Override
        public void onFinish() {
            Log.i("onFinish ","finish");
            if (roundCounter > 0) {
                if (switcher) {
                    timer = new CounterClass(seriesTime, 1);
                    Log.i("seriesTime ", String.valueOf(seriesTime));
                    switcher = !switcher;
                    timer.start();
                } else {
                    timer = new CounterClass(pauseTime, 1);
                    Log.i("pauseTime ", String.valueOf(pauseTime));
                    switcher = !switcher;
                    timer.start();
                }
                roundCounter--;
            }
            if (roundCounter == 0) {
                textViewTotalTime.setText("Completed.");
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i("milistofinihed = ", String.valueOf(millisUntilFinished / 1000));
            long milis = millisUntilFinished;

            String hmsTotal = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milis),
                    TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis)),
                    TimeUnit.MILLISECONDS.toSeconds(milis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milis)));
            Log.i("hmsTotal ", hmsTotal);
            textViewTotalTime.setText(hmsTotal);
        }


    }


}
