package com.example.nimitarora.todoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nimitarora.todoapp.Data.TaskContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskEditorActivity extends AppCompatActivity {
    private static final String TAG = "";
    private  EditText taskN;
    private  EditText description;
    private  DatePicker datePicker;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        taskN=(EditText) findViewById(R.id.task);
        description=(EditText) findViewById(R.id.descp);
        datePicker=(DatePicker) findViewById(R.id.date_picker);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                saveDetails();
                Snackbar.make(findViewById(R.id.CreateTaskLayout),"Task Saved",Snackbar.LENGTH_LONG).show();
                Intent intent=new Intent(this,MainActivity.class);
                finish();
                startActivity(intent);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDetails() {
        String taskName=taskN.getText().toString().trim();
        String descriptios=description.getText().toString();
        String currentDate=getCurrentDate();
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        DateFormat format = new SimpleDateFormat("dd MMMM YYYY");
        String dueDate = format.format(calendar.getTime());

        ContentValues values=new ContentValues();
        values.put(TaskContract.taskEntry.COLUMN_TITLE,taskName);
        values.put(TaskContract.taskEntry.COLUMN_DESCRIPTION,descriptios);
        values.put(TaskContract.taskEntry.COLUMN_DUEDATE,dueDate);
        values.put(TaskContract.taskEntry.CREATE_DATE,currentDate);

        if(taskName.isEmpty()){
            Toast.makeText(TaskEditorActivity.this,"TASK NAME CANNOT BE EMPTY !!",Toast.LENGTH_LONG).show();
        }
        else {
            getContentResolver().insert(TaskContract.taskEntry.CONTENT_URI, values);
        }


      }
    private String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("dd MMMM YYYY");
        String date= df.format(Calendar.getInstance().getTime());
        return date;
    }
}
