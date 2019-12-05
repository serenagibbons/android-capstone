package com.example.androidcapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class DetailedTaskActivity extends AppCompatActivity {

    TextView title, creator, priority, deadline, postDate, description;
    Button acceptBtn;
    boolean isPersonalTask;

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
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acceptBtn.getText().equals("Accept Task")) {
                    isPersonalTask = true;
                    acceptBtn.setText(getResources().getString(R.string.leave_task));
                }
                else {
                    // else "leave task"
                    isPersonalTask = false;
                    acceptBtn.setText(getResources().getString(R.string.accept_task));
                }

            }
        });

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {


            String createdByString = String.format(getResources().getString(R.string.set_creator), i.getStringExtra("taskit creator"));
            String deadlineString = String.format(getResources().getString(R.string.set_deadline), i.getStringExtra("taskit deadline"));
            String priorityString = String.format(getResources().getString(R.string.set_priority), i.getStringExtra("taskit priority"));
            String descriptionString = String.format(getResources().getString(R.string.set_description), i.getStringExtra("taskit description"));

            title.setText(i.getStringExtra("taskit title"));
            creator.setText(createdByString);
            priority.setText(priorityString);
            deadline.setText(deadlineString);
            postDate.setText(i.getStringExtra("taskit posted date"));
            description.setText(descriptionString);


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