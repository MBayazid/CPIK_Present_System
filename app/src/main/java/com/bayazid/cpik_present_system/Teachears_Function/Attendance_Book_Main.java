package com.bayazid.cpik_present_system.Teachears_Function;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bayazid.cpik_present_system.R;
import com.bayazid.cpik_present_system.DATA_SECTOR.Std_Data_set;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Attendance_Book_Main extends AppCompatActivity implements MyAdapter.ItemClickListener{
private FirebaseFirestore db=FirebaseFirestore.getInstance();
private FirebaseAuth mAuth=FirebaseAuth.getInstance();
private FirebaseUser mUser;
private String UserEmail,UserName,DBPath;

private MyAdapter adapter;
   private RecyclerView recyclerView;
    private List<Std_Data_set> std_data_sets= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance__book__main);

        mUser=mAuth.getCurrentUser();
        UserEmail=mUser.getEmail();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_attendance_book);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView = findViewById(R.id.recycler_view_attendance_book);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(Attendance_Book_Main.this,std_data_sets);
       adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        getAttendenceBook();

    }
    @Override
    public void onItemClick(View view, int position) {
        Std_Data_set stdDataSet = std_data_sets.get(position);
         DBPath= UserEmail+"/"+stdDataSet.getDbPathId();
        Intent i=new Intent(this,Attendance_Book_Child_1.class);
        i.putExtra("dbpath",DBPath);
        startActivity(i);

    }
    private void getAttendenceBook() {

        //  get All Documents Data by ID/Roll
        db.collection(UserEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Std_Data_set stdDataSet=new Std_Data_set(document.getId());


                                std_data_sets.add(stdDataSet);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                           // Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(getApplicationContext(),"Getting Error "+ task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
