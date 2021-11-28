package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    ArrayList<Task> tasks = new ArrayList<>();

    public TaskAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }



        //
//        // create a model object
//        public Task task;
//
//        // create the view object
//         View itemView;
//
//        public TaskViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.itemView = itemView;
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.i("my Adapter", "Element "+ getAdapterPosition() + " clicked");
//                    Intent goToTask = new Intent(view.getContext(), TaskDetailPage.class);
//                    goToTask.putExtra("taskName",task.getTitle());
//                    goToTask.putExtra("body",task.getBody());
//                    goToTask.putExtra("state",task.getState());
//                    goToTask.putExtra("FileName",task.getFileName());
//                    view.getContext().startActivity(goToTask);
//                }
//            });
//        }
//    }
//
//    @NonNull
//    @Override
//    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
//        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_task,viewGroup,false);
//
//        TaskViewHolder taskViewHolder =new TaskViewHolder(view);
//        return taskViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
//
//        taskViewHolder.task = tasks.get(position);
//        TextView taskTitle = taskViewHolder.itemView.findViewById(R.id.taskTitle);
//        TextView taskBody = taskViewHolder.itemView.findViewById(R.id.taskBody);
//        TextView taskState = taskViewHolder.itemView.findViewById(R.id.taskState);
//        ImageView taskImage = taskViewHolder.liner.itemView.findViewById(R.id.taskImageDetail);
//
//        taskTitle.setText(taskViewHolder.task.getTitle());
//        taskBody.setText(taskViewHolder.task.getBody());
//        taskState.setText(taskViewHolder.task.getState());
//        taskViewHolder.
//    }
//
//    @Override
//    public int getItemCount() {
//        return tasks.size();
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            Context context = viewHolder.itemView.getContext();

            Task task = tasks.get(position);
            viewHolder.textViewTitle.setText(task.getTitle());
            viewHolder.textViewBody.setText(task.getBody());
            viewHolder.textViewState.setText(task.getState());
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("my Adapter", "Element " + viewHolder.getAdapterPosition() + " clicked");

                    Toast.makeText(context, "Submitted!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, TaskDetailPage.class);

                    String title = viewHolder.textViewTitle.getText().toString();
                    intent.putExtra("title", title);
                    String body = viewHolder.textViewBody.getText().toString();
                    intent.putExtra("body", body);
                    String state = viewHolder.textViewState.getText().toString();
                    intent.putExtra("state", state);
                    String name = task.getFileName();
                    intent.putExtra("Filename", name);


//                String Task1 = viewHolder.textViewTitle.getText().toString();
//                editor.putString("TaskName", Task1);
//                editor.apply();
                    context.startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView textViewTitle;
            public TextView textViewBody;
            public TextView textViewState;
            public LinearLayout linearLayout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitle = (TextView) itemView.findViewById(R.id.title);
                textViewBody = (TextView) itemView.findViewById(R.id.body);
                textViewState = (TextView) itemView.findViewById(R.id.state);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.layout);

            }
        }
//    }
    }

