package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class AttendanceEntity implements Serializable{
	private String emp_code;
	private String attendance_ym;
	private String attendance_date;
	private String going_time;
	private String leaving_time;
	private String break_time;
	private String attendance_div;
	private Timestamp created_time;
	private Timestamp updated_time;
	private String dayoftheweek;
	
	
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public String getAttendance_ym() {
		return attendance_ym;
	}
	public void setAttendance_ym(String attendance_ym) {
		this.attendance_ym = attendance_ym;
	}
	public String getAttendance_date() {
		return attendance_date;
	}
	public void setAttendance_date(String attendance_date) {
		this.attendance_date = attendance_date;
	}
	public String getGoing_time() {
		return going_time;
	}
	public void setGoing_time(String going_time) {
		this.going_time = going_time;
	}
	public String getLeaving_time() {
		return leaving_time;
	}
	public void setLeaving_time(String leaving_time) {
		this.leaving_time = leaving_time;
	}
	public String getBreak_time() {
		return break_time;
	}
	public void setBreak_time(String break_time) {
		this.break_time = break_time;
	}
	public String getAttendance_div() {
		return attendance_div;
	}
	public void setAttendance_div(String attendance_div) {
		this.attendance_div = attendance_div;
	}
	public Timestamp getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Timestamp created_time) {
		this.created_time = created_time;
	}
	public Timestamp getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(Timestamp updated_time) {
		this.updated_time = updated_time;
	}
	public String getDayoftheweek() {
		return dayoftheweek;
	}
	public void setDayoftheweek(String dayoßftheweek) {
		this.dayoftheweek = dayoßftheweek;
	}
	

}
