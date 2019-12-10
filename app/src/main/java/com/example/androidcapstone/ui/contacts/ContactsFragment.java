package com.example.androidcapstone.ui.contacts;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidcapstone.ContactAdapter;
import com.example.androidcapstone.Model.Contact;
import com.example.androidcapstone.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ContactsFragment extends Fragment {

    private ContactsViewModel mViewModel;
    Button addFriend;
    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference contactRef = db.collection("Contact");
    private ContactAdapter contactAdapter;
    FirestoreRecyclerOptions<Contact> contacts;
    RecyclerView contactRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //return inflater.inflate(R.layout.fragment_contacts, container, false);

        mViewModel =
                ViewModelProviders.of(this).get(ContactsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contacts, container, false);
        addFriend = root.findViewById(R.id.buttonAddFriend);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new AddFriendActivity());
                ft.commit();
            }
        });

        // refer to recycler view
        contactRecyclerView = root.findViewById(R.id.contacts_recycler_view);

        setUpContactRecyclerView(contactRecyclerView);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);
    }

    private void setUpContactRecyclerView(RecyclerView recyclerView) {
        Query query = contactRef;

        contacts = new FirestoreRecyclerOptions.Builder<Contact>()
                .setQuery(query, Contact.class)
                .build();

        contactAdapter = new ContactAdapter(getContext(), contacts);

        // refer to recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        contactAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        contactAdapter.stopListening();
    }
}
