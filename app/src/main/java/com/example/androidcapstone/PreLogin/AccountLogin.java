package com.example.androidcapstone.PreLogin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidcapstone.MainActivity;
import com.example.androidcapstone.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountLogin extends Fragment {
    Button btnLogin;
    EditText loginEmail, loginPassword;
    ImageView facebookLogin, googleLogin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_screen,container,false);
        btnLogin = view.findViewById(R.id.btnLogin);
        loginEmail = view.findViewById(R.id.editLoginEmail);
        loginPassword = view.findViewById(R.id.editLoginPassword);
        facebookLogin = view.findViewById(R.id.loginFB);
        googleLogin = view.findViewById(R.id.loginGoogle);

        //Facebook Login is clicked
        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookSignInClick(view);
            }
        });
        //Google Login is clicked
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInClick(view);
            }
        });

        //Login Button is clicked
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for not empty
                //Check for user account - Not implemented yet
                //Check for password - Not implemented yet
                String emailText = loginEmail.getText().toString().trim();
                String passText = loginPassword.getText().toString().trim();
                if(passText.equals("") && emailText.equals("")){
                    Toast.makeText(getActivity(), "Empty Fields", Toast.LENGTH_SHORT).show();
                    loginEmail.setError("Empty");
                    loginPassword.setError("Empty");
                }
                else if(emailText.equals("")){
                    Toast.makeText(getActivity(), "Empty Fields", Toast.LENGTH_SHORT).show();
                    loginEmail.setError("Empty");
                }
                else if(passText.equals("")){
                    Toast.makeText(getActivity(), "Empty Fields", Toast.LENGTH_SHORT).show();
                    loginPassword.setError("Empty");
                }
                else if(isValidEmailAddress(emailText) == false){
                    loginEmail.setError("Invalid Format (example@mail.com)");
                }
                else if(passText.length() < 8){
                    loginPassword.setError("Password must be > 8 characters");
                }
                else{
                    //Check for existing user
                    //Check for matching password
                    //Log in and switch activity
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                }
            }
        });
        return view;
    }


    //When user clicks register check for values to pass into login
    public void updateText(String type, String msg){
        if(type == "KEY_EMAIL"){
            loginEmail.getText().clear();
            loginEmail.setText(msg);
        }
        else if(type == "KEY_PASSWORD"){
            loginPassword.getText().clear();
            loginPassword.setText(msg);
        }
    }

    public boolean isValidEmailAddress(String email) {
        // Email Regex java
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

        // static Pattern object, since pattern is fixed
        Pattern pattern;

        // non-static Matcher object because it's created from the input String
        Matcher matcher;

        // initialize the Pattern object
        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);


        /**
         * This method validates the input email address with EMAIL_REGEX pattern
         *
         * @param email
         * @return boolean
         */
        matcher = pattern.matcher(email);
        if(matcher.matches() == true){
            return true;
        }else {
            return false;
        }
    }

    public void facebookSignInClick(View view) {
        Toast.makeText(getActivity(), "Facebook Login", Toast.LENGTH_SHORT).show();
    }
    public void googleSignInClick(View view) {
        Toast.makeText(getActivity(), "Google Login", Toast.LENGTH_SHORT).show();
    }

}
