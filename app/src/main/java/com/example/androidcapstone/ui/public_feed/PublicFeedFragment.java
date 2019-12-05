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
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class PublicFeedFragment extends Fragment {

    private PublicFeedViewModel publicFeedViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference publicTaskRef = db.collection("Task");
    private FeedAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        publicFeedViewModel =
                ViewModelProviders.of(this).get(PublicFeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_public, container, false);

        setUpRecyclerView(root);

        return root;
    }

    private void setUpRecyclerView(View root) {
        //Query query = publicTaskRef.orderBy("m_TaskName", Query.Direction.DESCENDING);
        Query query = publicTaskRef.whereEqualTo("m_Privacy", "Public");


        FirestoreRecyclerOptions<Task> tasks = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();

        adapter = new FeedAdapter(getContext(), tasks);


        // refer to recycler view
        RecyclerView recyclerView = root.findViewById(R.id.public_feed_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}