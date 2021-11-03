package com.example.taskmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button addTaskButton = findViewById(R.id.button5);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"submitted!", Toast.LENGTH_SHORT).show();

                EditText taskTitleInput = findViewById(R.id.inputTaskTitle);
                String taskTitle = taskTitleInput.getText().toString();

                EditText taskBodyInput = findViewById(R.id.inputTaskBody);
                String taskBody = taskBodyInput.getText().toString();

                EditText taskStateInput= findViewById(R.id.inputTaskState);
                String taskState = taskStateInput.getText().toString();

                Task task= new Task(taskTitle,taskBody,taskState);

           Long addTaskID = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);

                System.out.println(
                        "****************************************************************"
                        +"Task ID : " + addTaskID
                );
            }
        });

    }
}