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

import com.example.androidcapstone.MainActivity;
import com.example.androidcapstone.MapsActivity;
import com.example.androidcapstone.R;
import com.example.androidcapstone.ui.create_task.CreateTaskViewModel;

import java.util.Calendar;

public class CreateTaskFragment extends Fragment {

    private CreateTaskViewModel createTaskViewModel;
    DatePickerDialog picker;

    EditText completedBy, tempStarDisplay, editTextMapLocation;
    Button createTask;
    RatingBar rBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        createTaskViewModel =
                ViewModelProviders.of(this).get(CreateTaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_task, container, false);

        completedBy = root.findViewById(R.id.editTextCompletedByDate);
        tempStarDisplay = root.findViewById(R.id.editTextTempStarDisplay);
        editTextMapLocation = root.findViewById(R.id.editTextMapLocation);
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
        editTextMapLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivity(i);
//                Fragment childFragment = new MapsActivity();
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                transaction.replace(R.id.child_fragment_container, childFragment).commit();


            }
        });

        //Create Task Button
        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float priority = rBar.getRating(); //Gets priority, 1.0 = Low, 2.0 = Medium, 3.0 = High
                tempStarDisplay.setText(Float.toString(priority));
            }
        });

        return root;
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
