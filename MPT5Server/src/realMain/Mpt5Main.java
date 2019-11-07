package realMain;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import admin.AdminMainService;
import admin.IAdminMainService;
import admin.adminDB.AdminDBService;
import admin.adminDB.IAdminDBService;
import admin.memList.IMemListService;
import admin.memList.MemListService;
import appointment.AppointmentService;
import appointment.IAppointmentService;
import board.notice.NoticeService;
import board.notice.NoticeServiceInf;
import board.qna.QnAService;
import board.qna.QnAServiceInf;
import disease.DiseaseService;
import disease.IDiseaseService;
import doctor.DoctorMainService;
import doctor.IDoctorMainService;
import doctorVisitClinic.DoctorVisitClinicService;
import doctorVisitClinic.IDoctorVisitClinicService;
import examination.ExaminationService;
import examination.IExaminationService;
import login.ILoginService;
import login.LoginService;
import msg.IMsgService;
import msg.MsgService;
import patient.IPatientInfoService;
import patient.PatientInfoService;
import patientAppointment.IPatientAppointmentService;
import patientAppointment.PatientAppointmentService;
import schedule.IScheduleService;
import schedule.ScheduleService;
import searchData.ISearchDataService;
import searchData.SearchDataService;
import survey.ISurveyService;
import survey.SurveyService;

public class Mpt5Main {
	public static void main(String[] args) {
		
		try {
			ILoginService loginService = new LoginService();
			IMemListService memService = new MemListService();
			NoticeServiceInf noticeService = new NoticeService();
			QnAServiceInf qnaService = new QnAService();
			IMsgService msgService = new MsgService();
			ISearchDataService searchDataService = new SearchDataService();
			IPatientInfoService paService = new PatientInfoService();
			IDoctorVisitClinicService doctorVisitClinicService = new DoctorVisitClinicService();
			IScheduleService scheduleService = new ScheduleService();
			IAdminDBService adminDBservice = new AdminDBService();
			IDoctorMainService doctorMainService = new DoctorMainService();
			IAppointmentService appointService = new AppointmentService();
			IAdminMainService adminMainService = new AdminMainService();
			IExaminationService examinationService = new ExaminationService();
			IDiseaseService diseaseService = new DiseaseService();
			ISurveyService surveyService = new SurveyService();
			IPatientAppointmentService patientAppointmentService = new PatientAppointmentService();

			
			Registry reg = LocateRegistry.createRegistry(9988);
			
			reg.rebind("appointment",appointService);
			reg.rebind("patient", paService);
			reg.rebind("Login", loginService);
			reg.rebind("memberList", memService);
			reg.rebind("Notice", noticeService);
			reg.rebind("Question", qnaService);
			reg.rebind("msg", msgService);
			reg.rebind("searchData", searchDataService);
			reg.rebind("doctorVisitClinic",doctorVisitClinicService);
			reg.rebind("schedule", scheduleService);
			reg.rebind("adminDB", adminDBservice);
			reg.rebind("doctorMain", doctorMainService);
			reg.rebind("adminMain", adminMainService);
			reg.rebind("disease", diseaseService );
			reg.rebind("examination", examinationService);
			reg.rebind("survey", surveyService);
			reg.rebind("patientAppointment", patientAppointmentService);
			
			
			System.out.println("서버 준비 완료");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
