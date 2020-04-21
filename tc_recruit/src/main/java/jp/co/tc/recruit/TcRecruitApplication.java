package jp.co.tc.recruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan("jp.co.tc.recruit.entity")//←今回追加したアノテーション
@EnableJpaRepositories("jp.co.tc.recruit.repository")
public class TcRecruitApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcRecruitApplication.class, args);
	}

}
