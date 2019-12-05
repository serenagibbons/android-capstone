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
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PersonalFeedFragment extends Fragment {

    private PersonalFeedViewModel personalFeedViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference publicTaskRef = db.collection("Task");
    private FeedAdapter personalAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalFeedViewModel =
                ViewModelProviders.of(this).get(PersonalFeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal, container, false);

        setUpPersonalRecyclerView(root);

        return root;
    }

    private void setUpPersonalRecyclerView(View root) {

        Query query = publicTaskRef.whereEqualTo("m_Privacy", "Private");

        FirestoreRecyclerOptions<Task> tasks = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();

        personalAdapter = new FeedAdapter(getContext(), tasks);


        // refer to recycler view
        RecyclerView recyclerView = root.findViewById(R.id.personal_feed_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(personalAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        personalAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        personalAdapter.stopListening();
    }
}