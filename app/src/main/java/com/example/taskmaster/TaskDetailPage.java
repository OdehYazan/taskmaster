package com.example.taskmaster;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TaskDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detailpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

//        String title = intent.getExtras().getString("title", "title");
//        String body = intent.getExtras().getString("body", "body");
//        String state = intent.getExtras().getString("state", "state");
//        String filename = intent.getExtras().getString("Filename", "Filename");
//
//        TextView textView =findViewById(R.id.title);
//        TextView bodyView =findViewById(R.id.body);
//        TextView stateView =findViewById(R.id.state);

//        textView.setText("Task Title:  "+textView);
//        bodyView.setText("Task Body:  "+bodyView);
//        stateView.setText("Task State:  "+stateView);




        String taskTitle = intent.getExtras().getString("taskName");
        TextView title = findViewById(R.id.TaskName);
        title.setText(taskTitle);

        String taskBody = intent.getExtras().getString("body");
        TextView body = findViewById(R.id.body);
        body.setText(taskBody);

        String taskState = intent.getExtras().getString("state");
        TextView state = findViewById(R.id.state);
        state.setText(taskState);

          String taskImage=intent.getExtras().getString("Filename");
          ImageView taskImageDetail = findViewById(R.id.taskImageDetail);
           Picasso.get().load(taskImage).into(taskImageDetail);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}