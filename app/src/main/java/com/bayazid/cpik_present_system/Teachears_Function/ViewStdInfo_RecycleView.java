package com.bayazid.cpik_present_system.Teachears_Function;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bayazid.cpik_present_system.DATA_SECTOR.Session;
import com.bayazid.cpik_present_system.R;
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
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String Department, TechnologyName, Semester, SubjectCode, Date, TAG = "View Student INFO Recycle.Activity";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String Email_Name, Person_Name;
    public SwitchButton switchButton;
    private boolean intentData, haveTname;
    private TextView Header_Technology, Header_Semester, Header_Subject, Header_Date;
    private Session session;
    private TextView ViewTotalStudents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        setContentView(R.layout.activity_student__recycle_);

        Toolbar toolbar = findViewById(R.id.toolbar_std_recycle_view);
        setSupportActionBar(toolbar);

        Header_Technology = findViewById(R.id.tollbar_techonology);
        Header_Semester = findViewById(R.id.tollbar_semester);
        Header_Subject = findViewById(R.id.tollbar_subcode);
        Header_Date = findViewById(R.id.tollbar_date);
        ViewTotalStudents = findViewById(R.id.total_students);

        session = new Session(this);


        //Curent User
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Email_Name = mUser.getEmail();
        Person_Name = mUser.getDisplayName();


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

                        if (isChecked == true) {
                            //set Switch= true at current position
                            std_data_sets.get(position).setSelect(isChecked);
                            //Toast.makeText(ViewStdInfo_RecycleView.this,session.getTotalStudents()+" Total",Toast.LENGTH_SHORT).show();
                            addStdAttendance(stdDataSet.getCollege_Roll(), stdDataSet.getFirst_Name());
                        } else {
                            //set Switch= false at current position
                            std_data_sets.get(position).setSelect(isChecked);
                            //icon_exit_and_back students Attendance function
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
        if (bundle.getString("department") != null && bundle.get("semester") != null && bundle.get("date") != null && bundle.get("subCode") != null) {
            intentData = true;
            Department = bundle.getString("department");
            Semester = bundle.getString("semester");
            SubjectCode = bundle.getString("subCode");
            Date = bundle.getString("date");
        }
        else {
            intentData = false;
        }
        if (intentData == true) {
            //get Technology Name
            getTechnologyName();

        }
        else if (intentData == false) {
            startActivity(new Intent(getApplicationContext(), Teacher_Class_type.class));
            ViewStdInfo_RecycleView.this.finish();
        }
    }


    private void createTeachersRoom() {
//Creat Room for Teachers / make date visible
        final Map<String, Object> Teachers_class = new HashMap<>();
        // Teachers_class.put("TeacherName",session.getName());
        //Teachers_class.put("date", new Timestamp(Date);
        Teachers_class.put((Department + Semester), SubjectCode);
//            Teachers_class.put("visibility",true);
        //Do Add mode About Teachers

        db.collection(session.getEamil())
                .document(Date)
                .update(Teachers_class)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ViewStdInfo_RecycleView.this, "Your Class is Added.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Toast.makeText(ViewStdInfo_RecycleView.this, "Plz Check Ur Network", Toast.LENGTH_SHORT).show();
                        db.collection(session.getEamil())
                                .document(Date).set(Teachers_class);
                        //  Log.d(TAG, e.toString());
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Create Menu
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attend_class, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter != null) mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    //.........................
    //icon_exit_and_back students Attendance function
    private void stdAttendanceDelete(final String deleteRoll) {
        //view total attendance
        session.setTotalStudents(session.getTotalStudents() - 1);
        ViewTotalStudents.setText(" " + session.getTotalStudents());

        final Map<String, Object> TotalStudentCount = new HashMap<>();
        TotalStudentCount.put("Total", session.getTotalStudents());

        db.collection(Email_Name)
                .document(Date)
                .collection(SubjectCode)
                .document(deleteRoll)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //view total

                        db.collection(Email_Name)
                                .document(Date)
                                .collection(SubjectCode).document("Total").set(TotalStudentCount);

                        // Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Toast.makeText(ViewStdInfo_RecycleView.this, deleteRoll + " Removed", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log.w(TAG, "Error deleting document", e);
                        Toast.makeText(ViewStdInfo_RecycleView.this, "Error Retry.." + deleteRoll, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //add attendance Single Student
    private void addStdAttendance(final String College_roll, String Name) {
        //view total attendance
        session.setTotalStudents(1 + session.getTotalStudents());
        ViewTotalStudents.setText(" " + session.getTotalStudents());

        Map<String, Object> students_Attendance = new HashMap<>();
        students_Attendance.put("Date", Date);
        students_Attendance.put("Name", Name);
        students_Attendance.put("CollegeRoll", College_roll);
        students_Attendance.put("SubjectCode", SubjectCode);
        students_Attendance.put("Semester", Semester);
        students_Attendance.put("Technology", Department);
        students_Attendance.put("WasPresent", true);

        final Map<String, Object> TotalStudentCount = new HashMap<>();
        TotalStudentCount.put("Total", session.getTotalStudents());


        // Add a new Student's present list with a generated ID
        db.collection(Email_Name)
                .document(Date)
                .collection(SubjectCode)
                .document(College_roll)
                .set(students_Attendance)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Toast.makeText(ViewStdInfo_RecycleView.this, College_roll + " Added", Toast.LENGTH_SHORT).show();
                        db.collection(Email_Name)
                                .document(Date)
                                .collection(SubjectCode).document("Total").set(TotalStudentCount);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        session.setTotalStudents(session.getTotalStudents() - 1);
                        ViewTotalStudents.setText(" " + session.getTotalStudents());
                        Toast.makeText(ViewStdInfo_RecycleView.this, "Try Again PLZ", Toast.LENGTH_SHORT).show();
                        //  Log.d(TAG, e.toString());
                    }
                });
    }

    private void getTechnologyName() {
        //Get
        DocumentReference docRef = db.collection("students_collection").document(Department);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.getId().equals(Department)) {
                            TechnologyName = document.getString("TechnologyName");

                            Header_Technology.setText(TechnologyName);
                            Header_Semester.setText(Semester);
                            Header_Subject.setText(SubjectCode);
                            Header_Date.setText(Date);
                            //get All STD Documents Data by Fields
                            getExpectedStudents();
                        }
                        // Toast.makeText(getApplicationContext(),"Getting Error "+ task.getException(),Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        // Toast.makeText(getApplicationContext(),"Getting Error "+ task.getException(),Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "No such document");
                        //If Not Existes
                    }
                } else {
                    // Toast.makeText(getApplicationContext(),"Getting Error "+ task.getException(),Toast.LENGTH_SHORT).show();
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
                                if (document.getString("Semester").equals(Semester)) {
                                    Std_Data_set stdDataSet = new Std_Data_set(document.getString("StudentName"), document.getString("BoardRoll"), document.getString("ClassRoll"), document.getString("Registration"), false);
                                    std_data_sets.add(stdDataSet);
                                    // notify adapter about data set changes  // so that it will render the list with new data
                                    mAdapter.notifyDataSetChanged();
                                } // "AdmissionDate" "InstituteCode"  "CurriculumCode" "TechnologyCode""Probidhan""ClassRoll"	"ClassRoll"	"Registration"	"Session""Semester"	"StudentName"
                            }
                        } else {
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(getApplicationContext(), "Getting Error " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.setTotalStudents(0);
    }

    @Override
    public void onBackPressed() {

        showCustomDialogConfirmClass();
        // super.onBackPressed();

    }

    private void showCustomDialogConfirmClass() {
        //before inflating the custom alert dialog layout, we will get the current activity view group
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.select_student_type_dialog, viewGroup, false);

        Button Yes = dialogView.findViewById(R.id.buttonOk);
        Button No = dialogView.findViewById(R.id.buttonNo);
        Button Edit_Class = dialogView.findViewById(R.id.edit_class);

        TextView Date_class = dialogView.findViewById(R.id.tollbar_date);
        TextView Technilogy = dialogView.findViewById(R.id.tollbar_techonology);
        TextView Semester_Class = dialogView.findViewById(R.id.tollbar_semester);
        TextView Subject = dialogView.findViewById(R.id.tollbar_subcode);
        TextView Total = dialogView.findViewById(R.id.tollbar_total);
        if (!Department.isEmpty() && !TechnologyName.isEmpty() && !Semester.isEmpty() && !SubjectCode.isEmpty() && !Date.isEmpty() && session.getTotalStudents() >= 0) {

            Date_class.setText(Date);
            Technilogy.setText(Department);
            Semester_Class.setText(Semester);
            Subject.setText(SubjectCode);
            Total.setText(": " + session.getTotalStudents());
            // Toast.makeText(getApplicationContext(), "Total"+session.getTotalStudents(), Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);


        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(getApplicationContext(), "Total", Toast.LENGTH_SHORT).show();
                //Creat Teachers Attendance Room

                // Read from the database
                createTeachersRoom();
                alertDialog.setCancelable(true);
                alertDialog.cancel();
                finish();
//
                //  onBackPressed(true);

            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Class Removed", Toast.LENGTH_SHORT).show();
                alertDialog.setCancelable(true);
                alertDialog.cancel();
                finish();
                //  onBackPressed();


            }
        });
        Edit_Class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Edit Class", Toast.LENGTH_SHORT).show();
                alertDialog.setCancelable(true);
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }
}
