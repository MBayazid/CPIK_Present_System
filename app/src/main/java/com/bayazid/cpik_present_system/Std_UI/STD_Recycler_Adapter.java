package com.bayazid.cpik_present_system.Std_UI;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bayazid.cpik_present_system.DATA_SECTOR.Std_Data_set;
import com.bayazid.cpik_present_system.R;
import com.bayazid.librarycpik.ToggleSwitchView.SwitchButton;

import java.util.List;


public class STD_Recycler_Adapter extends RecyclerView.Adapter<STD_Recycler_Adapter.MyViewHolder> {

    private List<Std_Data_set> std_data_sets;

    public   Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView STD_Board_Roll,full_name, STD_Roll, STD_Reg_Num;
        public SwitchButton switchButton;



        public MyViewHolder(View view) {
            super(view);
            full_name = (TextView) view.findViewById(R.id.std_full_name);
            STD_Roll = (TextView) view.findViewById(R.id.std_roll);
            STD_Board_Roll = (TextView) view.findViewById(R.id.b_roll);
            STD_Reg_Num = (TextView) view.findViewById(R.id.std_reg_number);
            switchButton =(SwitchButton)view.findViewById(R.id.togglebtn_std_attendene);
           // this.setIsRecyclable(false);
        }
    }


    public STD_Recycler_Adapter(List<Std_Data_set> std_data_sets) {
        this.std_data_sets = std_data_sets;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Std_Data_set stdDataSet = std_data_sets.get(position);


        holder.full_name.setText(stdDataSet.getFirst_Name());
        holder.STD_Roll.setText(stdDataSet.getCollege_Roll());
        holder.STD_Board_Roll.setText(stdDataSet.getLast_Name());
        holder.STD_Reg_Num.setText(stdDataSet.getReg_Number());

       // holder.switchButton.setOnCheckedChangeListener(null);
        holder.switchButton.setChecked(stdDataSet.isSelect());
        holder.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
//                std_data_sets.get(position).setSelect(isChecked);
//                stdDataSet.setSelect(isChecked);


            }
        });



    }

    @Override
    public int getItemCount() {
        return std_data_sets.size();
    }


}
//        ==========================================================================================
//        SwitchButton
//        ==========================================================================================
//        holder.sbSelect.setOnCheckedChangeListener(null);//清掉监听器
//                holder.sbSelect.setChecked(student.isSelect());
//                holder.sbSelect.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
//@Override
//public void onCheckedChanged(SwitchButton view, boolean isChecked) {
//        Log.e(TAG, "Name: " + student.getName() + "   position: " +
//        position + "   isChecked: " + isChecked);
//        studentList.get(position).setSelect(isChecked);
//        }
//        });

//==================================================================================================
//       SwitchButton2
//==================================================================================================
//        holder.sb2Select.setOnCheckedChangeListener(null);//清掉监听器
//        holder.sb2Select.setChecked(student.isSelect());
//        holder.sb2Select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.e(TAG, "Name: " + student.getName() + "   position: " +
//                        position + "   isChecked: " + isChecked);
//                studentList.get(position).setSelect(isChecked);
//            }
//        });


//        ==========================================================================================
//        ToggleButton
//        ==========================================================================================
//        holder.btnToggle.setOnCheckedChangeListener(null);//清掉监听器
//        holder.btnToggle.setChecked(student.isSelect());
//        holder.btnToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.e(TAG, "Name: " + student.getName() + "   position: " +
//                        position + "   isChecked: " + isChecked);
//                studentList.get(position).setSelect(isChecked);
//            }
//        });


//        ==========================================================================================
//        MyToggleButton
//        ==========================================================================================
//        holder.myToggleButton.setOnCheckedChangeListener(null);//清掉监听器
//        holder.myToggleButton.setChecked(student.isSelect());
//        holder.myToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.e(TAG, "Name: " + student.getName() + "   position: " +
//                        position + "   isChecked: " + isChecked);
//                studentList.get(position).setSelect(isChecked);
//            }
//        });