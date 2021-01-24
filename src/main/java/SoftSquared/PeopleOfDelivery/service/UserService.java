package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.secret.Secret;
import SoftSquared.PeopleOfDelivery.domain.user.PostUserRes;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import SoftSquared.PeopleOfDelivery.provider.UserProvider;
import SoftSquared.PeopleOfDelivery.utils.AES128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final UserProvider userProvider;

    @Autowired
    public UserService(UserRepository userRepository, UserProvider userProvider) {
        this.userRepository = userRepository;
        this.userProvider = userProvider;
    }


    /**
     * 회원 가입
     * @param name
     * @param email
     * @param password
     * @param phoneNumber
     * @param role
     * @param birthdate
     * @param gender
     * @return
     */
    @Transactional
    public PostUserRes createUser(String name, String email, String password, String phoneNumber,
                                  Integer role, String birthdate, Integer gender) throws BaseException {

        User existsUser = null;
        try {
            // 1-1. 이미 존재하는 회원이 있는지 조회
            existsUser = userProvider.retrieveUserByEmail(email);
        } catch (BaseException exception) {
            // 1-2. 이미 존재하는 회원이 없다면 그대로 진행
            if (exception.getStatus() != NOT_FOUND_USER) {
                throw exception;
            }
        }
        // 1-3. 이미 존재하는 회원이 있다면 return DUPLICATED_USER
        if (existsUser != null) {
            throw new BaseException(DUPLICATED_USER);
        }

        // 2. 유저 정보 생성
        String EncodingPassword = password;
        try {
            EncodingPassword = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(password);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_POST_USER);
        }

        User newUser = User.builder()
                .name(name)
                .email(email)
                .password(EncodingPassword)
                .phoneNumber(phoneNumber)
                .role(role)
                .birthdate(birthdate)
                .gender(gender)
                .status(1)
                .imageURL("/img/users/default")
                .build();

        // 3. 유저 정보 저장
        try {
            newUser = userRepository.save(newUser);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_POST_USER);
        }

        // 4. JWT 생성
        //String jwt = jwtService.createJwt(newUser.getId());

        // 5. UserInfoLoginRes로 변환하여 return
        Long id = newUser.getId();
        return PostUserRes.builder()
                .id(id)
                //.Jwt(jwt)
                .build();
    }


}
