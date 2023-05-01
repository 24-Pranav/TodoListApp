package com.batch2.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Spinner s;
    public static String table_name;
    ListView lv;
    DBHelp db;

    ImageView more;
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
        more=findViewById(R.id.more);

        db = new DBHelp(MainActivity.this);

        more.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // Show the PopupMenu on long press
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, more);
                    popupMenu.inflate(R.menu.more_menu);

//                    // Step 5: Handle menu item selection
//                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            switch (item.getItemId()) {
//                                case R.id.menu_item_share:
//                                    // Handle "Share" item click
//                                    return true;
//                                case R.id.menu_item_delete:
//                                    // Handle "Delete" item click
//                                    return true;
//                                default:
//                                    return false;
//                            }
//                        }
//                    });

                    popupMenu.show();
                    return true;
                }
                return false;
            }
        });

        adapterspin=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,new String[]{"All Lists","Default","Personal","Shopping","Wishlists","Work","Finished"});
        adapterspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapterspin);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the Spinner
                table_name = parent.getItemAtPosition(position).toString();

                switch (table_name)
                {
                    case "All Lists":
                        tasks = db.fetchAll("default_list");
                        tasks.addAll(db.fetchAll("personal"));
                        tasks.addAll(db.fetchAll("shopping"));
                        tasks.addAll(db.fetchAll("wishlist"));
                        tasks.addAll(db.fetchAll("work"));
                        break;

                    case "Default":
                        tasks = db.fetchAll("default_list");
                        break;

                    case "Personal":
                        tasks = db.fetchAll("personal");
                        break;

                    case "Shopping":
                        tasks = db.fetchAll("shopping");
                        break;

                    case "Wishlists":
                        tasks = db.fetchAll("wishlist");
                        break;

                    case "Work":
                        tasks = db.fetchAll("work");
                        break;

                    case "Finished":
                        tasks = db.fetchAll("finished");
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
                            case "Default":
                                db.deleteTask(String.valueOf(tasks.get(position)),"default_list");
                                break;

                            case "Personal":
                                db.deleteTask(String.valueOf(tasks.get(position)),"personal");
                                break;

                            case "Shopping":
                                db.deleteTask(String.valueOf(tasks.get(position)),"shopping");
                                break;

                            case "Wishlists":
                                db.deleteTask(String.valueOf(tasks.get(position)),"wishlist");
                                break;

                            case "Work":
                                db.deleteTask(String.valueOf(tasks.get(position)),"work");
                                break;
                        }
                        if(table_name.equals("Finished"))
                        {
                            db.deleteTask(String.valueOf(tasks.get(position)),"finished");
                            tasks.remove(position);
                        }
                        else
                        {
                            db.insertData(tasks.remove(position),"finished");
                        }
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
        if (table_name.equals("Finished"))
        {
            Toast.makeText(this,"Here You Only View An Delete Your Deleted Tasks From Other List",Toast.LENGTH_LONG).show();
        }
        else if(task.equals(""))
        {
            Toast.makeText(MainActivity.this,"Please Type Before Add",Toast.LENGTH_SHORT).show();
        }

        else
        {
            switch (table_name) {
                case "Default":
                    db.insertData(task,"default_list");
                    tasks = db.fetchAll("default_list");
                    break;

                case "Personal":
                    db.insertData(task,"personal");
                    tasks = db.fetchAll("personal");
                    break;

                case "Shopping":
                    db.insertData(task,"shopping");
                    tasks = db.fetchAll("shopping");
                    break;

                case "Wishlists":
                    db.insertData(task,"wishlist");
                    tasks = db.fetchAll("wishlist");
                    break;

                case "Work":
                    db.insertData(task,"work");
                    tasks = db.fetchAll("work");
                    break;
            }
            adapterlist = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_row_layout, R.id.task_textview, tasks);
            lv.setAdapter(adapterlist);
            et.setText("");
        }
    }
}