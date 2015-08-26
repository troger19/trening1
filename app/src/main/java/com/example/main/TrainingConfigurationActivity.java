package com.example.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.jano.trening.R;


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
    private Resources res;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_configuration);
        mLayout = (LinearLayout) findViewById(R.id.linearLayoutMain);
//        firstLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        firstLayout = (LinearLayout) findViewById(R.id.placeholder);
        spinner = (Spinner) findViewById(R.id.spinner1);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(onClick());
//        btnSave = (Button) findViewById(R.id.btnSave);
//        btnCancel = (Button) findViewById(R.id.btnCancel);

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

    private Spinner createNewSpinner() {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final Spinner spinner = new Spinner(this);
        String[] items = res.getStringArray(R.array.exercises_arrays);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setLayoutParams(lparams);
        return spinner;
    }

    private LinearLayout createNewLinearLayout(){
        LinearLayout linearLayout = new LinearLayout(this);
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final Spinner spinner = new Spinner(this);
        String[] items = res.getStringArray(R.array.exercises_arrays);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setLayoutParams(lparams);
//        Button button = new Button(this);
//        button.setText("fdafd");
        linearLayout.addView(spinner);

        ViewGroup layout = (ViewGroup) mButton.getParent();
        if(null!=layout) //for safety only  as you are doing onClick
            layout.removeView(mButton);
//        getParent().r
        linearLayout.addView(mButton);

        return linearLayout;

    }
}

