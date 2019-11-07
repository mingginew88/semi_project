package survey;

import java.rmi.Remote;
import java.rmi.RemoteException;

import VO.SurveyVO;

public interface ISurveyService extends Remote{
	/**
	 * 평가 저장하는 메서드
	 * @param survey
	 * @return
	 * @throws RemoteException
	 */
	public int insertsurvey(SurveyVO survey) throws RemoteException;

}
