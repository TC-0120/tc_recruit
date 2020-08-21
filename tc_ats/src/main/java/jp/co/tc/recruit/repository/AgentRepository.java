package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Integer>{
	public List<Agent> findAllByOrderByAgentId();

	public Agent findByAgentIdAndDeleteFlag(Integer agentId,Integer DeleteFlagConstant);

	public Agent findFirstByDeleteFlag(Integer DeleteFlagConstant);

	public List<Agent> findByDeleteFlagOrderByAgentId(Integer DeleteFlagConstant);
}
