package com.example.androidcapstone.PreLogin;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidcapstone.MainActivity;
import com.example.androidcapstone.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountLogin extends Fragment {

    private static final String TAG = "tag" ;
    private static final int RC_SIGN_IN = 101;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton googleLogin;

    LoginButton fbLoginButton;
    CallbackManager callbackManager;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    Button btnLogin;
    EditText loginEmail, loginPassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_screen,container,false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        googleLogin = view.findViewById(R.id.loginGoogle);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
// Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Set the dimensions of the sign-in button. Google
        SignInButton signInButton = view.findViewById(R.id.loginGoogle);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        mGoogleSignInClient = GoogleSignIn.getClient(getContext() , gso);

        //Facebook
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = view.findViewById(R.id.loginFB);
        fbLoginButton.setFragment(this);
        fbLoginButton.setReadPermissions("email", "public_profile");
        checkLoginStatus();
        // If you are using in a fragment, call loginButton.setFragment(this);




        btnLogin = view.findViewById(R.id.btnLogin);
        loginEmail = view.findViewById(R.id.editLoginEmail);
        loginPassword = view.findViewById(R.id.editLoginPassword);
        googleLogin = view.findViewById(R.id.loginGoogle);



        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(getActivity(), "Logged In", Toast.LENGTH_SHORT).show();
                    ProceedToMainMenu();
                }
            }
        };

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getContext(), "Facebook Sign-In Success", Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);

                //Toast.makeText(getContext(),"facebook:onError " + error, Toast.LENGTH_SHORT).show();
                // ...
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
                    try {
                        mFirebaseAuth.signInWithEmailAndPassword(emailText, passText).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Incorrect username/password", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(getContext(), "Google Sign-In Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken ==null){ //Not logged in
                Toast.makeText(getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            }
            else{
                loadUserProfile(currentAccessToken);

            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();
                    ProceedToMainMenu();

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            ProceedToMainMenu();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Google Sign-In Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    public void googleSignInClick(View view) {
        signIn();

    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        Toast.makeText(getContext(), "Logged In", Toast.LENGTH_SHORT).show();
        ProceedToMainMenu();
    }

    private void checkLoginStatus()
    {
        if(AccessToken.getCurrentAccessToken()!=null)
        {
            loadUserProfile(AccessToken.getCurrentAccessToken());

        }
    }

    private void ProceedToMainMenu(){
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }
}
