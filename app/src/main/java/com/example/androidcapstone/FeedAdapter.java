package com.example.androidcapstone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.Model.Task;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends FirestoreRecyclerAdapter<Task, FeedAdapter.FeedHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     *
     *
     */

    public FeedAdapter(FirestoreRecyclerOptions<Task> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(FeedHolder holder, int i, Task task) {
        holder.taskName.setText(task.getM_TaskName());
        //holder.deadline.setText(task.getM_DueDate());
        //holder.posted.setText(task.getM_PostedTime());
        holder.desc.setText(task.getM_TaskDescription());
    }

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item,
                parent, false);
        return new FeedHolder(v);
    }

    class FeedHolder extends RecyclerView.ViewHolder {

        //LinearLayout parentLayout;
        //ImageView image;
        TextView taskName;
        //TextView deadline;
        //TextView posted;
        TextView desc;

        public FeedHolder(@NonNull View itemView) {
            super(itemView);

            //parentLayout = itemView.findViewById(R.id.parent_layout);
            //image = itemView.findViewById(R.id.icon);
            taskName = itemView.findViewById(R.id.name_task);
            //deadline = itemView.findViewById(R.id.deadline_time);
            //posted = itemView.findViewById(R.id.posted_date);
            desc = itemView.findViewById(R.id.task_description);
        }
    }
}