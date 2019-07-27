package com.bayazid.cpik_present_system.Teachears_Function;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.bayazid.cpik_present_system.R;
public class Post_Students_Attendance extends AppCompatActivity {
    private Button Set_Present;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__students__attendance);

        Set_Present=findViewById(R.id.make_active_button);



        //Action For Set Active btn
        Set_Present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



    }
}
