package com.example.androidcapstone;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.PersonalFeedViewHolder> {

    private List<Task> task = new ArrayList<>();
    private Activity activity;
    public FeedAdapter(List<Task> list, Activity activity) {
        task.addAll(list);
        this.activity = activity;
    }
    @NonNull
    @Override
    public PersonalFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.feed_item, parent, false);
        return new PersonalFeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalFeedViewHolder holder, int i) {
        Task data = task.get(i);

        holder.taskName.setText(data.getM_TaskName());
        holder.deadline.setText(data.getM_DueDate());
        holder.posted.setText(data.getM_PostedTime());
        holder.desc.setText(data.getM_TaskDescription());
    }

    @Override
    public int getItemCount() {
        return task.size();
    }

     class PersonalFeedViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView taskName;
        TextView deadline;
        TextView posted;
        TextView desc;

        PersonalFeedViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.icon);
            taskName = itemView.findViewById(R.id.name_task);
            deadline = itemView.findViewById(R.id.deadline_time);
            posted = itemView.findViewById(R.id.posted_date);
            desc = itemView.findViewById(R.id.task_description);

        }
    }
}
