package com.batch2.todolistapp;

import android.os.Bundle;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Spinner;


public class TopBarFragment extends Fragment {
Spinner s;

    public TopBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_top_bar,container,false);
        s=(Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,new String[]{"All Lists","Default","Personal","Shopping","Wishlists","Work","Finished"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        SearchView search = v.findViewById(R.id.search_view);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.inflate(R.menu.actionbarr_main);
                popupMenu.show();
            }
        });

        ImageView more = v.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.inflate(R.menu.more_menu);
                popupMenu.show();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}