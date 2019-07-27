package com.bayazid.cpik_present_system.Auth_Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bayazid.cpik_present_system.DATA_SECTOR.Session;
import com.bayazid.cpik_present_system.Network_Cheaker.ConnectivityReceiver;
import com.bayazid.cpik_present_system.Network_Cheaker.MyApplicationController;
import com.bayazid.cpik_present_system.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener{
    private Button LogInBtn;
    public FirebaseAuth mAuth;
    public static final String TAG = "Login Activity ";
    private EditText Email,Password;
    private  String passEmail,passPassword ;
//   private String currentEmail;
    private ImageView logo;
    private LinearLayout linearLayout;
    private Animation downToUp, upToDown;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private boolean UserFound;


    private static final int RC_SIGN_IN = 264;

    private Session session;


    SignInButton googlesigninbtn;
    GoogleSignInClient mGoogleSignInClient;
    ProgressBar progressBar;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Calling Session Class
        session=new Session(this);


        //Floating Action Bar And SeckBar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //set up animation
        upToDown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downToUp = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        //Innitilize id s

         progressBar=findViewById(R.id.progressBar);
        LogInBtn =findViewById(R.id.email_sign_in_button);
        LogInBtn.setVisibility(View.GONE);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        googlesigninbtn=findViewById(R.id.signin);

        // Manually checking internet connection
        checkConnection();

        //...........Cheak EMAIL is valade or not..........................
        Email .addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (isValidEmailIdAndPassword(Email.getText().toString().trim()) && s.length() > 0)
                {
                    LogInBtn.setVisibility(View.VISIBLE);
                }
                else
                {
                    LogInBtn.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });





//Create Sign Dialog Box
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        // Initialize Firebase Auth current users
        mAuth = FirebaseAuth.getInstance();

        //Google Sign In Btn Action
        googlesigninbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                signIn();
            }
        });

        //login btn action
        LogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cheak Internate
                checkConnection();
                LogInBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                    if (isValidEmailIdAndPassword(Password.getText().toString().trim()) && Password.getText().toString().trim().length() >= 6)
                    {
                        //Email passs Login
                        tryLogIn();
                    }
                    else {Password.setError("Password Must be 6 digit or more");
                        LogInBtn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);}


                    }
        });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        showProgressDialog();

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firbaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firbaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firbaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                           // startActivity(new Intent(LoginActivity.this, Teachers_Panel.class));
                            startActivity(new Intent(LoginActivity.this, Teachers_Panel.class));
                            finishAffinity();

                            updateUserUI(user);
                           // startActivity(new Intent(LoginActivity.this, Teachers_Panel.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.fab), "Authentication Failed. Try Again...", Snackbar.LENGTH_LONG).show();
                             updateUserUI(null);
                        }

                        // ...
                    }
                });
    }
    //.........................
        private void showProgressDialog() {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.loading));
                mProgressDialog.setIndeterminate(true);
            }

            mProgressDialog.show();
        }
        private void hideProgressDialog() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.hide();
            }
        }


    @Override
    public void onStart() {
        super.onStart();

        if (session.getTitle().equals("SignOut")){
            mGoogleSignInClient.signOut();

        }
        else{
            updateUserUI(mAuth.getCurrentUser());
            showProgressDialog();
            progressBar.setVisibility(View.GONE);
        }
    }



    //Main UI updater
    public void updateUserUI(final FirebaseUser currentUser) {


//        if (currentUser.isEmailVerified() && currentUser!= null) {
//            session.setEamil(currentUser.getEmail().toString());
//            //Cheak Admin Auth Email
//            db.collection("Teachers_Collection").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                          boolean t = true;
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    if (document.getId().equals(session.getEamil())){
//                                        progressBar.setVisibility(View.GONE);
//                                        hideProgressDialog();
//                                        startActivity(new Intent(LoginActivity.this, Teachers_Panel.class));
//                                        finishAffinity();
//                                    }
//                                    else{
//                                        progressBar.setVisibility(View.GONE);
//                                        hideProgressDialog();
//                                        startActivity(new Intent(LoginActivity.this, GeneralUser_Profile.class));
//                                        finishAffinity();
//                                    }
//
//                                }
//                            }
//
//                        }
//                    });
//        }
    }

    // Normal Email Pass Login
    private void tryLogIn() {
        passEmail=Email.getText().toString();
        passPassword=Password.getText().toString();
        mAuth.signInWithEmailAndPassword(passEmail, passPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUserUI(currentUser);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Email.setError("Invalid Email Address");
                            Password.setError("Password didn't Matched");
                            Toast.makeText(LoginActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            LogInBtn.setVisibility(View.VISIBLE);
                            updateUserUI(null);
                        }

                        // ...
                    }
                });
    }

    //............................................Check Internet Connection..............................
            private void checkConnection() {
                boolean isConnected = ConnectivityReceiver.isConnected();
                showSnack(isConnected);
            }
            //Show SnackBarr nternet State
            private void showSnack(boolean isConnected) {
                String message;
                int color;
                if (isConnected) {
                    message = "Connected to Internet";
                    color = Color.GREEN;
                } else {
                    message = "Sorry! Not connected to internet";
                    color = Color.RED;

                    LogInBtn.setClickable(false);

                }
                Snackbar snackbar = Snackbar.make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                textView.setTextColor(color);
                snackbar.show();
            }

            @Override
            protected void onResume() {
                super.onResume();
                // register connection status listener
                MyApplicationController.getInstance().setConnectivityListener(this);
            }

            /**
             * Callback will be triggered when there is change in
             * network connection
             */
            @Override
            public void onNetworkConnectionChanged(boolean isConnected) {
                showSnack(isConnected);
            }

            //Email Pattern Validaty Cheaker
            private boolean isValidEmailIdAndPassword(String email){
                return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
            }

}
