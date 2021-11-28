package com.example.taskmaster;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import androidx.activity.result.ActivityResult;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;


import java.io.IOException;
import java.util.Date;

public class AddTask extends AppCompatActivity {
    String img = "";
    private String uploadedFileNames;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    private final String TAG="AddTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            onChooseFile(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        findViewById(R.id.btnUploadFile).setOnClickListener(view -> {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("*/*");
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            someActivityResultLauncher.launch(chooseFile);
        });

        Button addTaskButton = findViewById(R.id.button5);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

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

                String url = sharedPreferences.getString("fileUrl","null");
                Log.i("onChooseFile", "onClick: ========>" + url);

//                dataStore(taskTitle, taskBody, taskState, id);

                Intent intent = new Intent(AddTask.this, MainActivity.class);
                startActivity(intent);


                /////////////////////////first AWS try///////////////////////////////////
                     Task todo = Task.builder()
                             .teamId(id)
                             .title(taskTitle)
                             .body(taskBody)
                             .state(taskState)
                             .fileName(url)
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
    private void onChooseFile(ActivityResult activityResult) throws IOException {

        Uri uri = null;
        if (activityResult.getData() != null) {
            uri = activityResult.getData().getData();
        }
        assert uri != null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        Date date = new Date();
        String uploadedFileName = formatter.format(date) + "." + getMimeType(getApplicationContext(), uri);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        File uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFile");
        Log.i("URI", "onChooseFile: URI =>>>>" + uri);
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            FileUtils.copyToFile(inputStream, uploadFile);
        } catch (Exception exception) {
            Log.e("onChooseFile", "onActivityResult: file upload failed" + exception.toString());
        }

        Amplify.Storage.uploadFile(
                uploadedFileName,
                uploadFile,
                success -> {
                    Log.i("onChooseFile", "uploadFileToS3: succeeded " + success.getKey());
                    Amplify.Storage.getUrl(success.getKey(),
                            urlSuccess->{
                                Log.i("onChooseFile", "onChooseFile: " + urlSuccess.getUrl().toString());
                                sharedPreferences.edit().putString("fileUrl",urlSuccess.getUrl().toString()).apply();
                            },
                            urlError->{});
                },
                error -> Log.e("onChooseFile", "uploadFileToS3: failed " + error.toString())
        );
        uploadedFileNames = uploadedFileName;
    }


    public static String getMimeType(Context context, Uri uri) {
        String extension;

        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
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