package com.example.taskmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }
}