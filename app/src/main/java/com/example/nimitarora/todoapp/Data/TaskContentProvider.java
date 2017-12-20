package com.example.nimitarora.todoapp.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.nimitarora.todoapp.Task;

/**
 * Created by Nimit Arora on 10/26/2017.
 */

public class TaskContentProvider extends ContentProvider {

    public static final int Tasks=100;
    public static final int Task_Id=101;
    public static final UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(TaskContract.CONTENT_AUTHORITY,TaskContract.PATH,Tasks);
        uriMatcher.addURI(TaskContract.CONTENT_AUTHORITY,TaskContract.PATH+"/#",Task_Id);
    }
    TaskDbHelper mdbhelper;
    @Override
    public boolean onCreate() {
        mdbhelper=new TaskDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=mdbhelper.getReadableDatabase();
        Cursor cursor;
        int match=uriMatcher.match(uri);
        switch (match)
        {
            case Tasks :
                cursor=db.query(TaskContract.taskEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                        break;
            case Task_Id :
                selection=TaskContract.taskEntry.TASK_ID+"=?";
                selectionArgs=new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                cursor=db.query(TaskContract.taskEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                break;
            default:
                throw new IllegalArgumentException("Cannot Query");
        }

        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case Tasks:
                return TaskContract.CONTENT_LIST_TYPE;
            case Task_Id:
                return TaskContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db=mdbhelper.getWritableDatabase();
        final int match= uriMatcher.match(uri);
        switch (match)
        {
            case Tasks: return insertPet(uri,contentValues);

            default: throw new IllegalArgumentException();
        }

    }

    private Uri insertPet(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db=mdbhelper.getWritableDatabase();
        long id=  db.insert(TaskContract.taskEntry.TABLE_NAME,null,contentValues);
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db=mdbhelper.getWritableDatabase();
        final int match=uriMatcher.match(uri);
        switch (match)
        {
            case Tasks: return db.delete(TaskContract.taskEntry.TABLE_NAME,s,strings);

            case Task_Id: s= TaskContract.taskEntry.TASK_ID+"=?";
                             strings=new String[] { String.valueOf(ContentUris.parseId(uri))};
                return db.delete(TaskContract.taskEntry.TABLE_NAME,s, strings);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);


        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db=mdbhelper.getWritableDatabase();
        final int match=uriMatcher.match(uri);
        switch (match)
        {
            case Tasks: return db.update(TaskContract.taskEntry.TABLE_NAME,contentValues,s,strings);

            case Task_Id: s= TaskContract.taskEntry.TASK_ID+"=?";
                strings=new String[] { String.valueOf(ContentUris.parseId(uri))};
                return db.update(TaskContract.taskEntry.TABLE_NAME,contentValues,s, strings);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);


        }
    }
}
