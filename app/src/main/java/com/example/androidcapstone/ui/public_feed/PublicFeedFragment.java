package com.example.androidcapstone.ui.public_feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidcapstone.FeedAdapter;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PublicFeedFragment extends Fragment {

    private static final String KEY_TASK_NAME = "m_TaskName";
    private static final String KEY_TASK_DESCRIPTION = "m_TaskDescription";
    private static final String KEY_IMPORTANCE = "m_Importance";
    private static final String KEY_DEADLINE = "m_DueDate";
    private static final String KEY_CREATOR = "m_Creator";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.collection("Task").document("b0hiGEGXfucT3G6fnlT3");
    private PublicFeedViewModel publicFeedViewModel;
    RecyclerView recyclerView;
    FeedAdapter adapter;
    RecyclerView.LayoutManager manager;
    List<Task> taskList;
    Task taskObj;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        publicFeedViewModel =
                ViewModelProviders.of(this).get(PublicFeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_public, container, false);
        // refer to recycler view
        recyclerView = root.findViewById(R.id.public_feed_recycler_view);


        loadNote(getView());
        return root;
    }


    private void loadNote(View v){
        noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String taskName = documentSnapshot.getString(KEY_TASK_NAME);
                    String taskDescription = documentSnapshot.getString(KEY_TASK_DESCRIPTION);
                    String taskImportance = documentSnapshot.getString(KEY_IMPORTANCE);
                    String taskCreator = documentSnapshot.getString(KEY_CREATOR);
                    Date taskDeadline = documentSnapshot.getDate(KEY_DEADLINE);


                    // add dummy data
                    taskList = new ArrayList<>();
                    taskObj = new Task();
                    taskObj.setM_TaskName(taskName);
                    taskObj.setM_TaskDescription(taskDescription);
                    taskObj.setM_Creator(taskCreator);
                    taskObj.setM_Importance(taskImportance);
                    taskObj.setM_DueDate(taskDeadline);
                    taskList.add(taskObj);
                    taskList.add(taskObj);
                    // create recycler view adapter and layout manager
                    adapter = new FeedAdapter(taskList,getActivity());
                    manager = new LinearLayoutManager(getActivity());
                    // Link the adapter to the RecyclerView
                    recyclerView.setAdapter(adapter);
                    // Set layout for the RecyclerView
                    recyclerView.setLayoutManager(manager);
                    //Map<String, Object> note = documentSnapshot.getData();

                }else{
                    Toast.makeText(getContext(),"Document doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Failure: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}