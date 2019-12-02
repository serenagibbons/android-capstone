package com.example.androidcapstone.ui.public_feed;

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
import com.example.androidcapstone.R;
import com.example.androidcapstone.TaskData;

import java.util.ArrayList;
import java.util.List;

public class PublicFeedFragment extends Fragment {

    private PublicFeedViewModel publicFeedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        publicFeedViewModel =
                ViewModelProviders.of(this).get(PublicFeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_public, container, false);
        // add dummy data
        List<TaskData> test = new ArrayList<>();
        TaskData t = new TaskData();
        t.task = "task4";
        t.postedTime = "3 min ago";
        t.deadline = "12/30/19";
        t.description = "task description goes here";
        test.add(t);

        t = new TaskData();
        t.task = "task5";
        t.postedTime = "4 min ago";
        t.deadline = "12/31/19";
        t.description = "task description goes here";
        test.add(t);

        t = new TaskData();
        t.task = "task6";
        t.postedTime = "5 min ago";
        t.deadline = "1/1/20";
        t.description = "task description goes here";
        test.add(t);

        t = new TaskData();
        t.task = "task7";
        t.postedTime = "5 min ago";
        t.deadline = "1/9/20";
        t.description = "task description goes here";

        test.add(t);

        // create recycler view adapter and layout manager
        FeedAdapter adapter = new FeedAdapter(test,getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());

        // refer to recycler view
        RecyclerView recyclerView = root.findViewById(R.id.public_feed_recycler_view);

        // Link the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
        // Set layout for the RecyclerView
        recyclerView.setLayoutManager(manager);

        return root;
    }

}