package jp.co.tc.recruit.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.SelectionStatus;

/**
 * 選考ステータスのリポジトリ
 *
 * @author TC-0115
 *
 */
public interface SelectionStatusRepository extends JpaRepository<SelectionStatus, Integer> {
<<<<<<< HEAD
	/*public SelectionRepository findBySlcPK(Integer slcPK);*/

=======
	public SelectionStatus findBySlcStatusId(Integer id);

	/*松原*/
	public List<SelectionStatus> findByOrderBySlcStatusId();
>>>>>>> 7827f19e166a8ddb2c63b84dc03b27e6806564f4
}
