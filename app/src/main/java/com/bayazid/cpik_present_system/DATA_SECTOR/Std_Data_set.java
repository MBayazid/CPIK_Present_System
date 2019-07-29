package com.bayazid.cpik_present_system.DATA_SECTOR;

public class Std_Data_set {
   private String First_Name,Last_Name,College_Roll,Reg_Number,DbPathId;
   private boolean select;
   private int posi;

  
    public Std_Data_set(String First_Name,String Last_Name,String College_Roll,String Reg_Number,boolean select){
    this.First_Name=First_Name;
    this.Last_Name=Last_Name;
    this.College_Roll=College_Roll;
    this.Reg_Number=Reg_Number;
    this.select=select;

    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
    //DB Path ID
    public Std_Data_set(String DbPathId){
        this.DbPathId=DbPathId;
    }
    public Std_Data_set(int posi){
        this.posi=posi;
    }

    //F Name
    public String getFirst_Name() {
        return First_Name;
    }
    public void setFirst_Name(String First_Name) {
        this.First_Name = First_Name;
    }

    //L Name
    public String getLast_Name() {
        return Last_Name;
    }
    public void setLast_Name(String Last_Name) {
        this.Last_Name = Last_Name;
    }

    //College Rool/ID
    public String getCollege_Roll() {
        return College_Roll;
    }
    public void setCollege_Roll(String College_Roll) {
        this.College_Roll = College_Roll;
    }

    //Registration Number
    public String getReg_Number() {
        return Reg_Number;
    }
    public void setReg_Number(String Reg_Number) {
        this.Reg_Number = Reg_Number;
    }
 //db path id
    public String getDbPathId() {
        return DbPathId;
    }
    public void setDbPathId(String DbPathId) {
        this.DbPathId = DbPathId;
    }

    //Get itemPosition
    public int getposi() {
        return posi;
    }
    public void setposi(int posi) {
        this.posi = posi;
    }
}
