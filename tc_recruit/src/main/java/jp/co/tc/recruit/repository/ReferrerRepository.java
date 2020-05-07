package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Referrer;

public interface ReferrerRepository extends JpaRepository<Referrer, Integer> {
	public List<Referrer> findByOrderByReferrerId();
}
