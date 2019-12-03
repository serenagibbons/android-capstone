package com.example.androidcapstone;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.Model.Contact;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<com.example.androidcapstone.ContactAdapter.ContactViewHolder> {

    private List<Contact> contacts = new ArrayList<>();
    private Activity activity;
    public ContactAdapter(List<Contact> list, Activity activity) {
        contacts.addAll(list);
        this.activity = activity;
    }
    @NonNull
    @Override
    public com.example.androidcapstone.ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.androidcapstone.ContactAdapter.ContactViewHolder holder, int i) {
        Contact contact = contacts.get(i);
        holder.contactName.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView contactName;

       ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.contact_name);
        }
    }
}
