package jp.co.tc.recruit.repository.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jp.co.tc.recruit.repository.CandidatesViewRepositoryCustom;
import jp.co.tc.recruit.view.CandidatesView;

public class CandidatesViewRepositoryImpl implements CandidatesViewRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<CandidatesView> findBySlcDate(String from, String to) {
		String queryStr = "from CandidatesView WHERE slcDate ";

		if (!from.isEmpty() && !to.isEmpty()) {
			queryStr += "BETWEEN :from AND :to ORDER BY slcDate";
		} else if (from.isEmpty() && !to.isEmpty()) {
			queryStr += "< :to ORDER BY slcDate";
		} else if (!from.isEmpty() && to.isEmpty()) {
			queryStr += ">= :from ORDER BY slcDate";
		}

		Query query = em.createQuery(queryStr);

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			if (!from.isEmpty()) {
				query.setParameter("from", sdf.parse(from));
			}

			if (!to.isEmpty()) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(to));
				cal.add(Calendar.DAY_OF_MONTH, 1);
				query.setParameter("to", cal.getTime());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return query.getResultList();
	}

}
