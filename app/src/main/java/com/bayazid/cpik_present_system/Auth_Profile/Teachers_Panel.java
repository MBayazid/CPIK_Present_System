package com.bayazid.cpik_present_system.Auth_Profile;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bayazid.cpik_present_system.CommonFunctions;
import com.bayazid.cpik_present_system.DATA_SECTOR.Session;
import com.bayazid.cpik_present_system.Teachears_Function.Attendance_Book_Main;
import com.bayazid.cpik_present_system.R;

import com.bayazid.cpik_present_system.Teachears_Function.Teacher_Class_type;
import com.bayazid.librarycpik.TerminalAnimation.SplashScreen;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.circular_profile_picture.CircularView;

public class Teachers_Panel extends AppCompatActivity {
      private Session session;
   private FirebaseAuth mAuth;
   private FirebaseUser mUser;
  private   TextView Name, Email;
  private   CircularView ProfilePic;
   private GoogleSignInClient mGoogleSignInClient;
   private CommonFunctions commonFunctions;
    private Animation slidOut, downToUp,fade,ZoomOut,ZoomIn,Bounce;


    private Button SignOut, View_Schduled_Attendance_Date,Take_Attendance,View_Attendance_Book, EditAttendance,Single_STD_Qurey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //start Animation
        SplashScreen.show(this,SplashScreen.TERMINAL_ANIMATION);


        setContentView(R.layout.activity_teachers__panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //call Session data
        session=new Session(this);
        //call CommonFunctions Class
        commonFunctions=new CommonFunctions();
        //initillize id/+
        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        ProfilePic = findViewById(R.id.profilepic);
        // SignOut = findViewById(R.id.signout);
        View_Schduled_Attendance_Date = findViewById(R.id.view_scheduled_attendance_book_button4);
        Take_Attendance = findViewById(R.id.take_attendance_button);
        View_Attendance_Book = findViewById(R.id.view_attendance_book_button3);
        EditAttendance = findViewById(R.id.edit_already_taken_btn);
        Single_STD_Qurey = findViewById(R.id.single_std_query__button4);
        SignOut = findViewById(R.id.sign_out_button5);



        //firebase Auth innit
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

            String name = session.getName();
            String email = session.getEamil();
            String PhotoUri = session.getImageURL();

        //update Teachers profile infoin Session
        updateAdminProfileSecssion();

            //set profile view
            Name.setText(name);
            Email.setText(email);
            commonFunctions.ImageGlider(getApplicationContext(),PhotoUri,ProfilePic);

            EditAttendance.setOnClickListener(new View.OnClickListener() {
            //Add EditAttendance Action
            @Override
            public void onClick(View view) {// load the animation
//                Bounce = AnimationUtils.loadAnimation(getApplicationContext(),
//                        R.anim.bounce);
//                EditAttendance.startAnimation(Bounce);
               // startActivity(new Intent(Teachers_Panel.this, Create_Student.class));
            }
        });

        View_Attendance_Book.setOnClickListener(new View.OnClickListener() {
            //View Attendance Book
            @Override
            public void onClick(View view) {// load the animation
                Bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                View_Attendance_Book.startAnimation(Bounce);
                Snackbar.make(view, "UP Cumming Feature", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        View_Schduled_Attendance_Date.setOnClickListener(new View.OnClickListener() {
            //view Full Semester Action
            @Override
            public void onClick(View view) {
                // load the animation
//                Bounce = AnimationUtils.loadAnimation(getApplicationContext(),
//                        R.anim.xoom_out);
//                View_Schduled_Attendance_Date.startAnimation(Bounce);
                startActivity(new Intent(getApplicationContext(), Attendance_Book_Main.class));
            }
        });
        Take_Attendance.setOnClickListener(new View.OnClickListener() {
            //take attendance action
            @Override
            public void onClick(View view) {
                // load the animation
//                Bounce = AnimationUtils.loadAnimation(getApplicationContext(),
//                        R.anim.xoom_out);
//                Take_Attendance.startAnimation(Bounce);
//                startActivity(new Intent(getApplicationContext(), Post_Students_Attendance.class));
                startActivity(new Intent(getApplicationContext(), Teacher_Class_type.class));

            }
        });

        Single_STD_Qurey.setOnClickListener(new View.OnClickListener() {
            //take Single_STD_Qurey action
            @Override
            public void onClick(View view) {
                // load the animation
                Bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                Single_STD_Qurey.startAnimation(Bounce);
                Snackbar.make(view, "UP Cumming Feature", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
              //  startActivity(new Intent(getApplicationContext(), Students_List.class));
            }
        });
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        //FloatingActionButton
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // load the animation
                Bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                fab.startAnimation(Bounce);
                Snackbar.make(view, "UP Cumming Feature", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void updateAdminProfileSecssion() {
        //store data to Sesion
        session.setTitle("SignIn");
        session.setEamil(mUser.getEmail());
        session.setName(mUser.getDisplayName());
        session.setImageURL(mUser.getPhotoUrl().toString());
        session.setuId(mUser.getUid());
        session.setuIdToken(mUser.getProviderId());
        session.setisAdminEmail(true);
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
                        // ...Clear Session
                        session.setisAdminEmail(false);
                        session.setTitle("SignOut");
                        session.setEamil(null);
                        session.setName(null);
                        session.setImageURL(null);
                        session.setuId(null);
                        session.setuIdToken(null);
                        session.setPhoneNumber(null);

                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Teachers_Panel.this,Auth_MainActivity.class));
                        finish();
                    }
                });
    }


}
