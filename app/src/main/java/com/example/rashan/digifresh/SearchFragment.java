package com.example.rashan.digifresh;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Rashan on 02-12-2016.
 */

public class SearchFragment extends Fragment {

RecyclerView fragmentRecyclerView;

    public SearchFragment()
    {


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.search_fragment, container, false);
        fragmentRecyclerView=(RecyclerView)v.findViewById(R.id.fragmentRecyclerView);

        return v;

    }



    public void setFragmentRecyclerView( Activity activity,List<SabziDetailsPojo> filteredList)
    {
        fragmentRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        fragmentRecyclerView.setAdapter(new MyAdapter(activity, filteredList));
    }
}
