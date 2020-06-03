package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.User;
import jp.co.tc.recruit.entity.User.Authority;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsername(String username);
<<<<<<< HEAD


}
=======
	public List<User> findAllByOrderByUsername();
	public List<User> findAllById(Integer id);
	public List<User> findByUsernameLike(String username);
	public List<User> findByLastNameLike(String lastName);
	public List<User> findByFirstNameLike(String firstName);
	public List<User> findByUsernameLikeAndLastNameLikeAndFirstNameLike(String sarchWord, String sarchWord2, String sarchWord3);
	public List<User> findByAuthority(Authority authority);
	public List<User> findByStatus(Integer i);
	public User findById(int i);
	public List<User> findAllByOrderByAuthority();
	public List<User> findAllByOrderByStatusDesc();
 }
>>>>>>> masterMaintenance
