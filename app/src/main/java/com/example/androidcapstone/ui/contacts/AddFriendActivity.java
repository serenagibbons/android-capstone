package com.example.androidcapstone.ui.contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.androidcapstone.ContactAdapter;
import com.example.androidcapstone.DetailedTaskActivity;
import com.example.androidcapstone.FeedAdapter;
import com.example.androidcapstone.FriendFeedAdapter;
import com.example.androidcapstone.Model.Contact;
import com.example.androidcapstone.Model.User;
import com.example.androidcapstone.R;
import com.example.androidcapstone.ui.public_feed.PublicFeedViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;


public class AddFriendActivity extends Fragment {

    private PublicFeedViewModel publicFeedViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference friendRef = db.collection("User");
    private FriendFeedAdapter adapter;
    public static final String KEY_DESCRIPTION = "Contacts";
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
        Query query = friendRef.whereEqualTo("m_Email", email);

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
                // store document snapshot as user object
                User user = documentSnapshot.toObject(User.class);

                // save document snapshot id as String
                String id = documentSnapshot.getId();
                String contactFName = user.getM_FirstName();
                String contactLName = user.getM_LastName();
                String fullName = contactFName + " " + contactLName;
                String contactUID = user.getM_UserID();

                addToContacts(fullName, contactUID); // would want to pass user ID
            }
        });
    }

    void addToContacts(String name, final String stringID) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setMessage("Add " + name + " as a contact?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // hard-coded ID for current user
                        String id = "rJpFpjoDfXEo225nubod";

                        Query query = friendRef.whereEqualTo("m_UserID", id);

                        // not enough time to code -- suspect something along these lines

                        // add selected user to current user's contacts
                        // get users current contact list
                        //List<String> contactList = user.getContacts();
                        // add to contact list
                        //contactList.push(stringID);
                        // set users contact list to new list
                        //friendRef.document(id).update(KEY_DESCRIPTION, contactList);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing (return)
                    }
                });
        AlertDialog alert = adb.create();
        alert.show();
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