package jp.co.tc.recruit.repository;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jp.co.tc.recruit.view.CandidatesView;

public class CandidatesViewRepositoryImpl implements CandidatesViewRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<CandidatesView> findBySlcDate(String period) {
		Calendar today = Calendar.getInstance();
		Calendar after = Calendar.getInstance();
		Query query = null;

		if (period.equals("a month")) {
			after.add(Calendar.MONTH, 1);
		} else {
			if (period.equals("a week")) {
				after.add(Calendar.DAY_OF_MONTH, 7);
			} else if (period.equals("two week")) {
				after.add(Calendar.DAY_OF_MONTH, 14);
			}
		}

		query = em.createQuery("from CandidatesView WHERE slcDate BETWEEN :today AND :after ORDER BY slcDate")
				.setParameter("today", today.getTime())
				.setParameter("after", after.getTime());

		//Query query = em.createNativeQuery("SELECT * " +
		//		"FROM VIEW_CANDIDATES " +
		//		"WHERE  selection_date BETWEEN CURRENT_DATE AND (CURRENT_DATE + INTERVAL '7 DAY')", CandidatesView.class);
		return query.getResultList();
	}

}
