package SoftSquared.PeopleOfDelivery.domain.user;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.provider.UserProvider;
import SoftSquared.PeopleOfDelivery.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.DUPLICATED_USER;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class duplicateUserTest {

    UserService userService;
    UserProvider userProvider;
    UserRepository userRepository;

    @Autowired
    duplicateUserTest(UserService userService, UserProvider userProvider,
                      UserRepository userRepository) {
        this.userService = userService;
        this.userProvider = userProvider;
        this.userRepository = userRepository;
    }
    
    /*
    String name, String email, String password, String phoneNumber,
                                  Integer role, String birthdate, Integer gender
     */
    @Test
    public void 중복회원예외처리테스트() throws BaseException {
        userRepository.deleteAll();

        userService.createUser("testName1", "test1@naver.com", "123321",
                "010-1111-2222",1,"1995-03-10", 1);

        BaseException e = Assertions.assertThrows(BaseException.class,
                () -> userService.createUser("testName1", "test1@naver.com", "123321",
                        "010-1111-2222",1,"1995-03-10", 1));

        assertThat(e.getStatus().getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }
}
