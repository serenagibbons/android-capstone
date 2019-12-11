package com.example.androidcapstone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailedContactActivity extends AppCompatActivity {

    ImageView contactImageView;
    TextView contactNameText, contactEmailText;
    Button removeButton;

    String contactName;
    String contactEmail;
    String contactImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_contact);

        removeButton = findViewById(R.id.button_delete);
        contactEmailText = findViewById(R.id.contact_email);
        contactNameText = findViewById(R.id.contact_name);
        contactImageView = findViewById(R.id.contact_image);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {

            // get extras from the intent
            contactName = i.getStringExtra("contact name");
            contactEmail = i.getStringExtra("contact email");
            //String contactImage = i.getStringExtra("contact image");
            final String id = i.getStringExtra("Document id");
        }

        contactNameText.setText(contactName);
        contactEmailText.setText(contactEmail);
    }
}
