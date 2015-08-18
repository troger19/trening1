package com.example.main;

/**
 * Created by jan.babel on 18/08/2015.
 */


import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jano.trening.R;

public class MyListFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Linux", "OS/2" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        String item = (String) getListAdapter().getItem(position);
//        Toast.makeText(getActivity(), item + " selected", Toast.LENGTH_LONG).show();


        Fragment fragment = new FragmentThree();

        Bundle args = new Bundle();


//        fragment.setArguments(args);
//        FragmentManager frgManager = getFragmentManager();
//        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
//                .commit();
        getChildFragmentManager().beginTransaction().replace(R.id.detailFragment, fragment).addToBackStack(null).commit();




    }
}