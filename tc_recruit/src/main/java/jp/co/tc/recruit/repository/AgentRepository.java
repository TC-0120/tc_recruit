package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Integer> {

}
