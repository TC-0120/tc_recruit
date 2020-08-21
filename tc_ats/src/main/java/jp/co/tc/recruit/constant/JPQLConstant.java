package jp.co.tc.recruit.constant;

public class JPQLConstant {
	//該当年度の候補者情報のみ検索
	public static final String FIND_BY_INSERT_YEAR_AND_INSERT_MONTH =
			"SELECT tr FROM V_TransitionPopulation tr WHERE (tr.insertYear = :year AND tr.insertMonth BETWEEN 4 AND 12) "
			+ "OR (tr.insertYear = (:year + 1) AND tr.insertMonth BETWEEN 1 AND 3) "
			+ "ORDER BY tr.insertMonth, tr.candidateId";

	public static final String FIND_BY_INSERT_YEAR_AND_AGENT =
			"SELECT tr FROM V_TransitionPopulation tr WHERE tr.agentId = :agentId "
			+ "AND ((tr.insertYear = :year AND tr.insertMonth BETWEEN 4 AND 12) "
			+ "OR (tr.insertYear = (:year + 1) AND tr.insertMonth BETWEEN 1 AND 3)) ORDER BY tr.candidateId";

	public static final String FIND_BY_INSERT_YEAR_AND_UNIVERSITY_RANK =
			"SELECT tr FROM V_TransitionPopulation tr WHERE tr.universityRankId = :universityRankId "
			+ "AND ((tr.insertYear = :year AND tr.insertMonth BETWEEN 4 AND 12) "
			+ "OR (tr.insertYear = (:year + 1) AND tr.insertMonth BETWEEN 1 AND 3)) ORDER BY tr.candidateId";

	public static final String FIND_BY_INSERT_YEAR_AND_INSERT_MONTH_AND_SLC_STATUS_ID =
			"SELECT tr FROM V_TransitionPopulation tr WHERE tr.insertYear = :year AND tr.insertMonth = :month "
			+ "ORDER BY tr.candidateId";

	public static final String FIND_BY_INSERT_YEAR_AND_BETWEEN_INSERT_MONTH_AND_SLC_STATUS_ID =
			"SELECT tr FROM V_TransitionPopulation tr WHERE tr.insertYear = (:year + 1) AND tr.insertMonth = :month "
			+ "ORDER BY tr.candidateId";
}
