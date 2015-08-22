package com.example.main;

/**
 * Created by jan.babel on 18/08/2015.
 */


import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jano.trening.R;

public class SettingsList extends ListFragment {

    private  Resources res;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        res = getResources();
        String[] trainings = res.getStringArray(R.array.trainings_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, trainings);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), TrainingConfigurationActivity.class);
        intent.putExtra(getString(R.string.training_type),getListAdapter().getItem(position).toString());
        startActivity(intent);

    }
}