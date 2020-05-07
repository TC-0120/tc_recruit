package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Referrer;

/**
 * 紹介元のリポジトリ
 *
 * @author TC-0115
 *
 */
public interface ReferrerRepository extends JpaRepository<Referrer, Integer> {
	public List<Referrer> findByOrderByReferrerId();

	public Referrer findByReferrerId(Integer id);
}
