package com.example.androidcapstone.PreLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.androidcapstone.R;
import com.google.android.material.tabs.TabLayout;

public class LoginScreenActivity extends AppCompatActivity implements AccountRegister.OnRegisterFragmentListener{
    SectionsPageAdapter mSectionsPageAdapter;
    ViewPager viewPager;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter((getSupportFragmentManager()));
        adapter.addFragment(new AccountLogin(), "Login");
        adapter.addFragment(new AccountRegister(), "Register");
        viewPager.setAdapter(adapter);
    }

    // The Activity handles receiving a message from one Fragment
    // and passing it on to the other Fragment
    @Override
    public void messageFromRegister(String type, String msg) {
        String tag = "android:switcher:" + R.id.view_pager + ":" + 0;
        AccountLogin loginFrag = (AccountLogin) getSupportFragmentManager().findFragmentByTag(tag);
        loginFrag.updateText(type, msg);
    }
    @Override
    public void switchTab(){
        tabs.getTabAt(0).select();
    }
}

