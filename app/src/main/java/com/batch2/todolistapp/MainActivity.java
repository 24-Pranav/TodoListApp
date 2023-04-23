package com.batch2.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
FragmentManager fm;
FragmentTransaction ft;
Spinner s;
    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        loadFragment(new TopBarFragment());

        tasks = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void addTask(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String task = editText.getText().toString().trim();
        if (!task.isEmpty()) {
            tasks.add(task);
            adapter.notifyDataSetChanged();
            editText.setText("");
        }
    }
    public void loadFragment(Fragment fragment){
        fm=getFragmentManager();
        ft=fm.beginTransaction();
        ft.replace(R.id.linearLayout,fragment);
        ft.commit();
    }
}
