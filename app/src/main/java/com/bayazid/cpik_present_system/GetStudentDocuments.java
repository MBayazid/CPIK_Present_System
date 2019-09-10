package com.bayazid.cpik_present_system;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.bayazid.cpik_present_system.DATA_SECTOR.Custom_Array;
import com.bayazid.cpik_present_system.DATA_SECTOR.Std_Data_set;
import com.bayazid.cpik_present_system.Teachears_Function.Teacher_Class_type;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GetStudentDocuments extends AppCompatActivity {
    private String Department,Semester;
    private boolean pathDirr=true;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    public static final String TAG = "GetStudentDocuments";
    private ListView listView_Std;
    private Custom_Array cAdapter;
   private ArrayList<Std_Data_set> std_data_sets = new ArrayList<>();
    private Button Confirm;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String User_NAme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_student_documents);
        Bundle bundle = getIntent().getExtras();
        listView_Std=findViewById(R.id.std_listView);
        Confirm=findViewById(R.id.confirm_btn);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeAlartDialog();
            }
        });
        //User
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        User_NAme= mUser.getEmail();




        if (pathDirr==true)
        {
            //get All Documents Data by ID/Roll
            getAllStudentsInfo();
        }
    }

    private void makeAlartDialog() {
        //dialog builder
        AlertDialog.Builder builder1 = new AlertDialog.Builder(GetStudentDocuments.this);
        builder1.setMessage("Submit 22 Students Attendances  following this Context");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(GetStudentDocuments.this, Teacher_Class_type.class));
                        GetStudentDocuments.this.finish();
                        Toast.makeText(getApplicationContext(),"Students Attendance Data Submitted Successfully ",Toast.LENGTH_LONG).show();


                    }
                });

        builder1.setNegativeButton(
                "Deny",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void getAllStudentsInfo() {

      //  get All Documents Data by ID/Roll
        db.collection(User_NAme)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                    std_data_sets.add(new Std_Data_set(document.getId()));

                                    cAdapter = new Custom_Array(getApplicationContext(),std_data_sets);
                                    listView_Std.setAdapter(cAdapter);
                            }


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(getApplicationContext(),"Getting Error "+ task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getAllStudentsInfo(String time) {

      //  get All Documents Data by ID/Roll
        db.collection(User_NAme).document(time)
                .collection("Subject_Code")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                    std_data_sets.add(new Std_Data_set(document.getId()));

                                    cAdapter = new Custom_Array(getApplicationContext(),std_data_sets);
                                    listView_Std.setAdapter(cAdapter);
                            }


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(getApplicationContext(),"Getting Error "+ task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
