package com.bayazid.cpik_present_system.Teachears_Function;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bayazid.cpik_present_system.R;
import com.bayazid.cpik_present_system.DATA_SECTOR.Std_Data_set;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Attendance_Book_Child_1 extends AppCompatActivity{
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private String DBPath;
    private boolean intentData;
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private List<Std_Data_set> std_data_sets= new ArrayList<>();
    private Button TotalAttendance;
    private TextView totalStd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance__book__child_1);

        TotalAttendance= findViewById(R.id.total_button);
        totalStd= findViewById(R.id.total_students);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView = findViewById(R.id.recycler_view_attendance_book_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(Attendance_Book_Child_1.this,std_data_sets);
        recyclerView.setAdapter(adapter);


        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("dbpath")!= null)
        { //set bollen true
            intentData=true;
            DBPath=bundle.getString("dbpath");
            Toast.makeText(Attendance_Book_Child_1.this,DBPath,Toast.LENGTH_SHORT).show();
            //change title full_name
            setTitle("Query Report");
            setTitleColor(R.color.White);
        }
        else {  intentData=false; }

        if (intentData==true){



        }
        TotalAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              String n=  db.collection("musanna324@gmail.com").document(DBPath).collection("2251").document("666").collection("1").document().getId().toString();
//                Toast.makeText(Attendance_Book_Child_1.this,n,Toast.LENGTH_SHORT).show();
//                db.collection("musanna324@gmail.com")
//                        .document(DBPath)
//                        .collection("2251").
//                        document("666")
//                        .collection("1")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Std_Data_set stdDataSet=new Std_Data_set(document.getId());
//                                std_data_sets.add(stdDataSet);
//                             adapter.notifyDataSetChanged();
//
//
//
//
//                            } // totalStd.setText(adapter.getItemCount());
//
//                        } else {
//                            // Log.d(TAG, "Error getting documents: ", task.getException());
//                            Toast.makeText(getApplicationContext(),"Getting Error "+ task.getException(),Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });

            }

        });





    }
    private void getAttendenceBook() {

        //  get All Documents Data by ID/Roll
        db.collection(DBPath+"/6644")
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
