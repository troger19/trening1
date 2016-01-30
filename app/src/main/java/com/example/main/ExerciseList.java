package com.example.main;

/**
 * Created by jan.babel on 18/08/2015.
 */


import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExerciseList extends ListFragment {
    //
    private Resources res;
    private String[] exerciseList;

    // Array of all images in res/drawable
    int[] exerciseArray = new int[]{
            R.drawable.archer_push_up_small,
            R.drawable.clapping_push_up_small,
            R.drawable.close_push_up_small,
            R.drawable.decline_push_up_small,
            R.drawable.diamond_push_up_small,
            R.drawable.elevated_one_legged_push_up_small,
            R.drawable.l_sit_small,
            R.drawable.one_arm_ball_push_up_small,
            R.drawable.one_arm_knee_push_up_small,
            R.drawable.one_arm_push_up_small,
            R.drawable.one_legged_knee_push_up_small,
            R.drawable.planche_push_up_small,
            R.drawable.push_up_rotate_small,
            R.drawable.push_up_small,
            R.drawable.raised_leg_push_up_small,
            R.drawable.russian_push_up_small,
            R.drawable.side_side_push_up_small,
            R.drawable.spiderman_push_up_small,
            R.drawable.staggered_push_up_small,
            R.drawable.tuck_planche_small,
            R.drawable.wide_push_up_small
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        res = getResources();
        exerciseList = res.getStringArray(R.array.exercise_array);
        // Each row in the list stores exercise name, description and image
        List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < exerciseArray.length; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("exerciseTitle", exerciseList[i]);
            hm.put("image", Integer.toString(exerciseArray[i]));
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"exerciseTitle", "image"};

        // Ids of views in movies_list
        int[] to = {R.id.exerciseTitle, R.id.exerciseImage};

        // Instantiating an adapter to store each items
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.exercise_list, from, to);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        Intent intent = new Intent(getActivity(), MoviesActivity.class);
//        String movieName = moviesDescription[position];
//        intent.putExtra(getString(R.string.selected_movie), movieName);
//        startActivity(intent);
        System.out.println("kuuruvaaa");
    }


}