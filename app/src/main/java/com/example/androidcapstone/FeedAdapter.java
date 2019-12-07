package com.example.androidcapstone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.Model.Task;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;


public class FeedAdapter extends FirestoreRecyclerAdapter<Task, FeedAdapter.FeedHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     */

    private OnItemClickListener listener;
    private Context mContext;

    public FeedAdapter(Context context, FirestoreRecyclerOptions<Task> options) {
        super(options);
        mContext = context;

    }

    String dateString, createdOnString;
    int priorityNum;

    @Override
    protected void onBindViewHolder(FeedHolder holder, int i, final Task task) {
        holder.taskName.setText(task.getM_TaskName());

        SimpleDateFormat sdfr = new SimpleDateFormat("MM/dd/yyyy");
        try{
            dateString = sdfr.format(task.getM_DueDate());
            createdOnString = sdfr.format(task.getM_CreatedOnDate());
        }catch (Exception ex ){
            ex.printStackTrace();
        }

        priorityNum = getPriorityNum(task.getM_Importance());
        holder.deadline.setText(dateString);
        holder.priority.setRating(priorityNum);
        holder.posted.setText(createdOnString);
        holder.desc.setText(task.getM_TaskDescription());

    }

    private int getPriorityNum(String rating) {
        switch (rating) {
            case "High":
                return 3;
            case "Medium":
                return 2;
            case "Low":
                return 1;
            default:
                return 0;
        }
    }

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item,
                parent, false);
        return new FeedHolder(v);
    }

    class FeedHolder extends RecyclerView.ViewHolder {

        LinearLayout parentLayout;
        //ImageView image;
        TextView taskName;
        TextView deadline;
        TextView posted;
        RatingBar priority;
        TextView desc;

        public FeedHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parent_layout);
            //image = itemView.findViewById(R.id.icon);
            taskName = itemView.findViewById(R.id.name_task);
            deadline = itemView.findViewById(R.id.deadline_time);
            posted = itemView.findViewById(R.id.posted_date);
            priority = itemView.findViewById(R.id.taskRating);
            desc = itemView.findViewById(R.id.task_description);

            // set onClickListener for recycler view items
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}