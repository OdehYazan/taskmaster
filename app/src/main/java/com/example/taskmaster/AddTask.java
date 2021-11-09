package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

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

                     Task todo = Task.builder()
                        .title(taskTitle)
                        .body(taskBody)
                        .state(taskState)
                        .build();

                  Amplify.API.mutate(
                        ModelMutation.create(todo),
                        response -> Log.i("AddTask", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("AddTask", "Create failed", error)
                );
//
//                Task task= new Task(taskTitle,taskBody,taskState);
//
//           Long addTaskID = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);
//
//                System.out.println(
//                        "****************************************************************"
//                        +"Task ID : " + addTaskID
//                );
            }
        });

    }
}