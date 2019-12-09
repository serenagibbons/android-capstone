package com.example.androidcapstone.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.DetailedTaskActivity;
import com.example.androidcapstone.FeedAdapter;
import com.example.androidcapstone.FriendFeedAdapter;
import com.example.androidcapstone.Model.User;
import com.example.androidcapstone.R;
import com.example.androidcapstone.ui.public_feed.PublicFeedViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class AddFriendActivity extends Fragment {

    private PublicFeedViewModel publicFeedViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference publicTaskRef = db.collection("User");
    private FriendFeedAdapter adapter;
    private RecyclerView recyclerView;
    FirestoreRecyclerOptions<User> users;
    Button findFriend;
    EditText emailInput;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        publicFeedViewModel =
                ViewModelProviders.of(this).get(PublicFeedViewModel.class);
        root = inflater.inflate(R.layout.activity_add_friend, container, false);
        emailInput = root.findViewById(R.id.editTextFriendEmail);
        findFriend = root.findViewById(R.id.buttonFindFriend);
        findFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String friendEmail = emailInput.getText().toString().trim();
                if(friendEmail != ""){
                    setUpRecyclerView(friendEmail);
                }
            }
        });
        // refer to recycler view
        recyclerView = root.findViewById(R.id.add_friends_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    //Used to find friend
    private void setUpRecyclerView(String email) {
        Query query = publicTaskRef.whereEqualTo("m_Email", email);

        users = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        adapter = new FriendFeedAdapter(getContext(), users);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        Toast.makeText(getActivity(), "Press: " + users.toString(), Toast.LENGTH_SHORT).show();

        adapter.setOnItemClickListener(new FriendFeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                // store document snapshot as task object
                User users = documentSnapshot.toObject(User.class);
                // save document snapshot id as String
                String id = documentSnapshot.getId();

                // create new intent to DetailedTaskActivity
                Intent intent = new Intent(getContext(), DetailedTaskActivity.class);
                intent.putExtra("taskit title", users.getM_UserID());
                intent.putExtra("taskit creator", users.getM_Email());
                intent.putExtra("taskit priority", users.getM_Avatar());
                intent.putExtra("taskit deadline", users.getM_FirstName());

                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //adapter.stopListening();
    }
}