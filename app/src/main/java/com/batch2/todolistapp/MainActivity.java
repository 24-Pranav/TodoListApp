package com.batch2.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Spinner s;
    public static String table_name;
    ListView lv;
    DBHelp db;
    Personal p;
    Wishlist wl;
    Work w;
    Finished f;
    Shopping shopping;
    public ArrayList<String> tasks;
    public ArrayAdapter<String> adapterlist;
    public ArrayAdapter<String> adapterspin;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.editText);
        lv = findViewById(R.id.listView);
        s = findViewById(R.id.spinner);
        adapterspin=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,new String[]{"Default_list","Personal","Shopping","Wishlists","Work","Finished"});
        adapterspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapterspin);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the Spinner
                table_name = parent.getItemAtPosition(position).toString();

                switch (table_name)
                {
                    case "Default_list":
                        db = new DBHelp(MainActivity.this);
                        tasks = db.fetchAll();
                        break;

                    case "Personal":
                        p = new Personal(MainActivity.this);
                        tasks = p.fetchAll();
                        break;

                    case "Shopping":
                        shopping = new Shopping(MainActivity.this);
                        tasks = shopping.fetchAll();
                        break;

                    case "Wishlists":
                        wl = new Wishlist(MainActivity.this);
                        tasks = wl.fetchAll();
                        break;

                    case "Work":
                        w = new Work(MainActivity.this);
                        tasks = w.fetchAll();
                        break;

                    case "Finished":
                        f = new Finished(MainActivity.this);
                        tasks = f.fetchAll();
                        break;
                }
                adapterlist = new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_row_layout, R.id.task_textview,tasks);
                lv.setAdapter(adapterlist);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int position, long id)
            {
                Toast.makeText(MainActivity.this, "Click Again To Delete Task", Toast.LENGTH_SHORT).show();
                ImageView deleteButton = view.findViewById(R.id.delete_button);
                deleteButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        switch (table_name)
                        {
                            case "Default_list":
                                db.deleteTask(String.valueOf(tasks.get(position)));
                                break;

                            case "Personal":
                                p.deleteTask(String.valueOf(tasks.get(position)));
                                break;

                            case "Shopping":
                                shopping.deleteTask(String.valueOf(tasks.get(position)));
                                break;

                            case "Wishlists":
                                wl.deleteTask(String.valueOf(tasks.get(position)));
                                break;

                            case "Work":
                                w.deleteTask(String.valueOf(tasks.get(position)));
                                break;

                            case "Finished":
                                f.deleteTask(String.valueOf(tasks.get(position)));
                                break;
                        }
                        tasks.remove(position);
                        adapterlist.notifyDataSetChanged();
                        return;
                    }
                });
                return;
            }
        });

    }

    public void addTask(View view)
    {
        String task = et.getText().toString();
        if(task.equals(""))
        {
            Toast.makeText(MainActivity.this,"Please Type Before Add",Toast.LENGTH_SHORT).show();
        }
        else
        {
            switch (table_name) {
                case "Default_list":
                    db.insertData(task);
                    tasks = db.fetchAll();
                    break;

                case "Personal":
                    p.insertData(task);
                    tasks = p.fetchAll();
                    break;

                case "Shopping":
                    shopping.insertData(task);
                    tasks = shopping.fetchAll();
                    break;

                case "Wishlists":
                    wl.insertData(task);
                    tasks = wl.fetchAll();
                    break;

                case "Work":
                    w.insertData(task);
                    tasks = w.fetchAll();
                    break;

                case "Finished":
                    f.insertData(task);
                    tasks = f.fetchAll();
                    break;
            }
            adapterlist = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_row_layout, R.id.task_textview, tasks);
            lv.setAdapter(adapterlist);
            et.setText("");
        }
    }
}