package com.example.androidcapstone.ui.contacts;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

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
import com.example.androidcapstone.FeedAdapter;
import com.example.androidcapstone.Model.Contact;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;
import com.example.androidcapstone.ui.personal_feed.PersonalFeedViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {

    private ContactsViewModel mViewModel;
    Button addFriend;
    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

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

        // add dummy data
        List<Contact> connections = new ArrayList<>();
        Contact c = new Contact();
        connections.add(c);
        c = new Contact();
        connections.add(c);

        // create recycler view adapter and layout manager
        ContactAdapter adapter = new ContactAdapter(connections, getActivity());
        RecyclerView.LayoutManager mgr = new LinearLayoutManager(getActivity());

        // refer to recycler view
        RecyclerView contactRecyclerView = root.findViewById(R.id.contacts_recycler_view);

        // Link the adapter to the RecyclerView
        contactRecyclerView.setAdapter(adapter);
        // Set layout for the RecyclerView
        contactRecyclerView.setLayoutManager(mgr);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);

    }

}
