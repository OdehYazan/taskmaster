package com.example.taskmaster;

import android.annotation.SuppressLint;
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
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    private final String TAG="MainActivity";

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e(TAG, "Could not initialize Amplify", e);
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String UserName = sharedPreferences.getString("UserName", "Enter User Name");
        String Team = sharedPreferences.getString("Team","chose team");

        TextView welcome = findViewById(R.id.GetUserHome);
        welcome.setText(UserName + "`s Tasks");

        createTeams();

        Button signInButton = findViewById(R.id.signin);
        signInButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        Button signUpButton = findViewById(R.id.signup);
        signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        Button signOutButton = findViewById(R.id.signout);
        signOutButton.setOnClickListener(view -> {
            Amplify.Auth.signOut(
                    () -> Log.i("AuthQuickstart", "Signed out successfully"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );
        });


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


        ArrayList <Task> tasks= new ArrayList<Task>();

        if(Team.equals("chose team")){
            tasks = GetData(allTasksRv);
        }
        else{
            tasks = GetData2(allTasksRv);
        }
        Log.i(TAG,tasks.toString());
        allTasksRv.setLayoutManager(new LinearLayoutManager(this));
        allTasksRv.setAdapter(new TaskAdapter(tasks));

//        ArrayList<Task> tasksList = new ArrayList<Task>();
//
//        allTasksRv.setLayoutManager(new LinearLayoutManager(this));
//        allTasksRv.setAdapter(new TaskAdapter(tasksList));

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String UserName = sharedPreferences.getString("UserName", "Enter User Name");
        String Team = sharedPreferences.getString("Team","chose team");




        TextView welcome = findViewById(R.id.GetUserHome);
        welcome.setText(UserName + "`s Tasks");
    }

    private  ArrayList<Task> GetData( RecyclerView allTasks ) {
        Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                allTasks.getAdapter().notifyDataSetChanged();

                return false;
            }
        });

        ArrayList<Task> foundTask=new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Task todo : response.getData()) {
                        foundTask.add(todo);
                        foundTask.toString();
                        Log.i("MyAmplifyApp", foundTask.toString());
                        Log.i("MyAmplifyApp", "Successful query, found posts.");
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        return  foundTask;
    }

    private ArrayList<Task> GetData2(RecyclerView allTaskDataRv ){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String Team = sharedPreferences.getString("Team","chose team");
        System.out.println("-------------------------------------------------------------------");
        System.out.println(Team);
        Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                allTaskDataRv.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        ArrayList<Task> foundTask=new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Task.class,Task.TEAM_ID.contains(Team)),
                response -> {
                    for (Task todo : response.getData()) {
                        foundTask.add(todo);
                        foundTask.toString();
                        Log.i("MyAmplifyApp", foundTask.toString());
                        Log.i("MyAmplifyApp", "Successful query, found posts.");
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        return  foundTask;
    }


    private void createTeams(){

        AtomicBoolean resData= new AtomicBoolean(false);
        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    if(response.getData().getRequestForNextResult()==null){
                        System.out.println("************************************************");
                        System.out.println(response.getData().getRequestForNextResult());
                        resData.set(true);
                        Log.i("Teams", "Successful query, found teams.");
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
        if(resData.equals(false)){
            Team todo1 = Team.builder()
                    .name("Team 1")
                    .id("1")
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(todo1),
                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
            Team todo2 = Team.builder()
                    .name("Team 2")
                    .id("2")
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(todo2),
                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
            Team todo3 = Team.builder()
                    .name("Team 3")
                    .id("3")
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(todo3),
                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
        } }
}