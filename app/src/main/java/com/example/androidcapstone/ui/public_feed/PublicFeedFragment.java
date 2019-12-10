package com.example.androidcapstone.ui.public_feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.DetailedTaskActivity;
import com.example.androidcapstone.FeedAdapter;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;


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
        // show only public tasks and order by deadline
        Query query = publicTaskRef.whereEqualTo("m_Privacy", "Public")
                .orderBy("m_CreatedOnDate");


        FirestoreRecyclerOptions<Task> tasks = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();

        adapter = new FeedAdapter(getContext(), tasks);


        // refer to recycler view
        RecyclerView recyclerView = root.findViewById(R.id.public_feed_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                // store document snapshot as task object
                Task task = documentSnapshot.toObject(Task.class);

                String dateString = "";
                SimpleDateFormat sdfr = new SimpleDateFormat("MM/dd/yyyy");
                try{
                    dateString = sdfr.format( task.getM_DueDate() );
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                // save document snapshot id as String
                String id = documentSnapshot.getId();

                // create new intent to DetailedTaskActivity
                Intent intent = new Intent(getContext(), DetailedTaskActivity.class);
                intent.putExtra("taskit title", task.getM_TaskName());
                intent.putExtra("taskit creator", task.getM_Creator());
                intent.putExtra("taskit priority", task.getM_Importance());
                intent.putExtra("taskit deadline", dateString);
                intent.putExtra("taskit description", task.getM_TaskDescription());
                intent.putExtra("taskit privacy", task.getM_Privacy());
                intent.putExtra("Document id", id);
                startActivity(intent);
            }
        });
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