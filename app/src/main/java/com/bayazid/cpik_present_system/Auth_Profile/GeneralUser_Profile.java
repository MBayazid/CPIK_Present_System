package com.bayazid.cpik_present_system.Auth_Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bayazid.cpik_present_system.Auth_Profile.Fragments.ProfileFragment;
import com.bayazid.cpik_present_system.Auth_Profile.Fragments.StudentsFragment;
import com.bayazid.cpik_present_system.Auth_Profile.Fragments.TeachersFragment;
import com.bayazid.cpik_present_system.Auth_Profile.Fragments.WebViewFragment;
import com.bayazid.cpik_present_system.CommonFunctions;
import com.bayazid.cpik_present_system.DATA_SECTOR.Session;
import com.bayazid.cpik_present_system.R;
import com.bayazid.cpik_present_system.Students_List;
import com.bayazid.librarycpik.TerminalAnimation.SplashScreen;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mikhaellopez.circularimageview.CircularImageView;

public class GeneralUser_Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private Session session;
private TextView headerName,headerPhoneNumber;
private TextView headerEmail;
private CircularImageView headerPhoto;
private CommonFunctions commonFunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_user__profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session=new Session(this);
        //call CommonFunctions Class
        commonFunctions=new CommonFunctions();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setTitle(" ");




        View headerView = navigationView.getHeaderView(0);

        headerEmail=(TextView)headerView.findViewById(R.id.header_email);
        headerName= (TextView) headerView.findViewById(R.id.header_name);
        headerPhoneNumber= (TextView) headerView.findViewById(R.id.header_Mobile);
        headerPhoto=(CircularImageView) headerView.findViewById(R.id.header_image);

        if (!session.getEamil().equals(null)){
            headerPhoneNumber.setVisibility(View.GONE);
            headerEmail.setText(session.getEamil());
            headerName.setText(session.getName());
            commonFunctions.ImageGlider(getApplicationContext(),session.getImageURL(),headerPhoto);
            if (session.getisAdminEmail()==true){



            }
        } else if(!session.getPhoneNumbern().equals(null)){
//            headerPhoneNumber.setVisibility(View.VISIBLE);

            headerPhoneNumber.setText(session.getPhoneNumbern());

        }
        else {

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (session.getisAdminEmail()==true){



        }
        if (item == null){

        }

        if (id == R.id.nav_students) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_host,new StudentsFragment()).commit();
        } else if (id == R.id.nav_teachers) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_host,new TeachersFragment()).commit();
        }  else if (id == R.id.nav_prfile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_host,new ProfileFragment()).commit();
        } else if (id == R.id.nav_visit) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_host,new WebViewFragment()).commit();
        }  else if (id == R.id.nav_sign_out) {
            signOut();
        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GeneralUser_Profile.this,Auth_MainActivity.class));
                        finish();
                    }
                });
    }


}
