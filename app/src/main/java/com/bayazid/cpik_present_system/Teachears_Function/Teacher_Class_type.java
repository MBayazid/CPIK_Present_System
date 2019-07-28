package com.bayazid.cpik_present_system.Teachears_Function;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bayazid.cpik_present_system.R;
import com.google.firebase.firestore.FirebaseFirestore;



public class Teacher_Class_type extends AppCompatActivity {
    private Button Confirm_btn,Create_Students;
    private EditText SubjectCode;
    private   FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Date_set spinnerData=new Date_set();
    private DatePicker picker;
    String Date;


    public static final String TAG = "Teachers_class_type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__class_type);
        //initialize components
        Confirm_btn=findViewById(R.id.button_submit);
        SubjectCode=findViewById(R.id.editText_sub_code);
        picker=(DatePicker)findViewById(R.id.datePicker1);
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

                    Intent passIntent=new Intent(Teacher_Class_type.this, ViewStdInfo_RecycleView.class);
                    //send data to next Activity
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
    //        Create_Students.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View view) {
    //                startActivity(new Intent(getApplicationContext(),Create_Student.class));
    //            }
    //        });
    }


    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity view group
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.select_student_type_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        //alertDialog.show();
    }

}
