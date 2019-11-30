package com.example.androidcapstone.ui.create_task;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidcapstone.R;
import com.example.androidcapstone.ui.dashboard.DashboardViewModel;

public class CreateTaskFragment extends Fragment {

    private CreateTaskViewModel createTaskViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createTaskViewModel =
                ViewModelProviders.of(this).get(CreateTaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_task, container, false);
        final TextView textView = root.findViewById(R.id.text_create);
        createTaskViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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
