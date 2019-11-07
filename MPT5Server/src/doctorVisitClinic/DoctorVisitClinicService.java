package doctorVisitClinic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import VO.AppointmentVO;
import VO.DiseaseVO;
import VO.ExaminationVO;
import VO.MedicineVO;
import VO.PatientVO;
import VO.PrescriptionVO;
import VO.VisitclinicVO;

public class DoctorVisitClinicService extends UnicastRemoteObject implements IDoctorVisitClinicService{
	private IDoctorVisitClinicDao dcDao;
	
	public DoctorVisitClinicService() throws RemoteException {
		dcDao = DoctorVisitClinicDao.getInstance();
	}

	@Override
	public List<AppointmentVO> getAllAppoint(int doctor_num) throws RemoteException {
		return dcDao.getAllAppoint(doctor_num);
	}

	@Override
	public PatientVO getPaInfo(String pa_id) throws RemoteException {
		return dcDao.getPaInfo(pa_id);
	}

	@Override
	public List<ExaminationVO> getExInfo(String pa_id) throws RemoteException {
		return dcDao.getExInfo(pa_id);
	}

	@Override
	public List<MedicineVO> getMedi(Map<String, String> data) throws RemoteException {
		return dcDao.getMedi(data);
	}

	@Override
	public List<DiseaseVO> getDis(String pa_id) throws RemoteException {
		return dcDao.getDis(pa_id);
	}

	@Override
	public int insertVisitClinic(VisitclinicVO vc) throws RemoteException {
		return dcDao.insertVisitClinic(vc);
	}

	@Override
	public int insertDisease(DiseaseVO dis) throws RemoteException {
		return dcDao.insertDisease(dis);
	}

	@Override
	public int insertPrescription(PrescriptionVO presc) throws RemoteException {
		return dcDao.insertPrescription(presc);
	}

	@Override
	public int maxVCNum() throws RemoteException {
		return dcDao.maxVCNum();
	}

	@Override
	public int maxDisNum() throws RemoteException {
		return dcDao.maxDisNum();
	}

	@Override
	public int insertExam(ExaminationVO exam) throws RemoteException {
		return dcDao.insertExam(exam);
	}

	@Override
	public boolean searchApptVC(int appt_num) throws RemoteException {
		return dcDao.searchApptVC(appt_num);
	}

	@Override
	public VisitclinicVO getPaVC(int appt_num) throws RemoteException {
		return dcDao.getPaVC(appt_num);
	}

	@Override
	public PrescriptionVO getPaPresc(int clinic_num) throws RemoteException {
		return dcDao.getPaPresc(clinic_num);
	}

	@Override
	public DiseaseVO getPaDisease(int clinic_num) throws RemoteException {
		return dcDao.getPaDisease(clinic_num);
	}

	@Override
	public int updateVC(VisitclinicVO vc) throws RemoteException {
		return dcDao.updateVC(vc);
	}

	@Override
	public int updatePresc(PrescriptionVO presc) throws RemoteException {
		return dcDao.updatePresc(presc);
	}

	@Override
	public int updateDisease(DiseaseVO dis) throws RemoteException {
		return dcDao.updateDisease(dis);
	}

	@Override
	public int updateExam(ExaminationVO exam) throws RemoteException {
		return dcDao.updateExam(exam);
	}

	@Override
	public ExaminationVO getPaExam(int clinic_num) throws RemoteException {
		return dcDao.getPaExam(clinic_num);
	}
	
	

}
