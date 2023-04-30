package com.batch2.todolistapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ActionBar actionBar;
    DBHelper db;
    FragmentManager fm;
    FragmentTransaction ft;
    Spinner s;
    public ArrayList<String> tasks;
    public ArrayAdapter<String> adapter;

//    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        loadFragment(new TopBarFragment());

//        layout=findViewById(R.id.linearLayout);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.actionbarr_main, (Menu) layout);


//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.HORIZONTAL);
//        layout.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//        ImageView imageView=new ImageView(this);
//        imageView.setImageResource(R.drawable.ic_launcher);
//        imageView.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//        Spinner spinner=new Spinner(this);
//        spinner.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//        layout.addView(imageView);
//        layout.addView(spinner);

//        actionBar=getSupportActionBar();
//        if(actionBar!=null){
//            actionBar.setDisplayShowTitleEnabled(false);
//            actionBar.setCustomView(layout);
//        }
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ActionbarColor)));
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                Gravity.LEFT);
//
//            imageView.setLayoutParams(params);
//            spinner.setLayoutParams(params);


//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        loadFragment(new TopBarFragment());

        db = new DBHelper(this);
        tasks = db.fetchAll();
        adapter = new ArrayAdapter<String>(this, R.layout.custom_row_layout, R.id.task_textview, tasks);


        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(MainActivity.this,"Click Again To Delete Task",Toast.LENGTH_SHORT).show();
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
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.actionbarr_main, menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_search:
//                // Do something when menu_item1 is selected
//                return true;
////            case R.id.menu_item2:
//                // Do something when menu_item2 is selected
////                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
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
