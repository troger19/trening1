//package com.example.main;
//
///**
// * Created by jan.babel on 18/08/2015.
// */
//
//import android.app.Fragment;
//import android.content.Context;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.media.SoundPool;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Vibrator;
//import android.speech.tts.TextToSpeech;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import java.util.Locale;
//import java.util.concurrent.TimeUnit;
//
//public class FragmentOne extends Fragment implements TextToSpeech.OnInitListener {
//
//    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
//    public static final String ITEM_NAME = "itemName";
//
//
//    private Button btnStart, btnStop;
//    private SeekBar seekBarTraining, seekBarPause;
//    private TextView textViewTotalTime, textViewTraining, textViewPause, textViewSeriesTime, textViewPauseTime;
//    EditText editTextSeries;
//    private SoundPool sounds;
//    private int sndtick;
//    MediaPlayer mediaPlayer = null;
//    String soundTick = "tick";
//    CounterClass timer;
//    int totalTrainingTime, seriesTime, pauseTime, seriesCounter, roundCounter;
//    TextToSpeech textToSpeech;
//    boolean switcherTrainingPause = false;
//    boolean playFiveSeconds = true;
//    Vibrator vibrator;
//    private String five = "five";
//    private String stop = "stop";
//    private String finish = "Done";
//    private String start = "start";
//
//
//    public FragmentOne() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.activity_counter, container, false);
//        textToSpeech = new TextToSpeech(getActivity(), this);
//
//        btnStart = (Button) view.findViewById(R.id.btnStart);
//        btnStop = (Button) view.findViewById(R.id.btnStop);
//        textViewTotalTime = (TextView) view.findViewById(R.id.textViewTotalTime);
//        textViewTraining = (TextView) view.findViewById(R.id.textViewTraining);
//        textViewPause = (TextView) view.findViewById(R.id.textViewPause);
//        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
//        sndtick = sounds.load(getActivity(), R.raw.ticks10s, 1);
//        seekBarTraining = (SeekBar) view.findViewById(R.id.seekBarTraining);
//        seekBarPause = (SeekBar) view.findViewById(R.id.seekBarPause);
//        editTextSeries = (EditText) view.findViewById(R.id.editTextSeries);
//        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
//
//
//        btnStart.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //total training time = (Training + pause) * series
//                totalTrainingTime = (Integer.parseInt(textViewTraining.getText().toString()) + Integer.parseInt(textViewPause.getText().toString()))
//                        * Integer.parseInt(editTextSeries.getText().toString());
//                seriesTime = Integer.parseInt(textViewTraining.getText().toString());
//                pauseTime = Integer.parseInt(textViewPause.getText().toString());
//                textViewTotalTime.setText(textViewTraining.getText());
//                textViewSeriesTime.setText(String.valueOf(seekBarTraining.getProgress()));
//                textViewPause.setText(String.valueOf(seekBarPause.getProgress()));
//                seriesCounter = Integer.parseInt(editTextSeries.getText().toString());
//                roundCounter = seriesCounter * 2;
//
//                if (timer != null) {
//                    timer.cancel();
//                }
//                timer = new CounterClass(seriesTime, 1);
//                timer.start();
//                managerOfSound(soundTick);
//            }
//        });
//
//        btnStop.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (timer != null) {
//                    timer.cancel();
//                }
//                mediaPlayer.stop();
//            }
//        });
//
//        seekBarPause.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textViewPause.setText(String.valueOf(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//
//        seekBarTraining.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textViewTraining.setText(String.valueOf(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        return view;
//    }
//
//
//    protected void managerOfSound(String theText) {
//        if (mediaPlayer != null) {
//            mediaPlayer.reset();
//            mediaPlayer.release();
//        }
//        if (theText == soundTick) {
//            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.ticks10s);
//        } else {
//            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.onetwo);
//        }
//
//        mediaPlayer.start();
//    }
//
//    @Override
//    public void onInit(int status) {
//        if (status == TextToSpeech.SUCCESS) {
//            int result = textToSpeech.setLanguage(Locale.US);
//            if (result == TextToSpeech.LANG_MISSING_DATA
//                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS", "Language is not supported");
//            } else {
//
//            }
//        } else {
//            Log.e("TTS", "Initilization Failed");
//        }
//    }
//
//    @Override
//    public void onPause() {
//        if (textToSpeech != null) {
//            textToSpeech.stop();
//            textToSpeech.shutdown();
//        }
//        super.onPause();
//    }
//
//    public class CounterClass extends CountDownTimer {
//
//
//        public CounterClass(long secondsInFuture, long countDownInterval) {
//            super(secondsInFuture * 1000, (countDownInterval * 1000) - 100);
//        }
//
//
//        @Override
//        public void onFinish() {
//            Log.i("onFinish ", "finish");
//            playFiveSeconds = true;
//            if (roundCounter > 0) {
//                if (switcherTrainingPause) {
//                    timer = new CounterClass(seriesTime, 1);
//                    textToSpeech.speak(start, TextToSpeech.QUEUE_FLUSH, null);
//                    Log.i("seriesTime ", String.valueOf(seriesTime));
//                    switcherTrainingPause = !switcherTrainingPause;
//                    timer.start();
//                } else {
//                    timer = new CounterClass(pauseTime, 1);
//                    textToSpeech.speak(stop, TextToSpeech.QUEUE_FLUSH, null);
//                    Log.i("pauseTime ", String.valueOf(pauseTime));
//                    switcherTrainingPause = !switcherTrainingPause;
//                    timer.start();
//                }
//                roundCounter--;
//            }
//            if (roundCounter == 0) {
//                textViewTotalTime.setText("Completed.");
//                textToSpeech.speak(finish, TextToSpeech.QUEUE_FLUSH, null);
//            }
//
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            Log.i("milistofinihed = ", String.valueOf(millisUntilFinished / 1000));
//            long milis = millisUntilFinished;
//
//            String hmsTotal = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milis),
//                    TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis)),
//                    TimeUnit.MILLISECONDS.toSeconds(milis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milis)));
//            Log.i("hmsTotal ", hmsTotal);
//            textViewTotalTime.setText(hmsTotal);
//
//            if (milis < 6000 && playFiveSeconds) {
//                playFiveSeconds = false;
//                textToSpeech.speak(five, TextToSpeech.QUEUE_FLUSH, null);
//            }
//
//        }
//
//
//    }
//
//
//}