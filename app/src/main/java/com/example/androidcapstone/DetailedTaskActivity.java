package com.example.androidcapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DetailedTaskActivity extends AppCompatActivity {

    TextView title, creator, deadline, postDate, description;
    Button acceptBtn;
    RatingBar ratingBar;
    int priorityInt;
    final static String KEY_DESCRIPTION = "m_Privacy";

    private CollectionReference TaskRef = FirebaseFirestore.getInstance().collection("Task");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_task);

        title = findViewById(R.id.title_taskit);
        creator = findViewById(R.id.creator_taskit);
        deadline = findViewById(R.id.deadline_taskit);
        postDate = findViewById(R.id.posted_taskit);
        description = findViewById(R.id.description_taskit);
        ratingBar = findViewById(R.id.detailedTaskRating);

        acceptBtn = findViewById(R.id.button_accept);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {

            // get extras from the intent
            String createdByString = String.format(getResources().getString(R.string.set_creator), i.getStringExtra("taskit creator"));
            String deadlineString = String.format(getResources().getString(R.string.set_deadline), i.getStringExtra("taskit deadline"));
            String priorityString = i.getStringExtra("taskit priority");
            String descriptionString = String.format(getResources().getString(R.string.set_description), i.getStringExtra("taskit description"));
            String privacy = i.getStringExtra("taskit privacy");
            final String id = i.getStringExtra("Document id");

            if (priorityString != null) {
                priorityInt = getPriorityInt(priorityString);
            }

            // set TextViews of the activity
            title.setText(i.getStringExtra("taskit title"));
            creator.setText(createdByString);
            deadline.setText(deadlineString);
            postDate.setText(i.getStringExtra("taskit posted date"));
            description.setText(descriptionString);

            // set ratingBar rating
            ratingBar.setRating(priorityInt);
            // prevent user from changing rating
            ratingBar.setIsIndicator(true);

            // set button text
            if (privacy != null) {
                if (privacy.equals("Private")) {
                    acceptBtn.setText(getResources().getString(R.string.leave_task));
                } else {
                    acceptBtn.setText(getResources().getString(R.string.accept_task));
                }
            }


            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id != null) {
                        if (acceptBtn.getText().equals("Accept Task")) { // accept task
                            // set button to "leave task" once clicked
                            acceptBtn.setText(getResources().getString(R.string.leave_task));
                            // update privacy to Public
                            TaskRef.document(id).update(KEY_DESCRIPTION, "Private");
                        } else {  // leave task
                            // set button to "accept task" once clicked
                            acceptBtn.setText(getResources().getString(R.string.accept_task));
                            // update privacy to Private
                            TaskRef.document(id).update(KEY_DESCRIPTION, "Public");
                        }
                    }

                }
            });
        }

    }

    public void shareButtonClicked(View v)
    {
        Intent sendIntent = new Intent();
        String subject = creator.getText().toString() + " has a Taskit for you!";
        String body = "Taskit: " + title.getText().toString() + "\nDue: " + deadline.getText().toString() + "\n\nJoin Taskr or log in now to accept!";
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.putExtra(Intent.EXTRA_TEXT, body);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Share using:");
        startActivity(shareIntent);
    }

    // get integer value of the priority
    private int getPriorityInt(String rating) {
        switch (rating) {
            case "3":
                return 3;
            case "2":
                return 2;
            case "1":
                return 1;
            default:
                return 0;
        }
    }
}