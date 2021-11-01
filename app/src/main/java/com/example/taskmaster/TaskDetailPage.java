package com.example.taskmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detailpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String task1Name = intent.getExtras().getString("taskName");
        TextView text = findViewById(R.id.TaskName);
        text.setText(task1Name);

//        String task2Name = intent.getExtras().getString("task2Name");
//        TextView text2 = findViewById(R.id.TaskName);
//        text2.setText(task2Name);
//
//        String task3Name = intent.getExtras().getString("task3Name");
//        TextView text3 = findViewById(R.id.TaskName);
//        text3.setText(task3Name);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}