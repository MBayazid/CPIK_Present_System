package com.bayazid.cpik_present_system.Teachears_Function;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bayazid.cpik_present_system.R;
import com.bayazid.cpik_present_system.Scan_Cpik_Server.Get_Student_Group_JSON;
import com.bayazid.cpik_present_system.Std_UI.STD_Recycler_Adapter;
import com.bayazid.cpik_present_system.DATA_SECTOR.Std_Data_set;
import com.bayazid.librarycpik.Recycler_Function.RecyclerTouchListener;
import com.bayazid.librarycpik.ToggleSwitchView.SwitchButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewStdInfo_RecycleView extends AppCompatActivity {
    private List<Std_Data_set> std_data_sets = new ArrayList<>();
    private RecyclerView recyclerView;
    private STD_Recycler_Adapter mAdapter;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private String Department,TechnologyName,Semester,SubjectCode,Date,TAG="View Student INFO Recycle.Activity";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private    String Email_Name,Person_Name;
    public SwitchButton switchButton;
    private boolean intentData,haveTname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__recycle_);

        Toolbar toolbar=findViewById(R.id.toolbar_std_recycle_view);
        //Curent User
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
         Email_Name=mUser.getEmail();
         Person_Name=mUser.getDisplayName();

        android.support.v7.widget.Toolbar toolbar_sts_recycle = (Toolbar) findViewById(R.id.toolbar_std_recycle_view);
        setSupportActionBar(toolbar_sts_recycle);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new STD_Recycler_Adapter(std_data_sets);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
               switchButton = view.findViewById(R.id.togglebtn_std_attendene);
               switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                       Std_Data_set stdDataSet = std_data_sets.get(position);

                       if (isChecked==true){
                           std_data_sets.get(position).setSelect(isChecked);
                           // Toast.makeText(ViewStdInfo_RecycleView.this,position,Toast.LENGTH_SHORT).show();
                           addStdAttendance(stdDataSet.getCollege_Roll());


                       }else {
                           //delete students Attendance function
                           stdAttendanceDelete(stdDataSet.getCollege_Roll());

                       }
                   }
               });
            }
            @Override
            public void onLongClick(View view, int position) {
                //Action ON Long Press Item
            }
        }));
      // start  getTechnologyName()
        //get Intent data
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("department")!= null && bundle.get("semester")!=null&& bundle.get("date")!=null&& bundle.get("subCode")!=null)
        {
            intentData=true;
            Department=bundle.getString("department");
            Semester=bundle.getString("semester");
            SubjectCode=bundle.getString("subCode");
            Date=bundle.getString("date");
        }else { intentData =false;}
                if (intentData ==true){
                    //get Technology Name
                    getTechnologyName();
                    //Creat Teachers Attendance Room
                    createTeachersRoom();
                    }else if (intentData==false){
                    startActivity(new Intent(getApplicationContext(),Teacher_Class_type.class));
                        ViewStdInfo_RecycleView.this.finish();}
    }
        //Creat Room for Teachers
        private void createTeachersRoom() {
            Map<String, Object> Teachers_class = new HashMap<>();
            Teachers_class.put("Teachers NAme",Person_Name);
            Teachers_class.put("visibility",true);
            //Do Add mode About Teachers
            db.collection(Email_Name)
                    .document(Date)
                    .set(Teachers_class)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ViewStdInfo_RecycleView.this, " Start Your Class...", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ViewStdInfo_RecycleView.this, "Plz Check Ur Network", Toast.LENGTH_SHORT).show();
                            //  Log.d(TAG, e.toString());
                        }
                    });
        }

    //Create Menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_attend_class, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_by_roll) {
                Toast.makeText(ViewStdInfo_RecycleView.this,"Method 2",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ViewStdInfo_RecycleView.this,Post_Students_Attendance.class));
                //call Session
                return true;
            }
           if (id == R.id.action_by_rescan) {
                Toast.makeText(ViewStdInfo_RecycleView.this,"Reloading Students List PLz WAit",Toast.LENGTH_LONG).show();
                Intent i= new Intent(ViewStdInfo_RecycleView.this, Get_Student_Group_JSON.class);
                i.putExtra("department",Department);
                i.putExtra("TechnologyName",TechnologyName);
                i.putExtra("semester",Semester);
                startActivity(i);
                //call Session
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
        //delete students Attendance function
        private void stdAttendanceDelete(final String deleteRoll) {
            db.collection("AttBook")
                    .document(Date)
                    .collection(SubjectCode)
                    .document(deleteRoll)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                           // Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            Toast.makeText(ViewStdInfo_RecycleView.this,deleteRoll+ " -- successfully Removed",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           // Log.w(TAG, "Error deleting document", e);
                            Toast.makeText(ViewStdInfo_RecycleView.this,"Error deleting "+ deleteRoll,Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        //add attendance Single Student
    private void addStdAttendance( final String College_roll) {

        Map<String, Object> students_Attendance = new HashMap<>();

        students_Attendance.put("SubjectCode", SubjectCode);
        students_Attendance.put("Semester", Semester);
        students_Attendance.put("WasPresent",true);


        // Add a new document with a generated ID
        db.collection("AttBook")
                .document(SubjectCode)
                .collection(Date)
                .document(College_roll)
                .set(students_Attendance)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ViewStdInfo_RecycleView.this, College_roll + " Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewStdInfo_RecycleView.this, "Not Successful!", Toast.LENGTH_SHORT).show();
                        //  Log.d(TAG, e.toString());
                    }
                });
    }

    private void getTechnologyName() {
        DocumentReference docRef =  db.collection("students_collection").document(Department);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.getId().equals(Department)){
                            TechnologyName= document.getString("TechnologyName");
                            //change title full_name
                            setTitle(TechnologyName+" > " + Semester +" > "+SubjectCode +" > "+Date);
                            setTitleColor(R.color.White);
                              //Toast.makeText(getApplicationContext(),"Department = "+TechnologyName+"\n Semester ="+Semester ,Toast.LENGTH_SHORT).show();
                              //get All STD Documents Data by Fields
                              getExpectedStudents();
                        }
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                        //If Not Existes
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
    //get All STD Documents Data by Fields
    private void getExpectedStudents() {
        //get All Documents Data by ID/Roll
        db.collection("students_collection")
                .document(Department)
                .collection(TechnologyName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("Semester").equals(Semester)){
                                    Std_Data_set stdDataSet= new Std_Data_set( document.getString("StudentName"), document.getString("BoardRoll"),document.getString("ClassRoll"), document.getString("Registration"),false);
                                    std_data_sets.add(stdDataSet);
                                    // notify adapter about data set changes  // so that it will render the list with new data
                                    mAdapter.notifyDataSetChanged();
                                } // "AdmissionDate" "InstituteCode"  "CurriculumCode" "TechnologyCode""Probidhan""ClassRoll"	"ClassRoll"	"Registration"	"Session""Semester"	"StudentName"
                            }
                        } else {
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(getApplicationContext(),"Getting Error "+ task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
