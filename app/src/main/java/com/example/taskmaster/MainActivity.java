package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button allTasksBtn = findViewById(R.id.allTasks);
        allTasksBtn.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, AllTasks.class);
            startActivity(intent1);
        });

        Button addTasksBtn = findViewById(R.id.addTasks);

        addTasksBtn.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this,AddTask.class);
            startActivity(intent1);
        });

        findViewById(R.id.Task1).setOnClickListener(view -> {

            Intent goToTask1 = new Intent(MainActivity.this, TaskDetailPage.class);

            TextView text = findViewById(R.id.Task1);
            String task1Name = text.getText().toString();
            goToTask1.putExtra("taskName",task1Name);

            startActivity(goToTask1);

        });
        findViewById(R.id.Task2).setOnClickListener(view -> {

            Intent goToTask2 = new Intent(MainActivity.this, TaskDetailPage.class);

            TextView text = findViewById(R.id.Task2);
             String task2Name = text.getText().toString();
             goToTask2.putExtra("taskName",task2Name);

            startActivity(goToTask2);

        });
        findViewById(R.id.Task3).setOnClickListener(view -> {

            Intent goToTask3 = new Intent(MainActivity.this, TaskDetailPage.class);

            TextView text = findViewById(R.id.Task3);
            String task3Name = text.getText().toString();
            goToTask3.putExtra("taskName",task3Name);

            startActivity(goToTask3);
        });
        findViewById(R.id.SittingHome).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsPage.class);
            startActivity(intent);
        });


    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String UserName = sharedPreferences.getString("UserName","Go to Sittings");

        TextView welcome = findViewById(R.id.GetUserHome);
        welcome.setText(UserName+"`s Tasks");
    }
}