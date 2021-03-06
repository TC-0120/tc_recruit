package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.Agent;
import jp.co.tc.recruit.repository.AgentRepository;

/**
 * 採用エージェントのサービスクラス
 *
 * @author TC-0115
 *
 */
@Service
public class AgentService {

	@Autowired
	AgentRepository agentRepo;

	public List<Agent> findAll() {
		return agentRepo.findByOrderByAgentId();
	}

}
