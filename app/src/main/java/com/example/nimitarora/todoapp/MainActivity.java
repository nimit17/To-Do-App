package com.example.nimitarora.todoapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nimitarora.todoapp.Data.TaskContract;
import com.example.nimitarora.todoapp.Data.TaskDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView currentDate;
    private TextView currentDay;
    private ArrayList<Task> tasks=new ArrayList<>();
    private TaskDbHelper mdbhelper;

    public static int id=0;
    private SQLiteDatabase db;
    private ListView listView ;
    TaskAdapter taskAdapter;
    Cursor displayCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contextOfApplication = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mdbhelper = new TaskDbHelper(getBaseContext());
        db=mdbhelper.getWritableDatabase();

        displayCursor=db.rawQuery("SELECT * FROM "+ TaskContract.taskEntry.TABLE_NAME+" ORDER BY "+TaskContract.taskEntry.COLUMN_DUEDATE,null);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ListView) findViewById(R.id.ListView);
        taskAdapter=new TaskAdapter(this,displayCursor);
        listView.setAdapter(taskAdapter);
        listView.setLongClickable(true);

        View emptyView=(View) findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           public static final String TAG = "";

           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent = new Intent(getBaseContext(), InfoActivity.class);
               intent.putExtra("ID",i);

               startActivity(intent);
           }

        });


       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                deleteDialog(i);
                return true;
           }
       });
        currentDate=(TextView) findViewById(R.id.DateToday);
        currentDay=(TextView) findViewById(R.id.Day);
        currentDay.setText(getCurrentDay());
        currentDate.setText(getCurrentDate());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, TaskEditorActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void deleteDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.Sure).setTitle(R.string.Delete)
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("", "onClick: " + MainActivity.getId());
                       deleteItem(i);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void deleteItem(int i) {
        displayCursor.moveToPosition(i);
        int id=displayCursor.getInt(displayCursor.getColumnIndex(TaskContract.taskEntry.TASK_ID));
        db.execSQL("DELETE FROM "+ TaskContract.taskEntry.TABLE_NAME+" WHERE "+ TaskContract.taskEntry.TASK_ID+"="+id+";");
        displayCursor=db.rawQuery("SELECT * FROM "+ TaskContract.taskEntry.TABLE_NAME+" ORDER BY "+TaskContract.taskEntry.COLUMN_DUEDATE,null);
        listView.setAdapter(new TaskAdapter(MainActivity.this,displayCursor));
    }

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
    private String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String date= df.format(Calendar.getInstance().getTime());
        return date;
    }

    private String getCurrentDay() {
        DateFormat df = new SimpleDateFormat("EEEE");
        String day = df.format(Calendar.getInstance().getTime());
        return day;

    }

    public static String getId() {
        return ""+id;
    }
}
