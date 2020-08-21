package jp.co.tc.recruit.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.tc.recruit.constant.JPQLConstant;
import jp.co.tc.recruit.entity.view.V_TransitionPopulation;

public interface V_TransitionPopulationRepository extends JpaRepository<V_TransitionPopulation, Integer>,V_TransitionPopulationRepositoryCustom{
	@Query(JPQLConstant.FIND_BY_INSERT_YEAR_AND_INSERT_MONTH)
	public List<V_TransitionPopulation> findByInsertYearAndInsertMonth(@Param("year") Integer year);

	@Query(JPQLConstant.FIND_BY_INSERT_YEAR_AND_INSERT_MONTH_AND_SLC_STATUS_ID)
	public List<V_TransitionPopulation> findByInsertYearAndInsertMonthAndSlcStatusId(@Param("year") Integer year,@Param("month") Integer month);

	@Query(JPQLConstant.FIND_BY_INSERT_YEAR_AND_BETWEEN_INSERT_MONTH_AND_SLC_STATUS_ID)
	public List<V_TransitionPopulation> findByInsertYearBetweenInsertMonthAndSlcStatusId(@Param("year") Integer year,@Param("month") Integer month);

	@Query(JPQLConstant.FIND_BY_INSERT_YEAR_AND_AGENT)
	public List<V_TransitionPopulation> findByInsertYearAndAgent(@Param("year") Integer year,@Param("agentId") Integer agentId);

	@Query(JPQLConstant.FIND_BY_INSERT_YEAR_AND_UNIVERSITY_RANK)
	public List<V_TransitionPopulation> findByInsertYearAndUniversityRank(@Param("year") Integer year,@Param("universityRankId") Integer universityRankId);

	public List<V_TransitionPopulation> findByAgentId(Integer agentId);
}
