package disease;

import java.util.List;

import VO.DiseaseVO;

public interface IDiseaseDao {

	/**
	 * 
	 * @param pa_id
	 * @return
	 */
	public List<DiseaseVO> searchDiseaseAll(String pa_id);
	
}
