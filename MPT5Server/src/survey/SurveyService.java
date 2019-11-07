package survey;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import VO.SurveyVO;

public class SurveyService extends UnicastRemoteObject implements ISurveyService{
	private ISurveyDao sDao;
	
	public SurveyService() throws RemoteException {
		sDao = SurveyDao.getInstance();
	}

	@Override
	public int insertsurvey(SurveyVO survey) throws RemoteException {
		return sDao.insertsurvey(survey);
	}
	
	

}
