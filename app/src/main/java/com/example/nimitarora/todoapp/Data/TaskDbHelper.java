package com.example.nimitarora.todoapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceFragment;

import com.example.nimitarora.todoapp.Task;
import com.example.nimitarora.todoapp.TaskAdapter;

/**
 * Created by Nimit Arora on 10/26/2017.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    public static final String TYPE_TEXT="TEXT";
    public static final String CREATE_STATEMENT="CREATE TABLE "+TaskContract.taskEntry.TABLE_NAME+"( "
                                                +TaskContract.taskEntry.TASK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                +TaskContract.taskEntry.COLUMN_TITLE+" "+TYPE_TEXT+", "
                                                + TaskContract.taskEntry.COLUMN_DUEDATE+" "+TYPE_TEXT+", "
                                                +TaskContract.taskEntry.CREATE_DATE+" "+TYPE_TEXT+", "+
                                                TaskContract.taskEntry.COLUMN_DESCRIPTION+" "+TYPE_TEXT+" );";
    public static final String DATABASE_NAME="tasks.db";
    public static final int DATABASE_VERSION=1;

    public static final String DROP_STATEMENT="DROP TABLE IF EXISTS"+TaskContract.taskEntry.TABLE_NAME;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STATEMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_STATEMENT);
        onCreate(sqLiteDatabase);
    }
}
