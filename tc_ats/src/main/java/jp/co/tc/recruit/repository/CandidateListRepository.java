package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.CandidateList;

public interface CandidateListRepository extends JpaRepository<CandidateList, Integer>{
	
}
