package com.example.main;

import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jano.trening.R;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class CounterActivity extends Activity {

	private Button btnStart, btnStop;
	private TextView textViewTime;
	private SoundPool sounds;
	private int sndtick;
	MediaPlayer mp        = null;
	String soundTick         = "tick";
   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		
		btnStart = (Button) findViewById(R.id.btnStart);
		btnStop = (Button) findViewById(R.id.btnStop);
		textViewTime=(TextView) findViewById(R.id.textViewTime);
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		sndtick = sounds.load(this, R.raw.ticks10s, 1);
		
		
		textViewTime.setText("00:03:00");
		
		final CounterClass timer = new CounterClass(180000,  1000);
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				timer.start();
				managerOfSound(soundTick);
			}
		});
		
		btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				timer.cancel();
				mp.stop();
			}
		});
		
	}
	
	
	 protected void managerOfSound(String theText) {
	        if (mp != null) {
	            mp.reset();
	            mp.release();
	        }
	        if (theText == soundTick){
	        	 mp = MediaPlayer.create(this, R.raw.ticks10s);
	        }
	        else{
	        	mp = MediaPlayer.create(this, R.raw.onetwo);
	        }
	            
	        mp.start();
	    }
	
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public class CounterClass extends CountDownTimer {

		public CounterClass(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
		textViewTime.setText("Completed.");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			long milis = millisUntilFinished;
			String hms = String.format("%02d:%02d:%02d" , TimeUnit.MILLISECONDS.toHours(milis),
					TimeUnit.MILLISECONDS.toMinutes(milis)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis)),
					TimeUnit.MILLISECONDS.toSeconds(milis)- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milis)));
			System.out.println(hms);
			textViewTime.setText(hms);
			
		}
		
		

	}
	
	
	
	
	
}
