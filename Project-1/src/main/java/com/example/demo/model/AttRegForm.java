package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class AttRegForm {
@NotEmpty(message = "社員コードは必須です")
@Size(min = 5, max = 5, message = "社員コードは５桁で入力してください")
@Pattern(regexp = "^[0-9]*$", message = "社員コードは数値で入力してください")
private String empCode = "";

@NotEmpty(message = "対象年月日は必須です")
@Pattern(regexp = "^[0-9]*$", message = "対象年月日は数値で入力してください")
private String attendanceYmd = "";

@Pattern(regexp = "(^$|.{1,2})", message = "出社時刻（時）は００−２３の間の数値で入力してください")
@Pattern(regexp = "^[0-9]*$", message = "出社時刻（時）は半角数値で入力してください")
private String goingtimeH = "";

@Pattern(regexp = "(^$|.{1,2})", message = "出社時刻（分）は００−５９の間の数値で入力してください")
@Pattern(regexp = "^[0-9]*$", message = "出社時刻（分）は半角数値で入力してください")
private String goingtimeM = "";

@Pattern(regexp = "(^$|.{1,2})", message = "退社時刻（時）は００−２３の間の数値で入力してください")
@Pattern(regexp = "^[0-9]*$", message = "退社時刻（時）は半角数値で入力してください")
private String leavingtimeH = "";

@Pattern(regexp = "(^$|.{1,2})", message = "退社時刻（分）は００−５９の間の数値で入力してください")
@Pattern(regexp = "^[0-9]*$", message = "退社時刻（分）は半角数値で入力してください")
private String leavingtimeM = "";

@Pattern(regexp = "(^$|.{1,2})", message = "休憩時間（時）は００−２３の間の数値で入力してください")
@Pattern(regexp = "^[0-9]*$", message = "休憩時間（時）は半角数値で入力してください")
private String breaktimeH = "";

@Pattern(regexp = "(^$|.{1,2})", message = "休憩時間（分）は００−５９の間の数値で入力してください")
@Pattern(regexp = "^[0-9]*$", message = "休憩時間（分）は半角数値で入力してください")
private String breaktimeM = "";

private String attendanceDiv = "";
private List<DivListBean>divList = new ArrayList<DivListBean>();


public String getAttendanceYmd() {
	return attendanceYmd;
}
public void setAttendanceYmd(String attendanceYmd) {
	this.attendanceYmd = attendanceYmd;
}
public String getEmpCode() {
	return empCode;
}
public void setEmpCode(String empCode) {
	this.empCode = empCode;
}
public String getGoingtimeH() {
	return goingtimeH;
}
public void setGoingtimeH(String goingtimeH) {
	this.goingtimeH = goingtimeH;
}
public String getGoingtimeM() {
	return goingtimeM;
}
public void setGoingtimeM(String goingtimeM) {
	this.goingtimeM = goingtimeM;
}
public String getLeavingtimeH() {
	return leavingtimeH;
}
public void setLeavingtimeH(String leavingtimeH) {
	this.leavingtimeH = leavingtimeH;
}
public String getLeavingtimeM() {
	return leavingtimeM;
}
public void setLeavingtimeM(String leavingtimeM) {
	this.leavingtimeM = leavingtimeM;
}
public String getBreaktimeH() {
	return breaktimeH;
}
public void setBreaktimeH(String breaktimeH) {
	this.breaktimeH = breaktimeH;
}
public String getBreaktimeM() {
	return breaktimeM;
}
public void setBreaktimeM(String breaktimeM) {
	this.breaktimeM = breaktimeM;
}
public String getAttendanceDiv() {
	return attendanceDiv;
}
public void setAttendanceDiv(String attendanceDiv) {
	this.attendanceDiv = attendanceDiv;
}
public List<DivListBean> getDivList() {
	return divList;
}
public void setDivList(List<DivListBean> divList) {
	this.divList = divList;
}

}
