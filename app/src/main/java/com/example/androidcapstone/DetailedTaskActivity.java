package com.example.androidcapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DetailedTaskActivity extends AppCompatActivity {

    TextView title, creator, priority, deadline, postDate, description;
    Button acceptBtn;
    final static String KEY_DESCRIPTION = "m_Privacy";

    private CollectionReference TaskRef = FirebaseFirestore.getInstance().collection("Task");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_task);

        title = findViewById(R.id.title_taskit);
        creator = findViewById(R.id.creator_taskit);
        priority = findViewById(R.id.priority_taskit);
        deadline = findViewById(R.id.deadline_taskit);
        postDate = findViewById(R.id.posted_taskit);
        description = findViewById(R.id.description_taskit);

        acceptBtn = findViewById(R.id.button_accept);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {

            // get extras from the intent
            String createdByString = String.format(getResources().getString(R.string.set_creator), i.getStringExtra("taskit creator"));
            String deadlineString = String.format(getResources().getString(R.string.set_deadline), i.getStringExtra("taskit deadline"));
            String priorityString = String.format(getResources().getString(R.string.set_priority), i.getStringExtra("taskit priority"));
            String descriptionString = String.format(getResources().getString(R.string.set_description), i.getStringExtra("taskit description"));
            String privacy = i.getStringExtra("taskit privacy");
            final String id = i.getStringExtra("Document id");

            // set TextViews of the activity
            title.setText(i.getStringExtra("taskit title"));
            creator.setText(createdByString);
            priority.setText(priorityString);
            deadline.setText(deadlineString);
            postDate.setText(i.getStringExtra("taskit posted date"));
            description.setText(descriptionString);

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

    private void updateDatabase(String privacy) {
        if (privacy.equals("Public")) {

            //create cust object of HashMap type taking key as string and value as object

            HashMap<String, Object> cust1 = new HashMap<>();
            cust1.put("m_Privacy", "Private");
/*
                myRef=db.getReference("padhle");

                //listener which tells about whether data is inserted or not.
                // update command and listeners are added
                myRef.updateChildren(cust1, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Toast.makeText(MainActivity.this,"Inserted successfully",Toast.LENGTH_LONG).show();
                    }
                });

            }


        }*/
        }
    }

}