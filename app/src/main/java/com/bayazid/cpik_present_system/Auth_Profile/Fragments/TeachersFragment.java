package com.bayazid.cpik_present_system.Auth_Profile.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bayazid.cpik_present_system.R;
import com.bayazid.cpik_present_system.Teachears_Function.Attendance_Book_Main;
import com.bayazid.cpik_present_system.Teachears_Function.Teacher_Class_type;
import com.bayazid.librarycpik.RippleEffect.RippleView;

public class TeachersFragment extends Fragment {
    private Button SignOut, View_Schduled_Attendance_Date, Take_Attendance, View_Attendance_Book, EditAttendance, Single_STD_Qurey;
    private RippleView rippleViewTake_Attendance, rippleViewView_Attendance_Book,rippleViewSignOut;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teahers_panel, container, false);


        Take_Attendance = view.findViewById(R.id.take_attendance_button);
        View_Attendance_Book = view.findViewById(R.id.view_attendance_book_button3);
        EditAttendance = view.findViewById(R.id.edit_already_taken_btn);
        Single_STD_Qurey = view.findViewById(R.id.single_std_query__button4);
        SignOut = view.findViewById(R.id.sign_out_button5);
        SignOut.setVisibility(View.GONE);

        rippleViewTake_Attendance = view.findViewById(R.id.rtake_attendance_button);
        rippleViewView_Attendance_Book = view.findViewById(R.id.rview_attendance_book_button3);
        rippleViewSignOut = view.findViewById(R.id.rsign_out_button5);


        rippleViewTake_Attendance.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(getContext(), Teacher_Class_type.class));
            }
        });
        rippleViewView_Attendance_Book.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(getContext(), Attendance_Book_Main.class));
            }
        });



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
