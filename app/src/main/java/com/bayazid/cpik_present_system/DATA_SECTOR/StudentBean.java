package com.bayazid.cpik_present_system.DATA_SECTOR;

public class StudentBean {
	/*
   			 "AdmissionDate": null,
			"InstituteCode": "35047",
			"CurriculumCode": "15",
			"TechnologyCode": "666",
			"Probidhan": "2016",
			"ClassRoll": "17199U",
			"BoardRoll": "958446",
			"Registration": "836287",
			"Session": "2017-18",
			"Semester": "4",
			"StudentName": "MD.IQRAMULISLAM RABBY"
			*/

	private String AdmissionDate;
	private String InstituteCode;
	private String CurriculumCode;
	private String TechnologyCode;
	private String Probidhan;
	private String ClassRoll;
	private String BoardRoll;
	private String Registration;
	private String Session;
	private String Semester;
	private String StudentName;

	public String getAdmissionDate() {
		return AdmissionDate;
	}
	public void setAdmissionDate(String AdmissionDate) {
		this.AdmissionDate = AdmissionDate;
	}

	public String getInstituteCode() {
		return InstituteCode;
	}
	public void setInstituteCode(String InstituteCode) {
		this.InstituteCode = InstituteCode;
	}

	public String getCurriculumCode() {
		return CurriculumCode;
	}
	public void setCurriculumCode(String curriculumCode) {
		this.CurriculumCode = curriculumCode;
	}

	public String getTechnologyCode() {
		return TechnologyCode;
	}
	public void setTechnologyCode(String technologyCode) {
		this.TechnologyCode = technologyCode;
	}

	public String getProbidhan() {
		return Probidhan;
	}
	public void setProbidhan(String probidhan) {
		this.Probidhan = probidhan;
	}

	public void setRegistration(String Registration) {
		this.Registration = Registration;
	}
	public String getRegistration() {
		return Registration;
	}

	public void setSession(String Session) {
		this.Session = Session;
	}
	public String getSession() {
		return Session;
	}

	public void setSemester(String Semester) {
		this.Semester = Semester;
	}
	public String getSemester() {
		return Semester;
	}

	public void setStudentName(String StudentName) {
		this.StudentName = StudentName;
	}
	public String getStudentName() {
		return StudentName;
	}


	public String getClassRoll() {
		return ClassRoll;
	}
	public void setClassRoll(String ClassRoll) {
		this.ClassRoll = ClassRoll;
	}


	public String getBoardRoll() {
		return BoardRoll;
	}
	public void setBoardRoll(String boardRoll) {
		this.BoardRoll = boardRoll;
	}



}
