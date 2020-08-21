package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.entity.Agent;
import jp.co.tc.recruit.repository.AgentRepository;

@Service
public class AgentService {

	@Autowired
	AgentRepository agentRepository;

	public List<Agent> findAll(){
		return agentRepository.findAllByOrderByAgentId();
	}
	public List<Agent> findByNotDeleted(){
		return agentRepository.findByDeleteFlagOrderByAgentId(DeleteFlagConstant.NOT_DELETED);
	}
	public Agent findTop1() {
		return agentRepository.findFirstByDeleteFlag(DeleteFlagConstant.NOT_DELETED);
	}
}
