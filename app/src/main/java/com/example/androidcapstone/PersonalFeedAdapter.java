package com.example.androidcapstone;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PersonalFeedAdapter extends RecyclerView.Adapter<PersonalFeedAdapter.PersonalFeedViewHolder> {

    private List<TaskData> task = new ArrayList<>();
    Activity activity;
    public PersonalFeedAdapter(List<TaskData> list, Activity activity) {
        task.addAll(list);
        this.activity = activity;
    }
    @NonNull
    @Override
    public PersonalFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.personal_feed_item, parent, false);
        PersonalFeedViewHolder holder = new PersonalFeedViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalFeedViewHolder holder, int i) {
        TaskData data = task.get(i);

        holder.taskName.setText(data.task);
        holder.deadline.setText(data.date);
    }

    @Override
    public int getItemCount() {
        return task.size();
    }

    public class PersonalFeedViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView taskName;
        public TextView deadline;

        public PersonalFeedViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.icon);
            taskName = itemView.findViewById(R.id.name_task);
            deadline = itemView.findViewById(R.id.deadline_time);
        }
    }
}
