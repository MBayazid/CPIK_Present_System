package com.bayazid.cpik_present_system.Auth_Profile;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;
import android.widget.Toast;

import com.bayazid.cpik_present_system.Auth_Profile.Fragments.ProfileFragment;
import com.bayazid.cpik_present_system.Auth_Profile.Fragments.StudentsFragment;
import com.bayazid.cpik_present_system.Auth_Profile.Fragments.TeachersFragment;
import com.bayazid.cpik_present_system.Auth_Profile.Fragments.WebViewFragment;
import com.bayazid.cpik_present_system.CommonFunctions;
import com.bayazid.cpik_present_system.DATA_SECTOR.Session;
import com.bayazid.cpik_present_system.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.circular_profile_picture.*;

public class GeneralUser_Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Session session;
    private TextView headerName, headerPhoneNumber;
    private TextView headerEmail;
    private CircularView headerPhoto;
    private CommonFunctions commonFunctions;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_user__profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setTitle(" ");

        session = new Session(this);
        //call CommonFunctions Class
        commonFunctions = new CommonFunctions();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        headerEmail = (TextView) headerView.findViewById(R.id.header_email);
        headerName = (TextView) headerView.findViewById(R.id.header_name);
        headerPhoneNumber = (TextView) headerView.findViewById(R.id.header_Mobile);
        headerPhoto = (CircularView) headerView.findViewById(R.id.header_image);
        //Gone ByDefult
        headerEmail.setVisibility(View.GONE);
        headerName.setVisibility(View.GONE);
        headerPhoto.setVisibility(View.GONE);
        headerPhoneNumber.setVisibility(View.GONE);


        if (session.getEamil() != null) {
            //Email is visible for now
            headerEmail.setVisibility(View.VISIBLE);
            //Name and Photo is Visible
            headerName.setVisibility(View.VISIBLE);

            headerPhoto.setVisibility(View.VISIBLE);

            headerEmail.setText(session.getEamil());
            headerName.setText(session.getName());
            commonFunctions.ImageGlider(getApplicationContext(), session.getImageURL(), headerPhoto);
            if (session.getisAdminEmail()) {
                setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_host, new TeachersFragment()).commit();
            } else {
                setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_host, new StudentsFragment()).commit();
            }
        } else if (session.getPhoneNumbern() != null) {
            headerPhoneNumber.setVisibility(View.VISIBLE);
            headerPhoneNumber.setText(session.getPhoneNumbern());

        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.general_user__profile, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_signout) {
//
//                // [START auth_fui_signout]
//                AuthUI.getInstance()
//                        .signOut(this)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            public void onComplete(@NonNull Task<Void> task) {                        // ...
//                                Toast.makeText(getApplicationContext(),"Signed Out", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(GeneralUser_Profile.this,Auth_MainActivity.class));
//                                finish();
//                            }
//                        });
//            return true;
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_prfile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_host, new ProfileFragment()).commit();
        } else if (id == R.id.nav_visit) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_host, new WebViewFragment()).commit();
        } else if (id == R.id.nav_sign_out) {
            signOut();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_exit) {

            if (commonFunctions.getdialogResult()==-1){
                commonFunctions.setdialogResult(0);
                this.finish();
            }else { commonFunctions.DialogWarning(this,"","Are You sure you want to exit?","Yes","No");}


        } else if (id == R.id.nav_home) {
            if (session.getisAdminEmail()) {
                setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_host, new TeachersFragment()).commit();
            } else {
                setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_host, new StudentsFragment()).commit();
            }

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

                        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GeneralUser_Profile.this, Auth_MainActivity.class));
                        finish();
                    }
                });
    }


}
