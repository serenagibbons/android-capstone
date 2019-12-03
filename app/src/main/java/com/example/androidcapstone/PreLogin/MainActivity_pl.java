package com.example.androidcapstone.PreLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidcapstone.R;

public class MainActivity_pl extends AppCompatActivity {

    View mainView;

    //Used for gestures
    private float x1,x2;
    static final int MIN_DISTANCE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);

        mainView = findViewById(R.id.mainView);

        //Screen gesture listener
        mainView.setOnTouchListener(new OnSwipeTouchListener(MainActivity_pl.this){

            //User Swipes left to go to next activity
            public void onSwipeLeft() {
                nextPageLoad();
            }
        });
    }


    //User can click the arrow to go to next activity
    public void nextPage(View view) {
        nextPageLoad();
    }

    public void nextPageLoad(){
        Intent i = new Intent(this, LoginScreenActivity.class);
        startActivity(i);
    }
}
