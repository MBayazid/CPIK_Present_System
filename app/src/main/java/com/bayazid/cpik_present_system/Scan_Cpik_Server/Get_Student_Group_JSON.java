package com.bayazid.cpik_present_system.Scan_Cpik_Server;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bayazid.cpik_present_system.DATA_SECTOR.StudentBean;
import com.bayazid.cpik_present_system.Network_Cheaker.MyApplicationController;
import com.bayazid.cpik_present_system.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Get_Student_Group_JSON extends AppCompatActivity {
    StudentBean sb =new StudentBean();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String TAG = "Get_Student_Group_JSON";
    private boolean intentData;
    String Department,Semester,TechnologyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__student__group__json);
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("department")!= null&&bundle.getString("semester")!= null&&bundle.getString("TechnologyName")!= null)
        {    //set bollen true
            intentData =true;
            Department=bundle.getString("department");
            Semester=bundle.getString("semester");
            TechnologyName=bundle.getString("TechnologyName");
        }
        else {intentData=false;}
        if (intentData==true){
            startScanningDB();
        }
    }

    private void startScanningDB() {
        //url
        String URL= getText(R.string.Root_URL).toString();
        final String url = URL+"get_selected_Studentsl_info.php?SEM="+Semester;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("Students Group");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                sb.setClassRoll(jsonObject.optString("ClassRoll").toString());
                                sb.setBoardRoll(jsonObject.optString("BoardRoll").toString());
                                sb.setRegistration(jsonObject.optString("Registration").toString());
                                sb.setSemester(jsonObject.optString("Semester").toString());
                                sb.setStudentName(jsonObject.optString("StudentName").toString());

                                startAddingStudentsGroup(sb.getClassRoll(),sb.getBoardRoll(),
                                        sb.getRegistration(),sb.getSemester(),sb.getStudentName());


                            }
                            Toast.makeText(Get_Student_Group_JSON.this,jsonArray.length()+ " Students Have been Checked..",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplication(),"Your Network Connection Problem", Toast.LENGTH_LONG).show();
                    finish();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplication(),"Failure to Connection Server", Toast.LENGTH_LONG).show();
                    finish();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplication(),"Server Problem", Toast.LENGTH_LONG).show();
                    finish();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplication(),"Network Problem", Toast.LENGTH_LONG).show();
                    finish();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplication(),"Parse Problem", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                //  params.put("Authorization", "Bearer "+access_token);
                return params;
            }


        };

        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );

        MyApplicationController.getPermission().addToRequestQueue(req);
        //set data s
    }

    private void startAddingStudentsGroup(String ClassRoll,String BoardRoll, String Registration, String Semester,String StudentName) {

        // startActivity(new Intent(Teacher_Class_type.this,Students_List.class));
        //  Teacher_Class_type.this.finish();

        // Create a new user with a first and last name
        Map<String, Object> students_detail = new HashMap<>();
        students_detail.put("ClassRoll", ClassRoll);
        students_detail.put("BoardRoll", BoardRoll);
        students_detail.put("Registration", Registration);
        students_detail.put("Semester", Semester);
        students_detail.put("StudentName", StudentName);


        // Add a new document with a generated ID
        db.collection("students_collection").document(Department).collection(TechnologyName)
                .document(ClassRoll).set(students_detail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       // Toast.makeText(Get_Student_Group_JSON.this, StudentName, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Get_Student_Group_JSON.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               // startActivity(new Intent(Get_Student_Group_JSON.this, Teacher_Class_type.class));
               finish();
            }
        });
    }


}
