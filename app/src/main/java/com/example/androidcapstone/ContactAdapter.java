package com.example.androidcapstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.Model.Contact;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ContactAdapter extends FirestoreRecyclerAdapter<Contact, ContactAdapter.ContactViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     */

    private ContactAdapter.OnItemClickListener listener;
    private Context mContext;

    public ContactAdapter(Context context, FirestoreRecyclerOptions<Contact> options) {
        super(options);
        mContext = context;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,
                parent, false);
        return new ContactAdapter.ContactViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ContactViewHolder holder, int position, @NonNull Contact contact) {
        holder.contactName.setText(contact.getName());
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        ImageView contactImage;
        TextView contactName;

       ContactViewHolder(@NonNull View item) {
            super(item);

            contactImage = item.findViewById(R.id.photoUserPicture);
            contactName = item.findViewById(R.id.contact_name);

           // set onClickListener for recycler view items
           item.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int position = getAdapterPosition();
                   if (position != RecyclerView.NO_POSITION && listener != null) {
                       listener.onItemClick(getSnapshots().getSnapshot(position), position);
                   }
               }
           });
       }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }

    public void setOnItemClickListener(ContactAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}