package com.example.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jano.trening.R;

public class TrainingConfigurationActivity extends AppCompatActivity {

    private EditText trainingTime, pauseTime, series;
    private Button btnSave, btnCancel;
    String trainingType;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_configuration);

        sharedPref = getSharedPreferences(getString(R.string.shared_pref_file), 0);

        Intent intent = getIntent();
        trainingType = intent.getStringExtra(getString(R.string.training_type));

        trainingTime = (EditText) findViewById(R.id.trainingTime);
        pauseTime = (EditText) findViewById(R.id.pauseTime);
        series = (EditText) findViewById(R.id.series);

        // load saved values into the boxes
        loadValues();

        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPref.edit();
                editor.putString(trainingType + getString(R.string.training_time), trainingTime.getText().toString());
                editor.putString(trainingType + getString(R.string.pause_time), pauseTime.getText().toString());
                editor.putString(trainingType + getString(R.string.series), series.getText().toString());
                editor.commit();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training_configuration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadValues() {
        trainingTime.setText(sharedPref.getString(trainingType + getString(R.string.training_time), null));
        pauseTime.setText(sharedPref.getString(trainingType + getString(R.string.pause_time), null));
        series.setText(sharedPref.getString(trainingType + getString(R.string.series), null));
    }
}
