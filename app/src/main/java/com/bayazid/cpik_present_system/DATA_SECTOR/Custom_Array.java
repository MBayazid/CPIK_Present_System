package com.bayazid.cpik_present_system.DATA_SECTOR;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bayazid.cpik_present_system.R;

import java.util.ArrayList;
import java.util.List;

    public class Custom_Array extends ArrayAdapter<Std_Data_set> {
    private Context mContext;
    private List<Std_Data_set> std_list = new ArrayList<>();

     public Custom_Array(@NonNull Context context, ArrayList<Std_Data_set> list) {
        super(context, 0 , list);
        mContext = context;
        std_list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.model_for_attendance,parent,false);

         final Std_Data_set c_std_data_set = std_list.get(position);

        TextView STD_Name= (TextView) listItem.findViewById(R.id.std_full_name);
      //  String full_name=c_std_data_set.getFirst_Name()+" "+c_std_data_set.getLast_Name();
//        STD_Name.setText(c_std_data_set.getFirst_Name());
        STD_Name.setText(c_std_data_set.getFirst_Name());

//        TextView STD_Roll= (TextView) listItem.findViewById(R.id.std_roll);
//        STD_Roll.setText(c_std_data_set.getCollege_Roll());
//
//        TextView STD_Reg_Num= (TextView) listItem.findViewById(R.id.std_reg_number);
//        STD_Reg_Num.setText(c_std_data_set.getReg_Number());
//
       //ONITECLICK
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,c_std_data_set.getFirst_Name(),Toast.LENGTH_SHORT).show();








            }
        });

        return listItem;
    }



}
