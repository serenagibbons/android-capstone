package com.example.androidcapstone.ui.personal_feed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

public class PersonalFeedFragment extends Fragment {

    private PersonalFeedViewModel personalFeedViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference personalTaskRef = db.collection("Task");
    private FeedAdapter personalAdapter, personalAdapter2;
    FirestoreRecyclerOptions<Task> tasks;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalFeedViewModel =
                ViewModelProviders.of(this).get(PersonalFeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal, container, false);

        recyclerView = root.findViewById(R.id.personal_feed_recycler_view);
        recyclerView2 = root.findViewById(R.id.personal_feed_recycler_view2);

        setUpRecyclerViewSortByDeadline(recyclerView);
        setUpRecyclerViewSortByPriority(recyclerView2);

        setUpSpinner(root);

        personalAdapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                // store document snapshot as task object
                Task task = documentSnapshot.toObject(Task.class);

                String dateString = "";
                SimpleDateFormat sdfr = new SimpleDateFormat("MM/dd/yyyy");
                try{
                    dateString = sdfr.format( task.getM_DueDate() );
                }catch (Exception ex ){
                    ex.printStackTrace();
                }
                // save document snapshot id as String
                String id = documentSnapshot.getId();

                // create new intent to DetailedTaskActivity
                Intent intent = new Intent(getContext(), DetailedTaskActivity.class);
                intent.putExtra("taskit title", task.getM_TaskName());
                intent.putExtra("taskit creator", task.getM_Creator());
                intent.putExtra("taskit priority", task.getM_Importance());
                intent.putExtra("taskit posted", task.getM_CreatedOnDate());
                intent.putExtra("taskit deadline", dateString);
                intent.putExtra("taskit description", task.getM_TaskDescription());
                intent.putExtra("taskit privacy", task.getM_Privacy());
                intent.putExtra("Document id", id);
                startActivity(intent);
            }
        });

        personalAdapter2.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                // store document snapshot as task object
                Task task = documentSnapshot.toObject(Task.class);

                String dateString = "";
                SimpleDateFormat sdfr = new SimpleDateFormat("MM/dd/yyyy");
                try{
                    dateString = sdfr.format( task.getM_DueDate() );
                }catch (Exception ex ){
                    ex.printStackTrace();
                }
                // save document snapshot id as String
                String id = documentSnapshot.getId();

                // create new intent to DetailedTaskActivity
                Intent intent = new Intent(getContext(), DetailedTaskActivity.class);
                intent.putExtra("taskit title", task.getM_TaskName());
                intent.putExtra("taskit creator", task.getM_Creator());
                intent.putExtra("taskit priority", task.getM_Importance());
                intent.putExtra("taskit posted", task.getM_CreatedOnDate());
                intent.putExtra("taskit deadline", dateString);
                intent.putExtra("taskit description", task.getM_TaskDescription());
                intent.putExtra("taskit privacy", task.getM_Privacy());
                intent.putExtra("Document id", id);
                startActivity(intent);
            }
        });

        return root;
    }

    private void setUpRecyclerViewSortByDeadline(RecyclerView recyclerView) {

        // show only public tasks and order by deadline
        Query query = personalTaskRef.whereEqualTo("m_Privacy", "Private")
                .orderBy("m_DueDate", Query.Direction.ASCENDING);

        tasks = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();

        personalAdapter = new FeedAdapter(getContext(), tasks);

        // refer to recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(personalAdapter);
    }

    private void setUpRecyclerViewSortByPriority(RecyclerView recyclerView) {
        // show only public tasks and order by priority
        Query query = personalTaskRef.whereEqualTo("m_Privacy", "Private")
                .orderBy("m_Importance", Query.Direction.DESCENDING);

        tasks = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();

        personalAdapter2 = new FeedAdapter(getContext(), tasks);

        // refer to recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(personalAdapter2);
    }

    @Override
    public void onStart() {
        super.onStart();
        personalAdapter.startListening();
        personalAdapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        personalAdapter.stopListening();
        personalAdapter2.stopListening();
    }

    // create spinner
    private void setUpSpinner(final View root) {
        Spinner sortSpinner = root.findViewById(R.id.personal_spinner);

        final String[] items = new String[] { "Deadline", "Priority" };

        ArrayAdapter<String> strAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, items);

        sortSpinner.setAdapter(strAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    // sort by deadline
                    recyclerView2.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else if (position == 1) {
                    recyclerView.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }
}