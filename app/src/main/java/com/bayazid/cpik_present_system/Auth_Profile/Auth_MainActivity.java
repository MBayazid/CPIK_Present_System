package com.bayazid.cpik_present_system.Auth_Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bayazid.cpik_present_system.DATA_SECTOR.Session;
import com.bayazid.cpik_present_system.R;
import com.bayazid.librarycpik.CustomizedToast.CT;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Auth_MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "Auth_MainActivity";
    private Session session;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity_main);

        //session objClass
        session=new Session(this);
        //cheak User
        //themeAndLogo();
//        checkCurrentUser();
    }
    public void checkCurrentUser() {
        // [START check_current_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            getUserProfile();
        }else {
            //Google button appears
            createSignInIntent();
            // No user is signed in
        }
        // [END check_current_user]
    }
    //Fire Base UI

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build()
                , new AuthUI.IdpConfig.GoogleBuilder().build()

        );
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAlwaysShowSignInMethodScreen(false)
                        .setIsSmartLockEnabled(false)
                        .setLogo(R.drawable.cpik_logo_old_blue)      // Set logo drawable
                        .setTheme(R.style.MySuperAppTheme)      // Set theme
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // User is signed in
            getUserProfile();
            //getProviderData();

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // User is signed in
                getUserProfile();
                Toast.makeText(this,"SignIn Successful..",Toast.LENGTH_SHORT).show();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...

                Toast.makeText(this,"SignIn Failed..",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    // [END auth_fui_result]

//    public void signOut() {
//        // [START auth_fui_signout]
//        AuthUI.getInstance()
//                .signOut(this)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // ...
//                        Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_SHORT).show();
//                        createSignInIntent();
//                    }
//                });
//        // [END auth_fui_signout]
//    }

//    public void themeAndLogo() {
//        List<AuthUI.IdpConfig> providers = Collections.emptyList();
//
//        // [START auth_fui_theme_logo]
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(providers)
//                        .setLogo(R.drawable.cpik_banar_logo)      // Set logo drawable
//                        .setTheme(R.style.MySuperAppTheme)// Set theme
//                        .build(),
//                RC_SIGN_IN);
//        // [END auth_fui_theme_logo]
//    }

//    public void privacyAndTerms() {
//        List<AuthUI.IdpConfig> providers = Collections.emptyList();
//        // [START auth_fui_pp_tos]
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(providers)
//                        .setTosAndPrivacyPolicyUrls(
//                                "https://example.com/terms.html",
//                                "https://example.com/privacy.html")
//                        .build(),
//                RC_SIGN_IN);
//        // [END auth_fui_pp_tos]
//    }

    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            String PhoneNumber=user.getPhoneNumber();

            Uri photoUrl = user.getPhotoUrl();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.



            if (emailVerified==true&&!email.equals(null)){
                adminIdFinder(email);
            }else {
                //mobile number
                Toast.makeText(this,PhoneNumber,Toast.LENGTH_SHORT).show();
             startActivity(new Intent(Auth_MainActivity.this,GeneralUser_Profile.class));
             finish();
            }

          //  Toast.makeText(this," User Name= "+name+email+photoUrl+"Is Varifide ? "+emailVerified,Toast.LENGTH_SHORT).show();
        }
        // [END get_user_profile]
    }

    private void adminIdFinder(final String email) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Cheak if Email Admin Auth Email
            db.collection("Teachers_Collection")
                    .whereEqualTo("teacher",true)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    boolean isAdminEmail=false;
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getId().equals(email)){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //set isAd bollren true
                                isAdminEmail=true;
                                session.setisAdminEmail(true);
                                updateUI(user);
                            }
                        }
                    }
                    if (task.isComplete()){
                        if (isAdminEmail==false)
                        session.setisAdminEmail(false);
                        updateUI(user);
                    }
                }
            });


//                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    if (document.getId().equals(email)){
////                                        progressBar.setVisibility(View.GONE);
////                                        hideProgressDialog();
//
//                                        task.isComplete();
//
//                                    }
//
//                                }
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(), "General Activity", Toast.LENGTH_LONG).show();
//                                //progressBar.setVisibility(View.GONE);
////                                        hideProgressDialog();
//                                startActivity(new Intent(Auth_MainActivity.this, GeneralUser_Profile.class));
//                               // finishAffinity();
//                            }
//
//                        }
//                    });
    }

    public void getProviderData() {
        // [START get_provider_data]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();
                // UID specific to the provider
                String uid = profile.getUid();
                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
                Toast.makeText(this," User Name= "+providerId,Toast.LENGTH_SHORT).show();
            }
        }
        // [END get_provider_data]
    }


