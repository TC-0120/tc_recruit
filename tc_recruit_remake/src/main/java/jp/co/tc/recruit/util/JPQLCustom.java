package jp.co.tc.recruit.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.repository.CandidateRepository;

public class JPQLCustom {
	@Autowired
	CandidateRepository candidateRepo;
	public List<Candidate> JPQLBySortNum(List<Integer> sortNumber, Integer sortType){
		String query = "SELECT c FROM Candidate c WHERE";
		
		return candidateRepo.findByOrderByCandidateId();
	}
}
