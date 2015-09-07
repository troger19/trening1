package com.example.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class TrainingConfigurationActivity extends AppCompatActivity {
/*
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

*/

    private LinearLayout mLayout, firstLayout;
    private Spinner spinner;
    private Button mButton,btnSave, btnCancel;
    private EditText editTraining, editPause,editSeries;
    private Resources res;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String trainingType, trainingTime, pauseTime, series;
    private TinyDB tinyDB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_configuration);

        sharedPref = getSharedPreferences(getString(R.string.shared_pref_file), 0);
        tinyDB = new TinyDB(this);

        Intent intent = getIntent();
        trainingType = intent.getStringExtra(getString(R.string.training_type));

        mLayout = (LinearLayout) findViewById(R.id.linearLayoutMain);
//        firstLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        firstLayout = (LinearLayout) findViewById(R.id.placeholder);
//        spinner = (Spinner) findViewById(R.id.spinner1);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(onClick());

        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(onSaveClick());
        editTraining= (EditText) findViewById(R.id.editTraining);
        editPause= (EditText) findViewById(R.id.editPause);
        editSeries= (EditText) findViewById(R.id.editSeries);

        res = getResources();
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                mLayout.addView(createNewSpinner());
//                mLayout.addView(createNewLinearLayout());
                firstLayout.addView(createNewLinearLayout());
            }
        };
    }


    private View.OnClickListener onSaveClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int dropDownsCount =  firstLayout.getChildCount();
//                editor = sharedPref.edit();
                ArrayList<String> exerciseList = new ArrayList<>();
                for (int i=0; i<dropDownsCount;i++) {
//                    editor.putString(trainingType + "_exercise" + i,  firstLayout.getChildAt(i).toString());
                    LinearLayout linearLayout = (LinearLayout) firstLayout.getChildAt(i);
                    Spinner tempSpinner = (Spinner) linearLayout.getChildAt(0);
                    exerciseList.add(tempSpinner.getSelectedItem().toString());
                }
//                editor.commit();
                tinyDB.putListString(trainingType, exerciseList);
                tinyDB.putString(trainingType + getString(R.string.training_time), editTraining.getText().toString());
                tinyDB.putString(trainingType + getString(R.string.pause_time), editPause.getText().toString());
                tinyDB.putString(trainingType + getString(R.string.series), editSeries.getText().toString());

                //check for not empty
                if(TextUtils.isEmpty(editTraining.getText())) {
                    editTraining.setError("Put training time");
                    return;
                }
                if(editTraining.getText().toString().startsWith("0")){
                    editTraining.setError("Training time cannot be 0");
                    return;
                }

                if(TextUtils.isEmpty(editPause.getText())) {
                    editPause.setError("Put pause time");
                    return;
                }
                if(editPause.getText().toString().startsWith("0")){
                    editPause.setError("Pause time cannot be 0");
                    return;
                }
                if(TextUtils.isEmpty(editSeries.getText())) {
                    editSeries.setError("Put number of series");
                    return;
                }
                if(editSeries.getText().toString().startsWith("0")) {
                    editSeries.setError("Number of series cannot be 0");
                    return;
                }

                Toast.makeText(TrainingConfigurationActivity.this, "Training Saved", Toast.LENGTH_SHORT).show();
                TrainingConfigurationActivity.this.finish();
            }
        };
    }

//
//    private Spinner createNewSpinner() {
//        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        final Spinner spinner = new Spinner(this);
//        String[] items = res.getStringArray(R.array.exercises_arrays);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
//        spinner.setAdapter(adapter);
//        spinner.setLayoutParams(lparams);
//        return spinner;
//    }

    private LinearLayout createNewLinearLayout(){
        LinearLayout linearLayout = new LinearLayout(this);
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final Spinner spinner = new Spinner(this);
        String[] items = res.getStringArray(R.array.exercises_arrays);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setLayoutParams(lparams);
        linearLayout.addView(spinner);

        ViewGroup layout = (ViewGroup) mButton.getParent();
        if(null!=layout) //for safety only  as you are doing onClick
            layout.removeView(mButton);
        linearLayout.addView(mButton);

        return linearLayout;

    }
}

