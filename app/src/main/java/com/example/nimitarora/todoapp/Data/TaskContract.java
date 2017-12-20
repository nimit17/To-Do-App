package com.example.nimitarora.todoapp.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by Nimit Arora on 10/26/2017.
 */

public class TaskContract implements BaseColumns {
    public static final String CONTENT_AUTHORITY="com.example.android.todo";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH="Task";
    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

    public TaskContract(){

    }

    public static final class taskEntry {

        public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH);
        public static final String TABLE_NAME="Task";
        public static final String TASK_ID=BaseColumns._ID;
        public static final String COLUMN_TITLE="Title";
        public static final String COLUMN_DESCRIPTION="Description";
        public static final String COLUMN_DUEDATE="Due_Date";
        public static final String CREATE_DATE="Create_Date";


    }
}
