package SoftSquared.PeopleOfDelivery.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void cleanUp(){
        userRepository.deleteAll();
        userRepository.save(new User("testName1","test1@naver.com","123321","010-1111-2222"
                                ,"서울시송파구잠실6동", 1,1,"1995-03-10",1));
    }

    @Test
    public void 회원이_잘들어가는지_확인() throws Exception {
        // when
        List<User> users = (List<User>) userRepository.findAll();
        // then
        assertEquals(users.get(0).getId(),1);
        assertThat(users).hasSizeGreaterThan(0);
        System.out.println("유저 데이터 들어가는 것 확인");
    }
}