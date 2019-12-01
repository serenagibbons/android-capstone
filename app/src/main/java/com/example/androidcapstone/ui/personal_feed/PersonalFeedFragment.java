package com.example.androidcapstone.ui.personal_feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidcapstone.R;

public class PersonalFeedFragment extends Fragment {

    private PersonalFeedViewModel personalFeedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalFeedViewModel =
                ViewModelProviders.of(this).get(PersonalFeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        personalFeedViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}