package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Agent;

/**
 * 採用エージェントのリポジトリ
 *
 * @author TC-0115
 *
 */
public interface AgentRepository extends JpaRepository<Agent, Integer> {
	public List<Agent> findByOrderByAgentId();

	public Agent findByAgentId(Integer id);
}
