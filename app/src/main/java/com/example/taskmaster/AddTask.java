package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

public class AddTask extends AppCompatActivity {

    private final String TAG="AddTask";

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

                RadioButton b1 = findViewById(R.id.team1);
                RadioButton b2 = findViewById(R.id.team2);
                RadioButton b3 = findViewById(R.id.team3);

                String id = null;
                if (b1.isChecked()) {
                    id = "1";
                } else if (b2.isChecked()) {
                    id = "2";
                } else if (b3.isChecked()) {
                    id = "3";
                }
//                dataStore(taskTitle, taskBody, taskState, id);

                Intent intent = new Intent(AddTask.this, MainActivity.class);
                startActivity(intent);


                /////////////////////////first AWS try///////////////////////////////////
                     Task todo = Task.builder()
                             .teamId(id)
                             .title(taskTitle)
                             .body(taskBody)
                             .state(taskState)
                             .build();

                  Amplify.API.mutate(
                        ModelMutation.create(todo),
                        response -> Log.i(TAG, "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e(TAG, "Create failed", error)
                );
//         /////////////////////////first AWS try///////////////////////////////////


//                Task task= new Task(taskTitle,taskBody,taskState);
//
//                 Long addTaskID = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);
//
//                System.out.println(
//                        "****************************************************************"
//                        +"Task ID : " + addTaskID
//                );
            }
        });

    }
//    private void dataStore(String title, String body, String state,String id) {
//        Task task = Task.builder().teamId(id).title(title).body(body).state(state).build();
//        Amplify.API.mutate(
//                ModelMutation.create(task),
//                response -> Log.i(TAG, "Added Todo with id: " + response.getData().getId()),
//                error -> Log.e(TAG, "Create failed", error)
//        );
//
//
//    }

}