//    public void sendEmailVerification() {
//        // [START send_email_verification]
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//
//        user.sendEmailVerification()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                        }
//                    }
//                });
//        // [END send_email_verification]
//    }
//    public void sendEmailVerificationWithContinueUrl() {
//        // [START send_email_verification_with_continue_url]
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//
//        String url = "http://www.example.com/verify?uid=" + user.getUid();
//        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
//                .setUrl(url)
//                .setIOSBundleId("com.example.ios")
//                // The default for this is populated with the current android package name.
//                .setAndroidPackageName("com.example.android", false, null)
//                .build();
//
//        user.sendEmailVerification(actionCodeSettings)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                        }
//                    }
//                });
//
//        // [END send_email_verification_with_continue_url]
//        // [START localize_verification_email]
//        auth.setLanguageCode("fr");
//        // To apply the default app language instead of explicitly setting it.
//        // auth.useAppLanguage();
//        // [END localize_verification_email]
//    }
//    public void sendPasswordReset() {
//        // [START send_password_reset]
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String emailAddress = "user@example.com";
//
//        auth.sendPasswordResetEmail(emailAddress)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                        }
//                    }
//                });
//        // [END send_password_reset]
//    }



    public void getGoogleCredentials() {
        String googleIdToken = "";
        // [START auth_google_cred]
        AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);
        // [END auth_google_cred]
    }

