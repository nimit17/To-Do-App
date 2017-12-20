package com.example.nimitarora.todoapp;

/**
 * Created by Nimit Arora on 10/25/2017.
 */

public class Task {
    private String TaskName=new String();
    private String creatDate=new String();
    private String DueDate=new String();
    private String Description=new String();

    public String getDescription() {
        return Description;
    }

    Task(String taskName, String creatDate, String DueDate)
   {
       this.TaskName=taskName;
       this.creatDate=creatDate;
       this.DueDate=DueDate;
   }

    public String getCreatDate() {
        return creatDate;
    }

    public String getDueDate() {
        return DueDate;
    }

    public String getTaskName() {

        return TaskName;
    }
}
