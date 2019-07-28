package com.bayazid.cpik_present_system.Auth_Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bayazid.cpik_present_system.DATA_SECTOR.Session;
import com.bayazid.cpik_present_system.Teachears_Function.Attendance_Book_Main;
import com.bayazid.cpik_present_system.R;

import com.bayazid.cpik_present_system.Teachears_Function.Create_Student;
import com.bayazid.cpik_present_system.Teachears_Function.Teacher_Class_type;
import com.bayazid.librarycpik.TerminalAnimation.SplashScreen;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;

public class Teachers_Panel extends AppCompatActivity {
       Session session;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextView Name, Email;
    CircularImageView ProfilePic;
    GoogleSignInClient mGoogleSignInClient;

    private Button SignOut, View_Schduled_Attendance,Take_Attendance,View_Attendance_Book, EditAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers__panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //call Session data
        session=new Session(this);
        //initillize id/+
        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        ProfilePic = findViewById(R.id.profilepic);
        // SignOut = findViewById(R.id.signout);
        View_Schduled_Attendance = findViewById(R.id.view_scheduled_attendance_book_button4);
        Take_Attendance = findViewById(R.id.take_attendance_button);
        View_Attendance_Book = findViewById(R.id.view_attendance_book_button3);
        EditAttendance = findViewById(R.id.edit_already_taken_btn);

        //firebase Auth innit
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        session.setTitle("SignIn");
        session.setName(mUser.getDisplayName());
        session.setImageURL(mUser.getPhotoUrl().toString());
        session.setuId(mUser.getUid().toString());

            String name = session.getName();
            String email = session.getEamil();
            String PhotoUri = session.getImageURL();

            Name.setText(name);
            Email.setText(email);
        //start Animation
        SplashScreen.show(this,SplashScreen.TERMINAL_ANIMATION);

        RequestOptions options = new RequestOptions()
                .circleCrop()
                .centerCrop()
                .placeholder(R.drawable.ic_round_user)
                .error(R.drawable.ic_round_user);
        Glide.with(Teachers_Panel.this).load(PhotoUri).apply(options).into(ProfilePic);
        //update Teachers profile info
        updateAdminProfile();

        //Add Std Action
        EditAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Teachers_Panel.this, Create_Student.class));
            }
        });
        //View Attendance Book
        View_Attendance_Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //view Full Semester Action
        View_Schduled_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Attendance_Book_Main.class));


            }
        });
        //take attendance action
        Take_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), Post_Students_Attendance.class));
                startActivity(new Intent(getApplicationContext(), Teacher_Class_type.class));
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "UP Cumming Feature", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void updateAdminProfile() {
        //add profil info


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teachers__panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_SignOut) {
            Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_SHORT).show();
            //call Session
           signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        session.setisAdminEmail(false);
                        Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Teachers_Panel.this,Auth_MainActivity.class));
                        finish();
                    }
                });
//        session.setTitle("SignOut");
//       Intent i=new Intent(Teachers_Panel.this,LoginActivity.class);
//       i.putExtra("requestSignout","SignOut");
//       startActivity(i);
//        finish();
        // [END auth_sign_out]
    }

}
