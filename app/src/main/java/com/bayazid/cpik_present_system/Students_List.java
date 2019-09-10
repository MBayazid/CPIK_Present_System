package com.bayazid.cpik_present_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.bayazid.cpik_present_system.Teachears_Function.Date_set;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

public class Students_List extends AppCompatActivity {
    private EditText CollegeRoll,subjectCode;
    private Button GetQ,Confirm_btn;
    private DatePicker picker;
    private DocumentReference  db,DB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private    String Email_Name,Person_Name,SubjectCode;
    private Date_set spinnerData=new Date_set();
    String Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




//        //Curent User
//        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();
//        Email_Name=mUser.getEmail();
//        Person_Name=mUser.getDisplayName();
//
//        setContentView(R.layout.activity_students__list);
//        Confirm_btn=findViewById(R.id.button_submit);
//        subjectCode=findViewById(R.id.editText_sub_code);
//        picker=(DatePicker)findViewById(R.id.datePicker1);
//
//        final Spinner departments=findViewById(R.id.spinner_depertment);
//        final Spinner semester=findViewById(R.id.spinner2_semester);
//        //set Adepters to spinners
//        ArrayAdapter<String> departmentsAdapter=new ArrayAdapter<>(this,R.layout.list_view_item_customized,spinnerData.Departments);
//        departments.setAdapter(departmentsAdapter);
//
//        ArrayAdapter<String> semesterAdapter=new ArrayAdapter<>(this,R.layout.list_view_item_customized,spinnerData.semesters);
//        semester.setAdapter(semesterAdapter);
//
//        Confirm_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Date = picker.getDayOfMonth()+" "+ (picker.getMonth() + 1)+" "+picker.getYear();
//                String Department=departments.getSelectedItem().toString();
//                String Semester=semester.getSelectedItem().toString();
//                String Sub_Code= SubjectCode.getText().toString();
//                db.collection(Email_Name)
//                        .document(Date)
//                        .collection(SubjectCode)
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Log.d("", document.getId() + " => " + document.getData());
//                                        Toast.makeText(getApplicationContext(),document.getId(),Toast.LENGTH_SHORT).show();
//                                    }
//                                } else {
//                                    Log.d("", "Error getting documents: ", task.getException());
//                                }
//                            }
//                        });
//
//            }
//        });
//


       // CollegeRoll=findViewById(R.id.college_roll);
       // GetQ=findViewById(R.id.get_std_data);

//        GetQ.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String StudentRoll=CollegeRoll.getText().toString();
//
//                Date = picker.getDayOfMonth()+" "+ (picker.getMonth() + 1)+" "+picker.getYear();
//            }
//        });



    }
}