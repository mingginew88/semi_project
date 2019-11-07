package examination;

import java.util.List;

import VO.ExaminationVO;

public interface IExaminationDao {
	
	/**
	 * 환자아이디로 검사 결과를 다 가져오는 메서드
	 * @param pa_id
	 * @return
	 */
	public List<ExaminationVO> searchExaminationAll(String pa_id);
}
