package com.example.androidcapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
            title.setText(i.getStringExtra("taskit title"));
            creator.setText(i.getStringExtra("taskit creator"));
            priority.setText(i.getStringExtra("taskit priority"));
            deadline.setText(i.getStringExtra("taskit deadline"));
            postDate.setText(i.getStringExtra("taskit posted date"));
            description.setText(i.getStringExtra("taskit description"));


        }

    }

}