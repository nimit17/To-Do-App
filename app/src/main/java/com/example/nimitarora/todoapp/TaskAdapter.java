package com.example.nimitarora.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Nimit Arora on 10/25/2017.
 */

public class TaskAdapter extends CursorAdapter{


    public TaskAdapter(Context context, Cursor c) {
        super(context, c,0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.task_view,viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView task=(TextView) view.findViewById(R.id.taskName);
        TextView createdDate=(TextView) view.findViewById(R.id.date);
        TextView duedate=(TextView) view.findViewById(R.id.dueDate);

        String taskName=cursor.getString(cursor.getColumnIndexOrThrow("Title"));
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");


        Date DueDate = Calendar.getInstance().getTime();
        Date CreateDate=Calendar.getInstance().getTime();
        try {
            DueDate=df.parse(cursor.getString(cursor.getColumnIndexOrThrow("Due_Date")));


        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            CreateDate=df.parse(cursor.getString(cursor.getColumnIndexOrThrow("Create_Date")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task.setText(taskName);
        createdDate.setText(df.format(CreateDate));
        duedate.setText(df.format(DueDate));
    }
}

