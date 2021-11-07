package com.example.taskmaster;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    ArrayList<Task> tasks = new ArrayList<>();

    public TaskAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        // create a model object
        public Task task;

        // create the view object
         View itemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("my Adapter", "Element "+ getAdapterPosition() + " clicked");
                    Intent goToTask = new Intent(view.getContext(), TaskDetailPage.class);
                    goToTask.putExtra("taskName",task.title);
                    goToTask.putExtra("body",task.body);
                    goToTask.putExtra("state",task.state);
                    view.getContext().startActivity(goToTask);
                }
            });
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_task,viewGroup,false);

        TaskViewHolder taskViewHolder =new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {

        taskViewHolder.task = tasks.get(position);
        TextView taskTitle = taskViewHolder.itemView.findViewById(R.id.taskTitle);
        TextView taskBody = taskViewHolder.itemView.findViewById(R.id.taskBody);
        TextView taskState = taskViewHolder.itemView.findViewById(R.id.taskState);

        taskTitle.setText(taskViewHolder.task.title);
        taskBody.setText(taskViewHolder.task.body);
        taskState.setText(taskViewHolder.task.state);

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
