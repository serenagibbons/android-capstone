package com.example.androidcapstone;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidcapstone.Model.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class FriendFeedAdapter extends FirestoreRecyclerAdapter<User, FriendFeedAdapter.FeedHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     */

    private OnItemClickListener listener;
    private Context mContext;
    AsyncTask<String, Void, Bitmap> dl;
    View globalView;

    public FriendFeedAdapter(Context context, FirestoreRecyclerOptions<User> options) {
        super(options);
        mContext = context;

    }

    @Override
    protected void onBindViewHolder(FeedHolder holder, int i, final User users) {
        holder.firstName.setText(users.getM_FirstName() + " " + users.getM_LastName());
        holder.email.setText(users.getM_Email());
        dl = new DownloadImageTask((ImageView) globalView.findViewById(R.id.photoUserPicture))
                .execute(users.getM_Avatar());

    }

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_feed_item,
                parent, false);
        return new FeedHolder(v);
    }

    class FeedHolder extends RecyclerView.ViewHolder {

        LinearLayout parentLayout;
        //ImageView image;
        TextView firstName; //First Name
        TextView email; //Email
        ImageView avatar;

        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            globalView = itemView;
            parentLayout = itemView.findViewById(R.id.parent_layout);
            firstName = itemView.findViewById(R.id.textFirstName);
            email = itemView.findViewById(R.id.textEmail);
            avatar = itemView.findViewById(R.id.photoUserPicture);

            // set onClickListener for recycler view items
            itemView.setOnClickListener(new View.OnClickListener() {
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}