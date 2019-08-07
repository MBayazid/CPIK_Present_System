package com.bayazid.cpik_present_system.Teachears_Function;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bayazid.cpik_present_system.DATA_SECTOR.Session;
import com.bayazid.cpik_present_system.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class Teacher_Class_type extends AppCompatActivity {
    private Button Confirm_btn,GetQueryReport;
    private EditText SubjectCode;
    private   FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Date_set spinnerData=new Date_set();
    private DatePicker picker;
    String Date;
    private Session session;


    public static final String TAG = "Teachers_class_type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__class_type);
        //initialize components
        GetQueryReport=findViewById(R.id.button_get_report);
        Confirm_btn=findViewById(R.id.button_submit);
        SubjectCode=findViewById(R.id.editText_sub_code);
        picker=(DatePicker)findViewById(R.id.datePicker1);

        session=new Session(this);
        session.setTotalStudents(0);
        //Show Dialog Box
      //  showCustomDialog();
        final Spinner departments=findViewById(R.id.spinner_depertment);
        final Spinner semester=findViewById(R.id.spinner2_semester);
        //set Adepters to spinners
          ArrayAdapter<String> departmentsAdapter=new ArrayAdapter<>(this,R.layout.list_view_item_customized,spinnerData.Departments);
                departments.setAdapter(departmentsAdapter);

          ArrayAdapter<String> semesterAdapter=new ArrayAdapter<>(this,R.layout.list_view_item_customized,spinnerData.semesters);
                semester.setAdapter(semesterAdapter);

        //............................Calender View,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,




        //confirm action
        Confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date = picker.getDayOfMonth()+" "+ (picker.getMonth() + 1)+" "+picker.getYear();
                String Department=departments.getSelectedItem().toString();
                String Semester=semester.getSelectedItem().toString();
                String Sub_Code= SubjectCode.getText().toString();

                if (Sub_Code.isEmpty()==false &&  Sub_Code.length()>=4){
                    //send data to next Activity
                    Intent passIntent=new Intent(Teacher_Class_type.this, ViewStdInfo_RecycleView.class);
                    passIntent.putExtra("department",Department);
                    passIntent.putExtra("semester",Semester);
                    passIntent.putExtra("subCode",Sub_Code);
                    passIntent.putExtra("date", Date);
                    Toast.makeText(getApplicationContext()," Current Date is : " + Date, Toast.LENGTH_SHORT).show();
                    startActivity(passIntent);

                }
                else {SubjectCode.setError("Reinsert Subject Code....");}




            }
        });
            //create std btn action
            GetQueryReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Date = picker.getDayOfMonth()+" "+ (picker.getMonth() + 1)+" "+picker.getYear();
                    String Department=departments.getSelectedItem().toString();
                    String Semester=semester.getSelectedItem().toString();
                    String Sub_Code= SubjectCode.getText().toString();
                    if (Sub_Code.isEmpty()==false &&  Sub_Code.length()>=4){

              db.collection(session.getEamil())
                        .document(Date)
                        .collection(Sub_Code)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("", document.getId() + " => " + document.getData());
                                        Toast.makeText(getApplicationContext(),document.getId(),Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d("", "Error getting documents: ", task.getException());
                                }
                            }
                        });
                    }
                    else {SubjectCode.setError("Reinsert Subject Code....");}



                }
            });
    }




    @Override
    protected void onStart() {
        super.onStart();
        session.setTotalStudents(0);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        session.setTotalStudents(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.setTotalStudents(0);
    }
}
