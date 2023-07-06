package com.example.demo.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AttRefForm {
	@NotEmpty(message = "社員コードは必須です")
	@Size(min = 5, max = 5, message = "社員コードは５桁で入力してください")
	@Pattern(regexp = "^[0-9]*$", message = "社員コードは数値で入力してください")
	private String code = "";
	
	@NotEmpty(message = "対象年月は必須です")
	@Size(min = 6, max = 6, message = "対象年月は６桁で入力してください")
	@Pattern(regexp = "^[0-9]*$", message = "対象年月日は数値で入力してください")
	private String ym = "";
	
	private String attendance_date = "";
	private String dayoftheweek = "";
	private String going_time = "";
	private String leaving_time = "";
	private String break_time = "";
	private String attendance_div = "";
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getYm() {
		return ym;
	}
	public void setYm(String ym) {
		this.ym = ym;
	}
	public String getAttendance_date() {
		return attendance_date;
	}
	public void setAttendance_date(String attendance_date) {
		this.attendance_date = attendance_date;
	}
	public String getDayoftheweek() {
		return dayoftheweek;
	}
	public void setDayoftheweek(String dayoftheweek) {
		this.dayoftheweek = dayoftheweek;
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

	
	
}