//    public void getFbCredentials() {
//        AccessToken token = AccessToken.getCurrentAccessToken();
//        // [START auth_fb_cred]
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        // [END auth_fb_cred]
//    }

    public void testPhoneVerify() {
        // [START auth_test_phone_verify]
        String phoneNum = "+801954382055";
        String testVerificationCode = "123456";

        // Whenever verification is triggered with the whitelisted number,
        // provided it is not set for auto-retrieval, onCodeSent will be triggered.
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNum, 30L /*timeout*/, TimeUnit.SECONDS,
                this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        // Save the verification id somewhere
                        // ...
                        Toast.makeText(getApplicationContext()," Verification Code Sent",Toast.LENGTH_SHORT).show();

                        // The corresponding whitelisted code above should be used to complete sign-in.
                        Auth_MainActivity.this.enableUserManuallyInputCode();
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        // Sign in with the credential
                        // ...
                        Toast.makeText(getApplicationContext()," Verification Successful",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        // ...
                        Toast.makeText(getApplicationContext()," Verification Failed",Toast.LENGTH_SHORT).show();

                    }

                });
        // [END auth_test_phone_verify]
    }

    private void enableUserManuallyInputCode() {
        // No-op
    }

    public void testPhoneAutoRetrieve() {
        // [START auth_test_phone_auto]
        // The test phone number and code should be whitelisted in the console.
        String phoneNumber = "+16505554567";
        String smsCode = "123456";

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();

        // Configure faking the auto-retrieval with the whitelisted numbers.
        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode);

        PhoneAuthProvider phoneAuthProvider = PhoneAuthProvider.getInstance();
        phoneAuthProvider.verifyPhoneNumber(
                phoneNumber,
                60L,
                TimeUnit.SECONDS,
                this, /* activity */
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // Instant verification is applied and a credential is directly returned.
                        // ...
                    }

                    // [START_EXCLUDE]
                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                    }
                    // [END_EXCLUDE]
                });
        // [END auth_test_phone_auto]
    }


    private void updateUI(@Nullable FirebaseUser user) {
        session.setName(user.getDisplayName());
        session.setEamil(user.getEmail());
        session.setImageURL(user.getPhotoUrl().toString());
        session.setuId(user.getUid());

        if (session.getisAdminEmail()==true){
           // CT.success(getApplicationContext(),session.getEamil()+"\n"+session.getName());
//            Toast.makeText(getApplicationContext(),,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Teachers_Panel.class));
            finish();

        }if(session.getisAdminEmail()==false) {
           // Toast.makeText(getApplicationContext(),"Students Account",Toast.LENGTH_SHORT).show();
            //CT.success(getApplicationContext(),session.getEamil()+"\n"+session.getName());
            startActivity(new Intent(this,GeneralUser_Profile.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkCurrentUser();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkCurrentUser();

    }
    //    public void updateProfile() {
//        // [START update_profile]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName("Jane Q. User")
//                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
//                .build();
//
//        user.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User profile updated.");
//                        }
//                    }
//                });
//        // [END update_profile]
//    }
//    public void updateEmail() {
//        // [START update_email]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.updateEmail("user@example.com")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User email address updated.");
//                        }
//                    }
//                });
//        // [END update_email]
//    }
//    public void updatePassword() {
//        // [START update_password]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String newPassword = "SOME-SECURE-PASSWORD";
//
//        user.updatePassword(newPassword)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User password updated.");
//                        }
//                    }
//                });
//        // [END update_password]
//    }
//    public void icon_exit_and_back() {
//        // [START auth_fui_delete]
//        AuthUI.getInstance()
//                .icon_exit_and_back(this)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // ...
//                    }
//                });
//        // [END auth_fui_delete]
//    }

    //    public void reauthenticate() {
    //        // [START reauthenticate]
    //        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //
    //        // Get auth credentials from the user for re-authentication. The example below shows
    //        // email and password credentials but there are multiple possible providers,
    //        // such as GoogleAuthProvider or FacebookAuthProvider.
    //        AuthCredential credential = EmailAuthProvider
    //                .getCredential("user@example.com", "password1234");
    //
    //        // Prompt the user to re-provide their sign-in credentials
    //        user.reauthenticate(credential)
    //                .addOnCompleteListener(new OnCompleteListener<Void>() {
    //                    @Override
    //                    public void onComplete(@NonNull Task<Void> task) {
    //                        Log.d(TAG, "User re-authenticated.");
    //                    }
    //                });
    //        // [END reauthenticate]
    //    }

    //    public void authWithGithub() {
    //        FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //
    //        // [START auth_with_github]
    //        String token = "<GITHUB-ACCESS-TOKEN>";
    //        AuthCredential credential = GithubAuthProvider.getCredential(token);
    //        mAuth.signInWithCredential(credential)
    //                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
    //                    @Override
    //                    public void onComplete(@NonNull Task<AuthResult> task) {
    //                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
    //
    //                        // If sign in fails, display a message to the user. If sign in succeeds
    //                        // the auth state listener will be notified and logic to handle the
    //                        // signed in user can be handled in the listener.
    //                        if (!task.isSuccessful()) {
    //                            Log.w(TAG, "signInWithCredential", task.getException());
    //                            Toast.makeText(Auth_MainActivity.this, "Authentication failed.",
    //                                    Toast.LENGTH_SHORT).show();
    //                        }
    //
    //                        // ...
    //                    }
    //                });
    //        // [END auth_with_github]
    //    }


    //    public void linkAndMerge(AuthCredential credential) {
    //        FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //
    //        // [START auth_link_and_merge]
    //        FirebaseUser prevUser = FirebaseAuth.getInstance().getCurrentUser();
    //        mAuth.signInWithCredential(credential)
    //                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    //                    @Override
    //                    public void onComplete(@NonNull Task<AuthResult> task) {
    //                        FirebaseUser currentUser = task.getResult().getUser();
    //                        // Merge prevUser and currentUser accounts and data
    //                        // ...
    //                    }
    //                });
    //        // [END auth_link_and_merge]
    //    }


    //    public void unlink(String providerId) {
    //        FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //
    //        // [START auth_unlink]
    //        mAuth.getCurrentUser().unlink(providerId)
    //                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
    //                    @Override
    //                    public void onComplete(@NonNull Task<AuthResult> task) {
    //                        if (task.isSuccessful()) {
    //                            // Auth provider unlinked from account
    //                            // ...
    //                        }
    //                    }
    //                });
    //        // [END auth_unlink]
    //    }

    //    public void buildActionCodeSettings() {
    //        // [START auth_build_action_code_settings]
    //        ActionCodeSettings actionCodeSettings =
    //                ActionCodeSettings.newBuilder()
    //                        // URL you want to redirect back to. The domain (www.example.com) for this
    //                        // URL must be whitelisted in the Firebase Console.
    //                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
    //                        // This must be true
    //                        .setHandleCodeInApp(true)
    //                        .setIOSBundleId("com.example.ios")
    //                        .setAndroidPackageName(
    //                                "com.example.android",
    //                                true, /* installIfNotAvailable */
    //                                "12"    /* minimumVersion */)
    //                        .build();
    //        // [END auth_build_action_code_settings]
    //    }

    //    public void sendSignInLink(String email, ActionCodeSettings actionCodeSettings) {
    //        // [START auth_send_sign_in_link]
    //        FirebaseAuth auth = FirebaseAuth.getInstance();
    //        auth.sendSignInLinkToEmail(email, actionCodeSettings)
    //                .addOnCompleteListener(new OnCompleteListener<Void>() {
    //                    @Override
    //                    public void onComplete(@NonNull Task<Void> task) {
    //                        if (task.isSuccessful()) {
    //                            Log.d(TAG, "Email sent.");
    //                        }
    //                    }
    //                });
    //        // [END auth_send_sign_in_link]
    //    }

    //    public void verifySignInLink() {
    //        // [START auth_verify_sign_in_link]
    //        FirebaseAuth auth = FirebaseAuth.getInstance();
    //        Intent intent = getIntent();
    //        String emailLink = intent.getData().toString();
    //
    //        // Confirm the link is a sign-in with email link.
    //        if (auth.isSignInWithEmailLink(emailLink)) {
    //            // Retrieve this from wherever you stored it
    //            String email = "someemail@domain.com";
    //
    //            // The client SDK will parse the code from the link for you.
    //            auth.signInWithEmailLink(email, emailLink)
    //                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    //                        @Override
    //                        public void onComplete(@NonNull Task<AuthResult> task) {
    //                            if (task.isSuccessful()) {
    //                                Log.d(TAG, "Successfully signed in with email link!");
    //                                AuthResult result = task.getResult();
    //                                // You can access the new user via result.getUser()
    //                                // Additional user info profile *not* available via:
    //                                // result.getAdditionalUserInfo().getProfile() == null
    //                                // You can check if the user is new or existing:
    //                                // result.getAdditionalUserInfo().isNewUser()
    //                            } else {
    //                                Log.e(TAG, "Error signing in with email link", task.getException());
    //                            }
    //                        }
    //                    });
    //        }
    //        // [END auth_verify_sign_in_link]
    //    }

//    public void linkWithSignInLink(String email, String emailLink) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//
//        // [START auth_link_with_link]
//        // Construct the email link credential from the current URL.
//        AuthCredential credential =
//                EmailAuthProvider.getCredentialWithLink(email, emailLink);
//
//        // Link the credential to the current user.
//        auth.getCurrentUser().linkWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Successfully linked emailLink credential!");
//                            AuthResult result = task.getResult();
//                            // You can access the new user via result.getUser()
//                            // Additional user info profile *not* available via:
//                            // result.getAdditionalUserInfo().getProfile() == null
//                            // You can check if the user is new or existing:
//                            // result.getAdditionalUserInfo().isNewUser()
//                        } else {
//                            Log.e(TAG, "Error linking emailLink credential", task.getException());
//                        }
//                    }
//                });
//        // [END auth_link_with_link]
//    }

//    public void reauthWithLink(String email, String emailLink) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//
//        // [START auth_reauth_with_link]
//        // Construct the email link credential from the current URL.
//        AuthCredential credential =
//                EmailAuthProvider.getCredentialWithLink(email, emailLink);
//
//        // Re-authenticate the user with this credential.
//        auth.getCurrentUser().reauthenticateAndRetrieveData(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // User is now successfully reauthenticated
//                        } else {
//                            Log.e(TAG, "Error reauthenticating", task.getException());
//                        }
//                    }
//                });
//        // [END auth_reauth_with_link]
//    }

//    public void differentiateLink(String email) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//
//        // [START auth_differentiate_link]
//        auth.fetchSignInMethodsForEmail(email)
//                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                        if (task.isSuccessful()) {
//                            SignInMethodQueryResult result = task.getResult();
//                            List<String> signInMethods = result.getSignInMethods();
//                            if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
//                                // User can sign in with email/password
//                            } else if (signInMethods.contains(EmailAuthProvider.EMAIL_LINK_SIGN_IN_METHOD)) {
//                                // User can sign in with email/link
//                            }
//                        } else {
//                            Log.e(TAG, "Error getting sign in methods for user", task.getException());
//                        }
//                    }
//                });
//        // [END auth_differentiate_link]
//    }



//    public void getEmailCredentials() {
//        String email = "";
//        String password = "";
//        // [START auth_email_cred]
//        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
//        // [END auth_email_cred]
//    }

//    public void signOut() {
//        // [START auth_sign_out]
//        FirebaseAuth.getInstance().signOut();
//        // [END auth_sign_out]
//    }



//    public void gamesMakeGoogleSignInOptions() {
//        // [START games_google_signin_options]
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
//                .requestServerAuthCode(getString(R.string.default_web_client_id))
//                .build();
//        // [END games_google_signin_options]
//    }

    // [START games_auth_with_firebase]
    // Call this both in the silent sign-in task's OnCompleteListener and in the
    // Activity's onActivityResult handler.
//    private void firebaseAuthWithPlayGames(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithPlayGames:" + acct.getId());
//
//        final FirebaseAuth auth = FirebaseAuth.getInstance();
//        AuthCredential credential = PlayGamesAuthProvider.getCredential(acct.getServerAuthCode());
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = auth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(Auth_MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }
    // [END games_auth_with_firebase]

//    private void gamesGetUserInfo() {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//        // [START games_get_user_info]
//        FirebaseUser user = mAuth.getCurrentUser();
//        String playerName = user.getDisplayName();
//
//        // The user's Id, unique to the Firebase project.
//        // Do NOT use this value to authenticate with your backend server, if you
//        // have one; use FirebaseUser.getIdToken() instead.
//        String uid = user.getUid();
//        // [END games_get_user_info]
//    }
    //    public void deleteUser() {
//        // [START delete_user]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.icon_exit_and_back()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User account deleted.");
//                        }
//                    }
//                });
//        // [END delete_user]
//    }
}
