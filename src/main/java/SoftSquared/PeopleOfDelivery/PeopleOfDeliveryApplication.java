package SoftSquared.PeopleOfDelivery;

import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeopleOfDeliveryApplication implements CommandLineRunner{

	private final UserRepository userRepository;

	public PeopleOfDeliveryApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PeopleOfDeliveryApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception{
		userRepository.save(new User("testName1","test1@naver.com","123321","010-1111-2222"
				,"서울시송파구잠실6동", 1,1,"1995-03-10",1));
	}

}
