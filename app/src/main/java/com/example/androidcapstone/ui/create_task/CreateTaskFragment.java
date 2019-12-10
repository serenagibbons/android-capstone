package com.example.androidcapstone.ui.create_task;

import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.androidcapstone.MapsActivity;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateTaskFragment extends Fragment {
    FirebaseFirestore db;
    private CreateTaskViewModel createTaskViewModel;
    DatePickerDialog picker;
    FirebaseUser fbUser;
    EditText taskName, taskDescription, completedBy, assignedTo, editTextMapLocation;
    Button createTask, pickLocation;
    ToggleButton privacyBtn;
    RatingBar rBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        createTaskViewModel =
                ViewModelProviders.of(this).get(CreateTaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_task, container, false);
        taskName = root.findViewById(R.id.editTextTaskName);
        taskDescription = root.findViewById(R.id.editTextDescription);
        completedBy = root.findViewById(R.id.editTextCompletedByDate);
        assignedTo = root.findViewById(R.id.editTextAssigned);
        editTextMapLocation = root.findViewById(R.id.editTextMapLocation);
        pickLocation = root.findViewById(R.id.btnLocationPicker);
        privacyBtn = root.findViewById(R.id.toggleButtonPrivacy);
        //Priority Bar
        rBar = root.findViewById(R.id.ratingBar1);
        //Create Task Button
        createTask = root.findViewById(R.id.buttonCreateTask);

        fbUser = FirebaseAuth.getInstance().getCurrentUser();

        //Completion Date EditText
        completedBy.setInputType(InputType.TYPE_NULL);
        completedBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                picker = new DatePickerDialog( getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                completedBy.setText(monthOfYear+1 + "/" + (dayOfMonth) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });



        //Map Location Picker
        pickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivity(i);
            }
        });

        //Create Task Button
        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fields
                String m_TaskName = taskName.getText().toString().trim(); //Req
                String m_Description = taskDescription.getText().toString().trim(); //Req
                String m_CompletedBy = completedBy.getText().toString().trim(); //Req
                String m_AssignedTo = assignedTo.getText().toString().trim(); //Req
                String m_Priority = checkRating(); //Req
                String m_MapLocation = editTextMapLocation.getText().toString().trim(); //Optional

                Date date1 = new Date();
                try {
                    date1=new SimpleDateFormat("MM/dd/yyyy").parse(m_CompletedBy);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Assign To
                String m_Privacy = (privacyBtn.isChecked() ? "Public" : "Private");
                //Check all required are filled
                //Firestore instance
                db = FirebaseFirestore.getInstance();

                if(!validateInputs(m_TaskName, m_Description, m_CompletedBy, m_AssignedTo, m_MapLocation)) {
                    CollectionReference dbProducts = db.collection("Task");
                    Task task = new Task( //String m_Creator, String m_AssignedTo, String m_TaskName, String m_TaskDescription,
                            //String m_Importance, String m_Location, Date m_DueDate, String m_Status, String m_Privacy
                            "Kevin", //TODO Get current user
                            false,
                            m_AssignedTo, //TODO Get user contacts
                            m_TaskName,
                            m_Description,
                            m_Priority,
                            m_MapLocation,
                            date1,
                            "Incomplete",
                            m_Privacy
                    );

                    dbProducts.add(task).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(), "Task Added", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    // clear edit text fields
                    taskName.setText("");
                    taskDescription.setText("");
                    completedBy.setText("");
                    assignedTo.setText("");
                    editTextMapLocation.setText("");
                }
            }
        });

        return root;
    }

    private boolean validateInputs(String m_TaskName, String m_Description, String m_Completed, String m_AssignedTo, String m_MapLocation){
      boolean missingFields = false;
        if(m_TaskName.equals("")){
            missingFields = true;
            taskName.setError("Empty");
        }
        if(m_Description.equals("")){
            missingFields = true;
            taskDescription.setError("Empty");
        }
        if(m_AssignedTo.equals("")){
            missingFields = true;
            assignedTo.setError("Empty");
        }
        if(m_MapLocation.equals("")){
            missingFields = true;
            editTextMapLocation.setError("Empty");
        }
        if(m_Completed.equals("")){
            missingFields = true;
            completedBy.setError("Empty");
        }

        return missingFields;
    }

    private String checkRating(){
        int rating = (int)rBar.getRating();
        switch (rating){
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
        }
        return "Empty";
    }
}
