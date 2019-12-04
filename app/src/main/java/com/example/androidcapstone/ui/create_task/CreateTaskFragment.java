package com.example.androidcapstone.ui.create_task;

import androidx.fragment.app.FragmentTransaction;
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
import android.widget.ToggleButton;

import com.example.androidcapstone.MainActivity;
import com.example.androidcapstone.MapsActivity;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;
import com.example.androidcapstone.ui.create_task.CreateTaskViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class CreateTaskFragment extends Fragment {
    FirebaseFirestore db;
    private CreateTaskViewModel createTaskViewModel;
    DatePickerDialog picker;

    EditText taskName, taskDescription, completedBy, tempStarDisplay, editTextMapLocation;
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
        tempStarDisplay = root.findViewById(R.id.editTextTempStarDisplay);
        editTextMapLocation = root.findViewById(R.id.editTextMapLocation);
        pickLocation = root.findViewById(R.id.btnLocationPicker);
        privacyBtn = root.findViewById(R.id.toggleButtonPrivacy);
        //Priority Bar
        rBar = root.findViewById(R.id.ratingBar1);
        //Create Task Button
        createTask = root.findViewById(R.id.buttonCreateTask);

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
                                completedBy.setText(monthOfYear + "/" + (dayOfMonth + 1) + "/" + year);
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
                String m_Priority = checkRating(); //Req
                String m_MapLocation = taskName.getText().toString().trim(); //Optional
                //Assign To
                String m_Privacy = (privacyBtn.isChecked() ? "Public" : "Private");
                //Check all required are filled
                //Firestore instance
                db = FirebaseFirestore.getInstance();
                CollectionReference dbProducts = db.collection("Task");
//                Task task = new Task(
//                        m_TaskName,
//                        m_Description,
//                        m_CompletedBy,
//                        m_Priority,
//                        m_MapLocation,
//                        m_Privacy
//                );

            }
        });

        return root;
    }

    private String checkRating(){
        int rating = (int)rBar.getRating();
        switch (rating){
            case 1:
                return "Low";
            case 2:
                return "Medium";
            case 3:
                return "High";
        }
        return "Empty";
    }
/*
    private CreateTaskViewModel mViewModel;

    public static CreateTaskFragment newInstance() {
        return new CreateTaskFragment();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CreateTaskViewModel.class);
        // TODO: Use the ViewModel
    }*/

}
