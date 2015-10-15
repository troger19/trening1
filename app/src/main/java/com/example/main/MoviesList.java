package com.example.main;

/**
 * Created by jan.babel on 18/08/2015.
 */


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MoviesList extends ListFragment {

//    private Resources res;
//    private List<String> exercisesList = new ArrayList<>();
//
//    @Override
//        public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        res = getResources();
//
//        String[] movies = res.getStringArray(R.array.movies_array);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, movies) {
//            // colorize the saved training's positions
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                return view;
//            }
//        };
//        setListAdapter(adapter);
//    }
//
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        Intent intent = new Intent(getActivity(), MoviesActivity.class);
//        intent.putExtra(getString(R.string.selected_movie),getListAdapter().getItem(position).toString());
//        startActivity(intent);
//
//    }


    // Array of strings storing country names
    String[] countries = new String[] {
            "India",
            "Pakistan",
            "Sri Lanka",
            "China",
            "Bangladesh",
            "Nepal",
            "Afghanistan",
            "North Korea",
            "South Korea",
            "Japan"
    };

    // Array of integers points to images stored in /res/drawable/
    int[] flags = new int[]{
            R.drawable.india,
            R.drawable.pakistan,
            R.drawable.srilanka,
            R.drawable.china,
            R.drawable.bangladesh,
            R.drawable.nepal,
            R.drawable.afghanistan,
            R.drawable.nkorea,
            R.drawable.skorea,
            R.drawable.japan
    };

    // Array of strings to store currencies
    String[] currency = new String[]{
            "Indian Rupee",
            "Pakistani Rupee",
            "Sri Lankan Rupee",
            "Renminbi",
            "Bangladeshi Taka",
            "Nepalese Rupee",
            "Afghani",
            "North Korean Won",
            "South Korean Won",
            "Japanese Yen"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", "Country : " + countries[i]);
            hm.put("cur","Currency : " + currency[i]);
            hm.put("flag", Integer.toString(flags[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag","txt","cur" };

        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.txt,R.id.cur};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.listview_layout, from, to);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }


}