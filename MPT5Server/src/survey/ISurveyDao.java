package survey;

import VO.SurveyVO;

public interface ISurveyDao {
	/**
	 * 평가 저장하는 메서드
	 * @param survey
	 * @return
	 * @throws RemoteException
	 */
	public int insertsurvey(SurveyVO survey);
}
