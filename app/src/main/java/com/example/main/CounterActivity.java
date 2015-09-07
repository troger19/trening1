package com.example.main;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class CounterActivity extends Activity implements TextToSpeech.OnInitListener{

    private Button btnStart, btnStop;
    private SeekBar seekBarTraining, seekBarPause;
    private TextView textViewTotalTime, textViewTraining, textViewPause, textExercise;
    EditText editTextSeries;
    private SoundPool sounds;
    private int sndtick;
    MediaPlayer mediaPlayer = null;
    String soundTick = "tick";
    CounterClass timer;
    int totalTrainingTime, seriesTime, pauseTime, series, roundCounter;
    TextToSpeech textToSpeech;
    boolean switcherTrainingPause = false;
    boolean playFiveSeconds = true;
    Vibrator vibrator;
    private String five="five";
    private String stop= "stop";
    private String finish = "Done";
    private String start = "start";
   private SharedPreferences sharedPref;
    private String trainingType;
    private TinyDB tinyDB;
    List<String> exercisesList = new ArrayList<>();
    private  int exerciseCounter = 0;
    private ImageView imageExercise;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        textToSpeech = new TextToSpeech(this, this);
        sharedPref = getSharedPreferences(getString(R.string.shared_pref_file), 0);


        Intent intent = getIntent();
        trainingType = intent.getStringExtra(getString(R.string.training_type));

        tinyDB = new TinyDB(this);
        exercisesList = tinyDB.getListString(trainingType);


        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        textViewTotalTime = (TextView) findViewById(R.id.textViewTotalTime);
        textExercise = (TextView) findViewById(R.id.textExercise);
        textViewTraining = (TextView) findViewById(R.id.textViewTraining);
        textViewPause = (TextView) findViewById(R.id.textViewPause);
        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sndtick = sounds.load(this, R.raw.ticks10s, 1);
        seekBarTraining = (SeekBar) findViewById(R.id.seekBarTraining);
        seekBarPause = (SeekBar) findViewById(R.id.seekBarPause);
        editTextSeries = (EditText) findViewById(R.id.editTextSeries);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        imageExercise = (ImageView) findViewById(R.id.imageExercise);

        // load saved values into the boxes
        loadValues();

        btnStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //total training time = (Training + pause) * series
                totalTrainingTime = (Integer.parseInt(textViewTraining.getText().toString()) + Integer.parseInt(textViewPause.getText().toString()))
                        * Integer.parseInt(editTextSeries.getText().toString()) * exercisesList.size() ;
                seriesTime = Integer.parseInt(textViewTraining.getText().toString());
                pauseTime = Integer.parseInt(textViewPause.getText().toString());
                textViewTotalTime.setText(textViewTraining.getText());
                textViewPause.setText(String.valueOf(seekBarPause.getProgress()));
                series = Integer.parseInt(editTextSeries.getText().toString());
                roundCounter = series * 2 * exercisesList.size();

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

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {

            }
        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }

    @Override
    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (timer != null) {
            timer.cancel();
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        super.onPause();
    }

    /**
     * load values from Shared preferences, set seekbar and TextViews
     */

    private void loadValues() {
//        String seriesTime = sharedPref.getString(trainingType + getString(R.string.training_time), "30");
//        String pauseTime = sharedPref.getString(trainingType + getString(R.string.pause_time), "15");
        String seriesTime =tinyDB.getString(trainingType + getString(R.string.training_time));
        String pauseTime = tinyDB.getString(trainingType + getString(R.string.pause_time));
        seekBarTraining.setProgress(Integer.valueOf(seriesTime));
        seekBarPause.setProgress(Integer.valueOf(pauseTime));
        textViewTraining.setText(seriesTime);
        textViewPause.setText(pauseTime);
//        editTextSeries.setText(sharedPref.getString(trainingType + getString(R.string.series), "3"));
        editTextSeries.setText(tinyDB.getString(trainingType + getString(R.string.series)));
//            editTextSeries.setText(exercisesList.get(2));
        textExercise.setText(exercisesList.get(0));
//        int resId = getResources().getIdentifier(exercisesList.get(0), "drawable", getPackageName());
//        imageExercise.setImageResource(resId);
//        int imageResource = getResources().getIdentifier("@drawable/"+exercisesList.get(0) , null, getPackageName());
//        Drawable res = getResources().getDrawable(R.drawable.push);
//        imageExercise.setImageDrawable(res);
        int imageResource = getResources().getIdentifier("drawable/" + exercisesList.get(0), null, getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageResource);
        imageExercise.setImageBitmap(bitmap);

    }



    // ---------------------------- HELPER TIMER CLASS -----------------  //

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {


        public CounterClass(long secondsInFuture, long countDownInterval) {
            super(secondsInFuture * 1000, (countDownInterval * 1000)-100);
        }

        @Override
        public void onFinish() {
            Log.i("onFinish ","finish");
            playFiveSeconds=true;

            if (roundCounter > 0) {
                if (switcherTrainingPause) {
                    timer = new CounterClass(seriesTime, 1);
                    textToSpeech.speak(start, TextToSpeech.QUEUE_FLUSH, null);
                    Log.i("seriesTime ", String.valueOf(seriesTime));
                    switcherTrainingPause = !switcherTrainingPause;
                    timer.start();
                } else {
                    timer = new CounterClass(pauseTime, 1);
                    textToSpeech.speak(stop, TextToSpeech.QUEUE_FLUSH, null);
                    Log.i("pauseTime ", String.valueOf(pauseTime));
                    switcherTrainingPause = !switcherTrainingPause;
                    timer.start();
                }
                roundCounter--;
                if (roundCounter % (series*2) ==0){  // Change the Exercise
                    exerciseCounter =  (exerciseCounter < exercisesList.size()-1) ? exerciseCounter+1 : exerciseCounter; // not overflow the index
                    textExercise.setText(exercisesList.get(exerciseCounter));  // change text
                    int imageResource = getResources().getIdentifier("drawable/" + exercisesList.get(exerciseCounter), null, getPackageName());
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageResource);
                    imageExercise.setImageBitmap(bitmap);
                    // TODO Say name of the Exercises
                }
            }
            if (roundCounter == 0) {
                textViewTotalTime.setText("Completed.");
                textToSpeech.speak(finish, TextToSpeech.QUEUE_FLUSH, null);
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

            if (milis < 6000 && playFiveSeconds){
                playFiveSeconds = false;
                textToSpeech.speak(five, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }


}
