package com.batch2.todolistapp;

import android.os.Bundle;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class TopBarFragment extends Fragment {
Spinner s;
String val[]={"All Lists","Default","Personal","Shopping","Wishlists","Work","Finished"};
    public TopBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View v=inflater.inflate(R.layout.fragment_top_bar,container,false);
//        s=(Spinner) v.findViewById(R.id.spinner);
//        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,val);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        s.setAdapter(adapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_bar, container, false);
    }
}