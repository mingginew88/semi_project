package patient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
		//아이디 
		//영문자 총4~12자리
//		public boolean regId(String memId){
//			Pattern p = Pattern.compile("^[a-zA-Z0-9]{4,12}$");
//			Matcher m = p.matcher(memId);
//			
//			if(m.find()){
//				return true;
//			}else{
//				return false;
//			}
//		}
		
		
		//비밀번호
		//첫글자는 영문자 총4~12자리
//		public boolean regPw(String passwoard){
//			Pattern p = Pattern.compile("^[a-zA-Z]{4,12}$");
//			Matcher m = p.matcher(passwoard);
//			
//			if(m.find()){
//				return true;
//			}else{
//				return false;
//			}
//		}
		
		
		//이름(한글영어 혼용 불가)
		//한글일 경우 2-6자
		//영어일 경우 첫글자 대문자 최소3~16자
		public boolean regName(String memName){
			Pattern p = Pattern.compile("(^[가-힣]{2,6}$)|(^[A-Z]{1}[a-zA-Z]{2,15}$)");
			Matcher m = p.matcher(memName);
			
			if (m.find()) {
				return true;
			} else {
				return false;
			}		
		}
		
		
		//전화번호
		//형태 000-0000-0000
		//첫 두숫자는 01 세번째는 16789 5번째는 0제외
		public boolean regTel(String phoneNumber){
			Pattern p = Pattern.compile("^01[^2-5]-[1-9][0-9]{3}-[0-9]{4}$");
			Matcher m = p.matcher(phoneNumber);
			
			if (m.find()) {
				return true;
			} else {
				return false;
			}	
		}
		
		
		//주민번호1
		//형태 000000
		//숫자로 구성  
		public boolean reg_RegNo1(String regNo1){
			Pattern p = Pattern.compile("^[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])$");
			Matcher m = p.matcher(regNo1);
			
			if (m.find()) {
				return true;
			} else {
				return false;
			}	
		}
		
		//주민번호2
		//형태 0000000
		//숫자로 구성  
		public boolean reg_RegNo2(String regNo2){
			Pattern p = Pattern.compile("^[1-4]{1}[0-9]{6}$");
			Matcher m = p.matcher(regNo2);
			
			if (m.find()) {
				return true;
			} else {
				return false;
			}	
		}
		
		//이메일
		//형태 
		public boolean reg_email(String regEmail) {
			Pattern p = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+$");
			Matcher m = p.matcher(regEmail);
			
			if(m.find()) {
				return true;
			} else {
				return false;
			}
		}
		
		
		//___________________________________________________________________
		
		//의사(일정 명)
		public boolean sche_name(String sche_name) {
			Pattern p = Pattern.compile("^[a-zA-Zㄱ-힣0-9]{1,20}$");
			Matcher m = p.matcher(sche_name);
			
			if(m.find()) {
				return true;
			} else {
				return false;
			}
		}
		
		
//		//의사(일정 일)
//		public boolean daysFromStart(String daysFromStart) {
//			Pattern p = Pattern.compile("^(18|19)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$");
//			Matcher m = p.matcher(daysFromStart);
//			
//			if(m.find()) {
//				return true;
//			} else {
//				return false;
//			}
//		}
		
		
		//의사 (일정 내용)
		public boolean sche_cont(String sche_cont) {
			Pattern p = Pattern.compile("^[a-zA-Zㄱ-힣0-9]{1,65}$");
			Matcher m = p.matcher(sche_cont);
			
			if(m.find()) {
				return true;
			} else {
				return false;
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

}
