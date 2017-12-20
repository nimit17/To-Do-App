package com.example.nimitarora.todoapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.nimitarora.todoapp.Data.TaskContract;
import com.example.nimitarora.todoapp.Data.TaskDbHelper;

public class InfoActivity extends AppCompatActivity {
    private static final String TAG ="" ;
    private TaskDbHelper mdbHelper;
    private TextView title;
    private TextView des;
    private TextView due;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        title=(TextView)findViewById(R.id.titleView);
        des=(TextView) findViewById(R.id.descpriction) ;
        due=(TextView) findViewById(R.id.dueText);

        mdbHelper=new TaskDbHelper(this);

        SQLiteDatabase db=mdbHelper.getWritableDatabase();

        int i=getIntent().getIntExtra("ID",0);

        String id= TaskContract.taskEntry.TASK_ID;
        Cursor cursor=db.rawQuery("SELECT * FROM "+ TaskContract.taskEntry.TABLE_NAME+" ORDER BY "+TaskContract.taskEntry.COLUMN_DUEDATE,null);
        cursor.moveToPosition(i);

        String Title=cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.taskEntry.COLUMN_TITLE));
        String Descp=cursor.getString(cursor.getColumnIndex(TaskContract.taskEntry.COLUMN_DESCRIPTION));
        String dueDate=cursor.getString(cursor.getColumnIndex(TaskContract.taskEntry.COLUMN_DUEDATE));

        title.setText(Title);
        des.setText(Descp);
        due.setText(dueDate);
    }
}
