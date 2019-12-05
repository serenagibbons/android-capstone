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

import java.text.SimpleDateFormat;


public class FeedAdapter extends FirestoreRecyclerAdapter<Task, FeedAdapter.FeedHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     */

    private Context mContext;

    public FeedAdapter(Context context, FirestoreRecyclerOptions<Task> options) {
        super(options);
        mContext = context;

    }

    @Override
    protected void onBindViewHolder(FeedHolder holder, int i, final Task task) {
        holder.taskName.setText(task.getM_TaskName());
        //Timestamp t = task.getM_DueDate();
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("MM/dd/yyyy");
        try{
            dateString = sdfr.format( task.getM_DueDate() );
        }catch (Exception ex ){
            ex.printStackTrace();
        }
        holder.deadline.setText(dateString);
        //holder.posted.setText(task.getM_PostedTime());
        holder.desc.setText(task.getM_TaskDescription());


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailedTaskActivity.class);
                intent.putExtra("taskit title", task.getM_TaskName());
                intent.putExtra("taskit creator", task.getM_Creator());
                intent.putExtra("taskit priority", task.getM_Importance());
                intent.putExtra("taskit deadline", task.getM_DueDate());
                intent.putExtra("taskit description", task.getM_TaskDescription());
                //intent.putExtra("taskit posted date", task.getM_PostedTime());
                mContext.startActivity(intent);
            }
        });
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
        //TextView posted;
        TextView desc;

        public FeedHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parent_layout);
            //image = itemView.findViewById(R.id.icon);
            taskName = itemView.findViewById(R.id.name_task);
            deadline = itemView.findViewById(R.id.deadline_time);
            //posted = itemView.findViewById(R.id.posted_date);
            desc = itemView.findViewById(R.id.task_description);
        }
    }
}