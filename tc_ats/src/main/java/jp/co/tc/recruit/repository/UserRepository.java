package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.User;
import jp.co.tc.recruit.entity.User.Authority;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsername(String username);

	public List<User> findAllByOrderByUsername();
	public List<User> findByDeleteFlagOrderByUsername(Integer DeleteFlag);
	public List<User> findAllById(Integer id);
	public List<User> findByUsernameLikeAndDeleteFlag(String username,Integer DeleteFlag);
	public List<User> findByLastNameLikeAndDeleteFlag(String lastName,Integer DeleteFlag);
	public List<User> findByFirstNameLikeAndDeleteFlag(String firstName,Integer DeleteFlag);
	public List<User> findByUsernameLikeAndLastNameLikeAndFirstNameLikeAndDeleteFlag(String sarchWord, String sarchWord2, String sarchWord3,Integer DeleteFlag);
	public List<User> findByAuthority(Authority authority);
	public List<User> findByStatus(Integer i);
	public User findById(int i);
	public List<User> findAllByOrderByAuthority();
	public List<User> findAllByOrderByStatusDesc();
	public boolean existsByUsername(String username);
 }

