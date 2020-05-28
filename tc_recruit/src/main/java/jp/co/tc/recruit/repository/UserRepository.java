package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsername(String username);
	public List<User> findAllByOrderByUsername();
	public List<User> findAllById(Integer id);
	public List<User> findByUsernameLike(String username);
	public List<User> findByLastNameLike(String lastName);
	public List<User> findByFirstNameLike(String firstName);
	public List<User> findByAuthorityLike(String authority);
}
