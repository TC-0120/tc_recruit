package jp.co.tc.recruit.repository.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jp.co.tc.recruit.constant.SlcStatusConstant;
import jp.co.tc.recruit.constant.SlcStatusDtlConstant;
import jp.co.tc.recruit.form.ConditionsForm;
import jp.co.tc.recruit.repository.CandidatesViewRepositoryCustom;
import jp.co.tc.recruit.view.CandidatesView;

public class CandidatesViewRepositoryImpl implements CandidatesViewRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<CandidatesView> findBySlcDate(String from, String to) {
		String queryStr = "from CandidatesView WHERE slcStatusDtlId = 2 AND slcDate ";

		if (!from.isEmpty() && !to.isEmpty()) {
			queryStr += "BETWEEN :from AND :to ORDER BY slcDate";
		} else if (from.isEmpty() && !to.isEmpty()) {
			queryStr += "< :to ORDER BY slcDate";
		} else if (!from.isEmpty() && to.isEmpty()) {
			queryStr += ">= :from ORDER BY slcDate";
		}

		Query query = em.createQuery(queryStr);

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

	@SuppressWarnings("unchecked")
	public List<CandidatesView> findBySlcStatusIdAndSlcStatudDtlId(ConditionsForm cf) {
		Integer ssId = cf.getSlcStatusId();
		Integer ssdId = cf.getSlcStatusDtlId();
		Integer order = cf.getOrder();
		Integer dir = cf.getDirection();

		String queryStr = "from CandidatesView";

		if (ssdId == SlcStatusDtlConstant.ALL) {

			if (ssId != SlcStatusConstant.ALL) {
				queryStr += " WHERE slcStatusId = :ssId";
			}
		} else if (ssdId == SlcStatusDtlConstant.NEED_RESPOND) {
			queryStr += " WHERE slcStatusDtlId = :adjusting OR slcStatusDtlId = :passing OR slcStatusDtlId = :acceptance "
					+ "OR ((slcStatusDtlId = :selecting OR slcStatusDtlId = :waitingAcceptance OR slcStatusDtlId = :confirmed) "
					+ "AND slcDate < :today)";

			if (ssId != SlcStatusConstant.ALL) {
				queryStr += " AND slcStatusId = :ssId";
			}
		} else {
			queryStr += " WHERE slcStatusDtlId = :ssdId";

			if (ssId != SlcStatusConstant.ALL) {
				queryStr += " AND slcStatusId = :ssId";
			}
		}

		switch (order) {
			case ConditionsForm.CANDIDATE_ID:
				queryStr += " ORDER BY candidateId";
				break;
			case ConditionsForm.GENDER:
				queryStr += " ORDER BY gender";
				break;
			case ConditionsForm.AGENT:
				queryStr += " ORDER BY agentId";
				break;
			case ConditionsForm.REFERRER:
				queryStr += " ORDER BY referrerId";
				break;
			case ConditionsForm.SLC_STATUS:
				queryStr += " ORDER BY slcStatusId";
				break;
			case ConditionsForm.SLC_STATUS_DTL:
				queryStr += " ORDER BY slcStatusDtlId";
				break;
			default:
				break;
		}

		if (dir == ConditionsForm.DESC) {
			queryStr += " DESC";
		}

		Query query = em.createQuery(queryStr);

		if (ssId != SlcStatusConstant.ALL) {
			query.setParameter("ssId", ssId);
		}

		if (ssdId == SlcStatusDtlConstant.NEED_RESPOND) {
			query.setParameter("adjusting", SlcStatusDtlConstant.ADJUSTING)
				 .setParameter("passing", SlcStatusDtlConstant.PASSING)
				 .setParameter("acceptance", SlcStatusDtlConstant.ACCEPTANCE)
				 .setParameter("selecting", SlcStatusDtlConstant.SELECTING)
				 .setParameter("waitingAcceptance", SlcStatusDtlConstant.WATING_ACCEPTANCE)
				 .setParameter("confirmed", SlcStatusDtlConstant.CONFIRMED)
				 .setParameter("today", Calendar.getInstance().getTime());
		} else if (ssdId != SlcStatusDtlConstant.ALL) {
			query.setParameter("ssdId", ssdId);
		}


		return query.getResultList();
	}

}
