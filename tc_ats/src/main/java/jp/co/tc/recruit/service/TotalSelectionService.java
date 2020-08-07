package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.view.TotalSelectionView;
import jp.co.tc.recruit.repository.TotalSelectionRepository;

@Service
public class TotalSelectionService {

	@Autowired
	TotalSelectionRepository ttlSlcRepo;

	public TotalSelectionView findByStatusMessageId(Integer statusMessageId) {
		return ttlSlcRepo.findByStatusMessageId(statusMessageId);
	}

	public List<TotalSelectionView> findAllByOrderByStatusMessageId(){
		return ttlSlcRepo.findAllByOrderByStatusMessageId();
	}

	public List<TotalSelectionView> findAllByOrderBySelectionStatusId(){
		return ttlSlcRepo.findAllByOrderBySelectionStatusId();
	}

	public List<TotalSelectionView> findAllByOrderBySort() {
		return ttlSlcRepo.findAllByOrderBySort();
	}

	public List<TotalSelectionView> findAll() {
		return ttlSlcRepo.findAll();
	}

	public TotalSelectionView findBySort(Integer sort) {
		return ttlSlcRepo.findBySort(sort);
	}
}
