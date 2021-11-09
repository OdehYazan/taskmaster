package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());

            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }




        Button allTasksBtn = findViewById(R.id.allTasks);
        allTasksBtn.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, AllTasks.class);
            startActivity(intent1);
        });

        Button addTasksBtn = findViewById(R.id.addTasks);

        addTasksBtn.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, AddTask.class);
            startActivity(intent1);
        });

        findViewById(R.id.Task1).setOnClickListener(view -> {

            Intent goToTask1 = new Intent(MainActivity.this, TaskDetailPage.class);

            TextView text = findViewById(R.id.Task1);
            String task1Name = text.getText().toString();
            goToTask1.putExtra("taskName", task1Name);

            startActivity(goToTask1);

        });
        findViewById(R.id.Task2).setOnClickListener(view -> {

            Intent goToTask2 = new Intent(MainActivity.this, TaskDetailPage.class);

            TextView text = findViewById(R.id.Task2);
            String task2Name = text.getText().toString();
            goToTask2.putExtra("taskName", task2Name);

            startActivity(goToTask2);

        });
        findViewById(R.id.Task3).setOnClickListener(view -> {

            Intent goToTask3 = new Intent(MainActivity.this, TaskDetailPage.class);

            TextView text = findViewById(R.id.Task3);
            String task3Name = text.getText().toString();
            goToTask3.putExtra("taskName", task3Name);

            startActivity(goToTask3);
        });
        findViewById(R.id.SittingHome).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsPage.class);
            startActivity(intent);
        });
////////////////////////////////ROME/////////////////////////////////////////////////////////
//        AppDatabase.getInstance(getApplicationContext()).taskDao().delete();

//        List<Task> taskDb = AppDatabase.getInstance(getApplicationContext()).taskDao().getAll();
//
//        ArrayList<Task> tasksFromDb = new ArrayList<>();
//        tasksFromDb.add(new Task("Task1", "body1", "new"));
//        tasksFromDb.add(new Task("Task2", "body2", "assigned"));
//        tasksFromDb.add(new Task("Task3", "body3", "in progress"));
//        tasksFromDb.add(new Task("Task4", "body4", "complete"));
//
////        tasksFromDb.addAll(taskDb); same as for loop
//
//        for(Task task:taskDb){
//            tasksFromDb.add(task);
//        }

////////////////////////////////ROME/////////////////////////////////////////////////////////

//        ArrayList<Task> task = new ArrayList<>();
//        task.add(new Task("Task1", "body1", "new"));
//        task.add(new Task("Task2", "body2", "assigned"));
//        task.add(new Task("Task3", "body3", "in progress"));
//        task.add(new Task("Task4", "body4", "complete"));

        RecyclerView allTasksRv = findViewById(R.id.tasksReVi);

        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                allTasksRv.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

       ArrayList<Task> tasksList = new ArrayList<Task>();
        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    for (Task taskTodo : response.getData()) {
                        tasksList.add(taskTodo);
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );
        allTasksRv.setLayoutManager(new LinearLayoutManager(this));
        allTasksRv.setAdapter(new TaskAdapter(tasksList));

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String UserName = sharedPreferences.getString("UserName", "Go to Sittings");

        TextView welcome = findViewById(R.id.GetUserHome);
        welcome.setText(UserName + "`s Tasks");
    }
}