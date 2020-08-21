package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.selection.SelectionReferrer;
import jp.co.tc.recruit.repository.SelectionReferrerRepository;

@Service
public class SelectionReferrerService {
	@Autowired
	SelectionReferrerRepository selectionReferrerRepository;

	public List<SelectionReferrer> findAll(){
		return selectionReferrerRepository.findByOrderBySelectionReferrerId();
	}
}
