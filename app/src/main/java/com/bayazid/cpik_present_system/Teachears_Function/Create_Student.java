package com.bayazid.cpik_present_system.Teachears_Function;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bayazid.cpik_present_system.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class Create_Student extends AppCompatActivity {
    private Date_set spinnerData=new Date_set();
    private Button Create_STD;
    private EditText first_name, subjectCode,college_roll,reg_no;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String TAG = "Create_Student_Class";
    private DatePicker picker;
    private String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__student);
       Create_STD=findViewById(R.id.create_std);
       first_name=findViewById(R.id.first_name);
       subjectCode =findViewById(R.id.last_name);
       college_roll=findViewById(R.id.college_roll);
       reg_no=findViewById(R.id.reg_no);
        picker=(DatePicker)findViewById(R.id.datePicker1);

        final Spinner departments=findViewById(R.id.spinner_depertment);
        final Spinner semester=findViewById(R.id.spinner2_semester);

        ArrayAdapter<String> departmentsAdapter=new ArrayAdapter<>(this,R.layout.list_view_item_customized,spinnerData.Departments);
        departments.setAdapter(departmentsAdapter);

        ArrayAdapter<String> semesterAdapter=new ArrayAdapter<>(this,R.layout.list_view_item_customized,spinnerData.semesters);
        semester.setAdapter(semesterAdapter);
        //............................Calender View,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


        //button action
        Create_STD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 String Department = departments.getSelectedItem().toString();
//                String Semester=semester.getSelectedItem().toString();
//                String College_roll=college_roll.getText().toString();
//                String First_name=first_name.getText().toString();
                String SubJectCode= subjectCode.getText().toString();
                Date = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();
              //  String Registration_number=reg_no.getText().toString();

                db.collection("AttBook")
                        .document(SubJectCode)
                        .collection(Date).whereEqualTo("WasPresent",true)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                               //
                                int count;

                                if (document.get("WasPresent").equals(true))
                                { Toast.makeText(getApplicationContext(),"Total"+document.getId(),Toast.LENGTH_SHORT).show();
                                    first_name.setText("Total Students werw :");

                                }


                               // document.getId().length();


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });

                // startActivity(new Intent(Teacher_Class_type.this,Students_List.class));
                //  Teacher_Class_type.this.finish();

                // Create a new user with a first and last name
//                Map<String, Object> students_detail = new HashMap<>();
//                students_detail.put("first_name", First_name);
//                students_detail.put("subjectCode", SubJectCode);
//                students_detail.put("college_roll", College_roll);
//                students_detail.put("registration", Registration_number);

                // Add a new document with a generated ID
//                db.collection("students_collection").document(Department   ).collection(Semester).document(College_roll).set(students_detail)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(Create_Student.this, "Note saved", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(Create_Student.this, "Error!", Toast.LENGTH_SHORT).show();
//                                Log.d(TAG, e.toString());
//                            }
//                        });
            }
        });




    }
}
