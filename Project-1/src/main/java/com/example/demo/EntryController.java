package com.example.demo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.example.demo.model.AttRegForm;
import com.example.demo.model.DivListBean;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.MsEmployeeRepository;

@Controller
public class EntryController {
		
		@Autowired
		private AttendanceRepository attendanceRepository;
		
		@Autowired
		private MsEmployeeRepository msEmployeeRepository;
		
		//メッセージ管理
		@Autowired
		MessageSource messagesource;
		
	@RequestMapping(value="/attRegEntry", params="entry")
	public String goAttReg(Model model,@Validated @ModelAttribute("form") AttRegForm arg, BindingResult bindingResult) {
			
			//区分プルダウン再設定
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
			
			//区分プルダウン選択値設定
			model.addAttribute("selectedDiv", arg.getAttendanceDiv());
			
			//時刻先頭0埋め処理
			arg.setGoingtimeH(padZeroForTime(arg.getGoingtimeH()));
			arg.setGoingtimeM(padZeroForTime(arg.getGoingtimeM()));
			arg.setLeavingtimeH(padZeroForTime(arg.getLeavingtimeH()));
			arg.setLeavingtimeM(padZeroForTime(arg.getLeavingtimeM()));
			arg.setBreaktimeH(padZeroForTime(arg.getBreaktimeH()));
			arg.setBreaktimeM(padZeroForTime(arg.getBreaktimeM()));
			
			//フォーム側バリデータ結果確認
			if(bindingResult.hasErrors()) {
				model.addAttribute("form", arg);
				return "attReg/attReg";
			}
			
			//1.日付チェック
			Boolean gt = ((arg.getGoingtimeH() != null && !arg.getGoingtimeH().isEmpty()) || (arg.getGoingtimeM() != null && !arg.getGoingtimeM().isEmpty()));
			Boolean lt = ((arg.getLeavingtimeH() != null && !arg.getLeavingtimeH().isEmpty()) || (arg.getLeavingtimeM() != null && !arg.getLeavingtimeM().isEmpty()));
			Boolean bt = ((arg.getBreaktimeH() != null && !arg.getBreaktimeH().isEmpty()) || (arg.getBreaktimeM() != null && !arg.getBreaktimeM().isEmpty()));
			
			String strDate = arg.getAttendanceYmd();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
			sdFormat.setLenient(false);
			try {
				//日付変換
				Date date = sdFormat.parse(strDate);
				strDate = sdFormat.format(date);
			}catch(ParseException e) {
				//例外発生時は暦上外日付扱い
				model.addAttribute("msg", messagesource.getMessage("date.msg", new String[] {}, Locale.getDefault()));
				return "attReg/attReg";
			}	
		
			//2.時刻チェック
			sdFormat = new SimpleDateFormat("yyyyMMddHHmm");
			sdFormat.setLenient(false);
			//出社時刻
			String strGoingDate = arg.getAttendanceYmd() + arg.getGoingtimeH() + arg.getGoingtimeM();
			Date goingDate = new Date();
			if(gt){
				try {
					//日付変換
					goingDate = sdFormat.parse(strGoingDate);
					strGoingDate = sdFormat.format(goingDate);
				}catch(ParseException e) {
					//例外発生時は暦上外日付扱い
					model.addAttribute("msg", messagesource.getMessage("goingTimeErr.msg", new String[] {}, Locale.getDefault()));
					return "attReg/attReg";
				}
			}
			
			//退社時刻
			String strLeavingDate = arg.getAttendanceYmd() + arg.getLeavingtimeH() + arg.getLeavingtimeM();
			Date leavingDate = new Date();
			if(lt){
				try {
					//日付変換
					leavingDate = sdFormat.parse(strLeavingDate);
					strLeavingDate = sdFormat.format(leavingDate);
				}catch(ParseException e) {
					model.addAttribute("msg", messagesource.getMessage("leavingTimeErr.msg", new String[] {}, Locale.getDefault()));
					return "attReg/attReg";
				}
			}
			
			//休憩時間
			String strBreakDate = arg.getAttendanceYmd() + arg.getBreaktimeH() + arg.getBreaktimeM();
			if(bt){
				try {
					//日付変換
					Date date = sdFormat.parse(strBreakDate);
					strDate = sdFormat.format(date);
				}catch(ParseException e) {
					model.addAttribute("msg", messagesource.getMessage("breakTimeErr.msg", new String[] {}, Locale.getDefault()));
					return "attReg/attReg";
				}
			}    
			
			//3.「出社時刻」＜「退社時刻」チェック
			if(gt && lt){
				if(strGoingDate.compareTo(strLeavingDate) > -1) {
					//「出社時刻の時」＋「出社時刻の分」＞「退社時刻の時」＋「退社時刻の分」の場合エラー
					model.addAttribute("msg", messagesource.getMessage("comp.msg", new String[] {}, Locale.getDefault()));
					return "attReg/attReg";
				}
			}
			
			//4.「休憩時間」≧「退社時刻 - 出社時刻」チェック
			if(bt) {
				long tmpTime = leavingDate.getTime() - goingDate.getTime();
				//「休憩時間(時)」をミリ秒で算出する ※「時」なので60倍して「分」にし、さらに60倍して「秒」にし、さらに10倍して「ミリ秒」にする
				long tmpH = Long.valueOf(arg.getBreaktimeH()) * 60 * 60 * 1000;
				//「休憩時間(分)」をミリ秒で算出する ※「分」なので60倍して「秒」にし、さらに10倍して「ミリ秒」にする
				long tmpM = Long.valueOf(arg.getBreaktimeM()) * 60 * 1000;
				//比較
				if(tmpTime <= (tmpH + tmpM)) {
					model.addAttribute("msg", messagesource.getMessage("breakTimeOver.msg", new String[] {}, Locale.getDefault()));
					return "attReg/attReg"; 
				}
			}
			
			//5.区分が「午前休」の場合の「出社時刻チェック」
			if("03".equals(arg.getAttendanceDiv())) {
				//出社時刻のミリ秒変換
				long tmpGoingTime = goingDate.getTime();
				long tmpTime = 0;
				try {
					//出社年月日＋「1400」で当日14時時点のミリ秒経過時間を算出
					Date tmpDate = sdFormat.parse(arg.getAttendanceYmd() + "1400");
					tmpTime = tmpDate.getTime();
				}catch(ParseException e){
					//起こり得ないエラー
					return "attReg/attReg";  
				}
				//比較
				if(tmpGoingTime < tmpTime) {
				model.addAttribute("msg", messagesource.getMessage("am.msg", new String[] {}, Locale.getDefault()));
					return "attReg/attReg";
				}
			}
			
			//6.区分が「午後休」の場合の「退社時刻チェック」
			if("04".equals(arg.getAttendanceDiv())) {
				//退社時刻のミリ秒変換
				long tmpLeavingTime = leavingDate.getTime();
				long tmpTime = 0;
				try {
					//退社年月日＋「1400」で当日14時時点のミリ秒経過時間を算出
					Date tmpDate = sdFormat.parse(arg.getAttendanceYmd() + "1400");
					tmpTime = tmpDate.getTime();
				}catch(ParseException e){
					//起こり得ないエラー
					return "attReg/attReg";  
				}
				//比較
				if(tmpLeavingTime < tmpTime) {
				model.addAttribute("msg", messagesource.getMessage("pm.msg", new String[] {}, Locale.getDefault()));
					return "attReg/attReg";
				}
			}
			
			//7.区分が（有給、欠勤、特別休暇）のいずれかの場合での各時間のチェック
			if(("02".equals(arg.getAttendanceDiv())) || ("06".equals(arg.getAttendanceDiv())) || ("07".equals(arg.getAttendanceDiv()))){
			if(gt && lt && bt) {
				model.addAttribute("msg", messagesource.getMessage("set.msg", new String[] {}, Locale.getDefault()));
				}
			}
			
			//社員コード存在チェック
			MsEmployee msEmployee = msEmployeeRepository.findById(arg.getEmpCode());
			if(msEmployee == null){
				//社員コード存在なし
				model.addAttribute("msg", messagesource.getMessage("nocode.msg", new String[] {}, Locale.getDefault()));
				return "attReg/attReg";
			}
			
			//勤怠情報存在チェック
			String strYm = arg.getAttendanceYmd().substring(0,6);
			String strDay = arg.getAttendanceYmd().substring(6,8);
			AttendanceEntity attendanceEntity = attendanceRepository.findByYmd(arg.getEmpCode(),strYm,strDay);
			if(attendanceEntity != null) {
				//勤怠情報存在あり
				model.addAttribute("msg", messagesource.getMessage("exist.msg", new String[] {}, Locale.getDefault()));
				return "attReg/attReg";
			}
			
			//勤怠情報登録処理
			//登録用Entity設定
			AttendanceEntity ent = new AttendanceEntity();
			//登録日・更新日用Timestamp作成
			long millis = System.currentTimeMillis();
			//ミリ秒を引数としてTimestampオブジェクトを作成
			Timestamp timestamp = new Timestamp(millis);
			
			//社員コード
			ent.setEmp_code(arg.getEmpCode());
			//勤務年月
			ent.setAttendance_ym(strYm);
			//勤務日
			ent.setAttendance_date(strDay);
			//出社時刻
			ent.setGoing_time(arg.getGoingtimeH() + arg.getGoingtimeM());
			//退社時刻
			ent.setLeaving_time(arg.getLeavingtimeH() + arg.getLeavingtimeM());
			//休憩時間
			ent.setBreak_time(arg.getBreaktimeH() + arg.getBreaktimeM());
			//区分
			ent.setAttendance_div(arg.getAttendanceDiv());
			//登録日
			ent.setCreated_time(timestamp);
			//更新日
			ent.setUpdated_time(timestamp);
			
			//登録実行
			attendanceRepository.entry(ent);
			model.addAttribute("msg", messagesource.getMessage("perf.msg", new String[] {}, Locale.getDefault()));		
			return "attReg/attReg";
	}
		
			//padZeroForTimeメソッド　引数が一桁の時に先頭に「0」を付加する
			public String padZeroForTime(String target) {
			String retTarget = "";
			if(target != null && !target.isEmpty()) {
			if(target.length() == 1) {
				retTarget = "0" + target;
			}else {
				retTarget = target;
			}
			
			}
				return retTarget;
		}
			
}
			
