package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.AttRefForm;
import com.example.demo.model.AttRegForm;
import com.example.demo.model.DivListBean;
import com.example.demo.model.EmpRefForm;


@Controller
public class TestController {
	
	@RequestMapping("/")
	public String login(Model model) {
		return "index";
	}
	
	@RequestMapping(value="/menu", params="login")
	public String menu(Model model) {
		return "menu/menu";
	}
	
	@RequestMapping(value="/empRef", params="empRef")
	public String empRef(Model model) {
		EmpRefForm erf = new EmpRefForm();
		erf.setCode("");
		erf.setName("");
		model.addAttribute("form", erf);
		return "empRef/empRef";
	}
	
	@RequestMapping(value="/attRef", params="attRef")
	public String attRef(Model model) {
		AttRefForm arf = new AttRefForm();
		arf.setCode("");
		arf.setYm("");
		model.addAttribute("form", arf);
		return "attRef/attRef";
	}
	
	@RequestMapping(value="/attReg", params="attReg")
	public String attReg(Model model) {
		AttRegForm arg = new AttRegForm();
		arg.setEmpCode("");
		arg.setAttendanceYmd("");
		arg.setGoingtimeH("");
		arg.setGoingtimeM("");
		arg.setLeavingtimeH("");
		arg.setLeavingtimeM("");
		arg.setBreaktimeH("");
		arg.setBreaktimeM("");
		arg.setAttendanceDiv("");
		
		List<DivListBean>divList = new ArrayList<DivListBean>();
		DivListBean listBean = new DivListBean();
		listBean.setDivCode("01");
		listBean.setDivName("通常");
		divList.add(listBean);
		
		listBean = new DivListBean();
		listBean.setDivCode("02");
		listBean.setDivName("有給");
		divList.add(listBean);
		
		listBean = new DivListBean();
		listBean.setDivCode("03");
		listBean.setDivName("午前休");
		divList.add(listBean);
		
		listBean = new DivListBean();
		listBean.setDivCode("04");
		listBean.setDivName("午後休");
		divList.add(listBean);
		
		listBean = new DivListBean();
		listBean.setDivCode("05");
		listBean.setDivName("シフト");
		divList.add(listBean);
		
		listBean = new DivListBean();
		listBean.setDivCode("06");
		listBean.setDivName("欠勤");
		divList.add(listBean);
		
		listBean = new DivListBean();
		listBean.setDivCode("07");
		listBean.setDivName("特別");
		divList.add(listBean);
		
		listBean = new DivListBean();
		listBean.setDivCode("99");
		listBean.setDivName("その他");
		divList.add(listBean);
		
		arg.setDivList(divList);
		
		model.addAttribute("form", arg);
		model.addAttribute("selectDiv", "01");
		
		return "attReg/attReg";
	}	
	
}
	