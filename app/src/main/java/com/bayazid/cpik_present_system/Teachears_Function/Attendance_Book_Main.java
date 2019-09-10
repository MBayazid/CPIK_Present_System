package com.bayazid.cpik_present_system.Teachears_Function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bayazid.cpik_present_system.DATA_SECTOR.Session;
import com.bayazid.cpik_present_system.R;
import com.bayazid.cpik_present_system.DATA_SECTOR.Std_Data_set;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
private String DBPath_Date,collegeRoll,subjectCode;
private EditText Scode,Croll;
private Session session;

private MyAdapter adapter;
   private RecyclerView recyclerView;
    private List<Std_Data_set> std_data_sets= new ArrayList<>();
    private TextView viewTotal,viewSingle,viewSingle_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_attendance__book__main);

//        mUser=mAuth.getCurrentUser();
//        UserEmail=mUser.getEmail();
        session=new Session(this);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_attendance_book);
        Scode=findViewById(R.id.tollbar_subcode);
        Croll=findViewById(R.id.tollbar_roll);
        viewTotal=findViewById(R.id.total_students);
        viewSingle=findViewById(R.id.single_students);
        viewSingle_name=findViewById(R.id.single_students_name);

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
         DBPath_Date = stdDataSet.getDbPathId();

         subjectCode=Scode.getText().toString();
         collegeRoll=Croll.getText().toString();

       // Toast.makeText(getApplicationContext(),document.getData().values().toString(),Toast.LENGTH_SHORT).show();

       if (subjectCode.isEmpty()==false ){
             db.collection(session.getEamil())
                     .document(DBPath_Date)
                     .collection(subjectCode).whereGreaterThanOrEqualTo("Total",0)
                     .get()
                     .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<QuerySnapshot> task) {
                             if (task.isSuccessful()) {
                                 for (QueryDocumentSnapshot document : task.getResult()) {
                                     // Toast.makeText(getApplicationContext(),""+ document.getData().values(),Toast.LENGTH_SHORT).show();
                                     viewTotal.setText(document.getData().values().toString());
                                 }
                             } else {
                                 Log.d("", "Error getting documents: ", task.getException());
                             }
                         }
                     });

                       if (!collegeRoll.equals(null)){
                           viewSingle.setText("false");
                           db.collection(session.getEamil())
                                   .document(DBPath_Date)
                                   .collection(subjectCode).whereEqualTo("CollegeRoll",collegeRoll)
                                   .get()
                                   .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                       @Override
                                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                           if (task.isSuccessful()) {
                                               for (QueryDocumentSnapshot document : task.getResult()) {
                                                   Toast.makeText(getApplicationContext(),""+ document.getData().values(),Toast.LENGTH_SHORT).show();
                                                     viewSingle.setText(document.getBoolean("WasPresent").toString());
                                                     viewSingle_name.setText(document.getString("Name")+"\n"+document.getString("CollegeRoll"));

                                               }
                                           } else {

                                               Log.d("", "Error getting documents: ", task.getException());
                                           }
                                       }
                                   }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(getApplicationContext(),"Nul",Toast.LENGTH_SHORT).show();
                               }
                           });

                       }
         }else {Scode.setError("Can't Be Empty");}
      //  String total= db.collection(DBPath_Date).document("Total").toString();



      //  Toast.makeText(Attendance_Book_Main.this,total,Toast.LENGTH_SHORT).show();
//        Intent i=new Intent(this,Attendance_Book_Child_1.class);
//        i.putExtra("dbpath",DBPath_Date);
//        startActivity(i);

    }
    private void getAttendenceBook() {

        //  get All Documents Data by ID/Roll
        db.collection(session.getEamil())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Std_Data_set stdDataSet=new Std_Data_set(document.getId(),document.getData().values().toString());
                               // Toast.makeText(getApplicationContext(),document.getData().values().toString(),Toast.LENGTH_SHORT).show();
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
