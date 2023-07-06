package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.entity.MsEmployee;
import com.example.demo.model.AttRefForm;
import com.example.demo.model.EmpRefForm;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.MsEmployeeRepository;

@Controller
public class SearchController {
	
		@Autowired
		private MsEmployeeRepository msEmployeeRepository;
		
		@Autowired
		private AttendanceRepository attendanceRepository;
	
		@Autowired
		MessageSource messagesource;
	
	@RequestMapping(value="/empRefSearch", params="search")
	public String goEmpRef(Model model,@Validated @ModelAttribute("form") EmpRefForm erf, BindingResult bindingResult) {
		
		//社員コード検索
		String paramCode = erf.getCode();
		MsEmployee me = msEmployeeRepository.findById(paramCode);
		
		//社員コードが見つからない場合、データは空欄にしエラーメッセージを表示
		if(me == null) {
			model.addAttribute("name", "");
			model.addAttribute("age", "");
			model.addAttribute("address", "");
			model.addAttribute("post", "");
			model.addAttribute("msg", messagesource.getMessage("nocode.msg", new String[] {}, Locale.getDefault()));
			model.addAttribute("form", erf);
			return "empRef/empRef";
		}else{
			model.addAttribute("name", me.getEmp_name());
			model.addAttribute("age", me.getEmp_age());
			model.addAttribute("address", me.getEmp_address());
			model.addAttribute("form", me.getEmp_post());
			}
		
		//役職
		switch(me.getEmp_post()) {
		case "01":
			model.addAttribute("post", "社長");
			break;
		case "02":
			model.addAttribute("post", "専務");
			break;
		case "03":
			model.addAttribute("post", "常務");
			break;
		case "04":
			model.addAttribute("post", "部長");
			break;
		case "05":
			model.addAttribute("post", "次長");
			break;
		case "06":
			model.addAttribute("post", "課長");
			break;
		case "07":
			model.addAttribute("post", "係長");
			break;
		case "08":
			model.addAttribute("post", "主任");
			break;
		case "09":
			model.addAttribute("post", "一般");
			break;
		default:
			model.addAttribute("post", "その他");
		}
		model.addAttribute("msg", messagesource.getMessage("ok.msg", new String[] {}, Locale.getDefault()));
		model.addAttribute("form", erf);
		return "empRef/empRef";
	}
	@RequestMapping(value="/attRefSearch", params="search")
	public String goAttRef(Model model,@Validated @ModelAttribute("form") AttRefForm arf, BindingResult bindingResult) {
		
		//バリデード結果判定文
		if(bindingResult.hasErrors()) {
			model.addAttribute("form", arf);
			return "attRef/attRef";
		}
		
		String paramCode = arf.getCode();
		String paramYm = arf.getYm();
	
		//データベースの取得
		List<AttendanceEntity>attendanceList = attendanceRepository.findByYm(paramCode,paramYm);
		
		//取得結果の有無判定
		if(attendanceList == null || attendanceList.isEmpty()) {
			model.addAttribute("msg", messagesource.getMessage("nodata.msg", new String[] {}, Locale.getDefault()));
			return "attRef/attRef";
		}else{
			for(int i = 0; i < attendanceList.size(); i++) {
				AttendanceEntity ae = attendanceList.get(i);
				//曜日・区分の変換を繰り返し処理の中で実施
				ae.setDayoftheweek(getDayofweek(ae.getAttendance_ym(), ae.getAttendance_date()));
				ae.setAttendance_div(getAttendanceDiv(ae.getAttendance_div()));
		
				//日付チェック文
				String strDate = ae.getAttendance_ym() + ae.getAttendance_date();
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
				try {
					Date date = sdFormat.parse(strDate);
					SimpleDateFormat a1 = new SimpleDateFormat("yyyyMMdd");
					String c1 = a1.format(date);
					ae.setAttendance_date(c1);
				}catch(ParseException e) {
					ae.setAttendance_date("");
				}	
			
			ae.setAttendance_date(ae.getAttendance_ym().substring(0,4) + "/" + ae.getAttendance_ym()
				.substring(4,6) + "/" + ae.getAttendance_date().substring(6,8));
				
			if(ae.getGoing_time() != null && ae.getGoing_time().length() >= 4) {
				ae.setGoing_time(ae.getGoing_time().substring(0,2) + ":" + ae.getGoing_time().substring(2,4));
			}
			
			if(ae.getLeaving_time() != null && ae.getLeaving_time().length() >= 4) {
				ae.setLeaving_time(ae.getLeaving_time().substring(0,2) + ":" + ae.getLeaving_time().substring(2,4));
			}
			
			ae.setAttendance_div(ae.getAttendance_div());
			}
			
		model.addAttribute("msg", messagesource.getMessage("ok.msg", new String[] {}, Locale.getDefault()));
		
		}
		
		//実行年月日時分秒表示
		Date d = new Date();
		SimpleDateFormat d1 = new SimpleDateFormat("yyyy/MM/dd HH.mm.ss");
		String c1 = d1.format(d);
		model.addAttribute("execTime", "実行年月日時分秒：" + c1);
		model.addAttribute("form", arf);
		model.addAttribute("attRefList", attendanceList);
		
		return "attRef/attRef";
}
	
	//曜日
	public String getDayofweek(String attendanceYm, String attendanceDate) {
		String ret = "";
		Calendar cal = Calendar.getInstance();
		int year = Integer.valueOf(attendanceYm.substring(0, 4));
		int month = Integer.valueOf(attendanceYm.substring(4, 6)) - 1;
		int date = Integer.valueOf(attendanceDate);
		cal.set(year,month,date);
		
		switch(cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY:
			ret = "日曜";
			break;
			
		case Calendar.MONDAY:
			ret = "月曜";
			break;
			
		case Calendar.TUESDAY:
			ret = "火曜";
			break;
			
		case Calendar.WEDNESDAY:
			ret = "水曜";
			break;
			
		case Calendar.THURSDAY:
			ret = "木曜";
			break;
			
		case Calendar.FRIDAY:
			ret = "金曜";
			break;
			
		case Calendar.SATURDAY:
			ret = "土曜";
			break;
		}
		return ret;
	}
	
	//区分
	public String getAttendanceDiv(String attendanceDiv) {
		String ret = "";
		switch(attendanceDiv) {
		case "01":
			ret = "通常";
			break;
			
		case "02":
			ret = "有給";
			break;
			
		case "03":
			ret = "午前休";
			break;
			
		case "04":
			ret = "午後休";
			break;
			
		case "05":
			ret = "通常";
			break;
			
		case "06":
			ret = "シフト";
			break;
			
		case "07":
			ret = "欠勤";
			break;
			
		case "99":
			ret = "その他";
			break;
		}
		return ret;
	}
	
}