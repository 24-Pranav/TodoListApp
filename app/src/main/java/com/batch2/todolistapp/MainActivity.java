package com.batch2.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    DBHelper db;
    FragmentManager fm;
    FragmentTransaction ft;
    Spinner s;
    public ArrayList<String> tasks;
    public ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        loadFragment(new TopBarFragment());

        db = new DBHelper(this);
        tasks = db.fetchAll();
        adapter = new ArrayAdapter<String>(this, R.layout.custom_row_layout, R.id.task_textview, tasks);


        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView deleteButton = view.findViewById(R.id.delete_button);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteTask(String.valueOf(tasks.get(position)));
                        tasks.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public void addTask(View view)
    {
        EditText editText = (EditText) findViewById(R.id.editText);
        String task = editText.getText().toString().trim();
        if (!task.isEmpty()) {
            tasks.add(task);
            adapter.notifyDataSetChanged();
            editText.setText("");
        }
        db.insertData(tasks);
    }
    public void loadFragment(Fragment fragment){
        fm=getFragmentManager();
        ft=fm.beginTransaction();
        ft.replace(R.id.linearLayout,fragment);
        ft.commit();
    }
}
