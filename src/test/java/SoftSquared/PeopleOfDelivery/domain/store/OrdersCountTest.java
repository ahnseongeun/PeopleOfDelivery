package SoftSquared.PeopleOfDelivery.domain.store;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import SoftSquared.PeopleOfDelivery.provider.StoreProvider;
import SoftSquared.PeopleOfDelivery.provider.UserProvider;
import SoftSquared.PeopleOfDelivery.service.StoreService;
import SoftSquared.PeopleOfDelivery.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrdersCountTest {

    UserService userService;
    UserProvider userProvider;
    UserRepository userRepository;
    StoreService storeService;
    StoreProvider storeProvider;
    StoreRepository storeRepository;

    @Autowired
    OrdersCountTest(UserService userService, UserProvider userProvider,
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
    public void 주문많은순으로정렬하기() throws BaseException {


    }
}
