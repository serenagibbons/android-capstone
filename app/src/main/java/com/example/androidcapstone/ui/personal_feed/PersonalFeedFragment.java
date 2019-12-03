package com.example.androidcapstone.ui.personal_feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.FeedAdapter;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;

import java.util.ArrayList;
import java.util.List;

public class PersonalFeedFragment extends Fragment {

    private PersonalFeedViewModel personalFeedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalFeedViewModel =
                ViewModelProviders.of(this).get(PersonalFeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal, container, false);

        // add dummy data
        List<Task> test = new ArrayList<>();
        Task t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);


        // create recycler view adapter and layout manager
        FeedAdapter adapter = new FeedAdapter(test,getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());

        // refer to recycler view
        RecyclerView recyclerView = root.findViewById(R.id.personal_feed_recycler_view);

        // Link the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
        // Set layout for the RecyclerView
        recyclerView.setLayoutManager(manager);

        return root;
    }
}