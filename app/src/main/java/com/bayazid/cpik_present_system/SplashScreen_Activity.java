package com.bayazid.cpik_present_system;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bayazid.cpik_present_system.Auth_Profile.Auth_MainActivity;
import com.bayazid.cpik_present_system.Auth_Profile.Teachers_Panel;
import com.bayazid.cpik_present_system.DATA_SECTOR.Session;

public class SplashScreen_Activity extends AppCompatActivity {
    private ImageView logo;
    private RelativeLayout cityTop, cityBottom;
    private Animation upToDown, downToUp, fade,zoomIn;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_);

        session=new Session(getApplicationContext());

        final boolean admin= session.getisAdminEmail();
        final String title= session.getTitle();
        final String email= session.getEamil();
        final String name= session.getName();
        final String PicUrl=session.getImageURL();
        final String uId=session.getuId();
        final String idToken= session.getuIdToken();

        //init views
        logo = findViewById(R.id.logo);
        cityTop = findViewById(R.id.cityTop);
        cityBottom = findViewById(R.id.cityBottom);

        //set up animation
        upToDown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downToUp = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        fade = AnimationUtils.loadAnimation(this, R.anim.fadein);
//        cityTop.setAnimation(upToDown);
//        cityBottom.setAnimation(downToUp);
        logo.setAnimation(downToUp);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (admin!=false && title!=null&& email!=null&& name!=null&& PicUrl!=null&& uId!=null&& idToken!=null){
                    Intent intent = new Intent(SplashScreen_Activity.this, Teachers_Panel.class);
                    startActivity(intent);
                    finish();

                }else {
                    Intent intent = new Intent(SplashScreen_Activity.this, Auth_MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        },2000);
    }
}
