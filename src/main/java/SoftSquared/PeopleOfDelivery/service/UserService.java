package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.secret.Secret;
import SoftSquared.PeopleOfDelivery.domain.coupon.Coupon;
import SoftSquared.PeopleOfDelivery.domain.coupon.CouponRepository;
import SoftSquared.PeopleOfDelivery.domain.user.*;
import SoftSquared.PeopleOfDelivery.provider.UserProvider;
import SoftSquared.PeopleOfDelivery.utils.AES128;
import SoftSquared.PeopleOfDelivery.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;
import static SoftSquared.PeopleOfDelivery.config.secret.Secret.FILE_UPLOAD_DIRECTORY;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository,
                       CouponRepository couponRepository,
                       UserProvider userProvider,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
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
                .location("default")
                .status(1)
                .imageURL("img/users/default")
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

        // 유저와 쿠폰은 1:1관계 여서 쿠폰 테이블도 같이 생성
        Coupon coupon = Coupon.builder()
                .coupon1000(0)
                .coupon3000(0)
                .coupon5000(0)
                .user(newUser)
                .build();
        try {
            couponRepository.save(coupon);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_POST_COUPON);
        }

        return PostUserRes.builder()
                .id(id)
                //.Jwt(jwt)
                .build();
    }



    /**
     * 내 프로필 수정
     * @param userId
     * @param location
     * @param imageFile
     * @return
     */
    public UpdateUserRes updateUser(Long userId,
                                    String updatePassword,
                                    String location,
                                    MultipartFile imageFile) throws BaseException {

        User user = userRepository.findByIdAndStatus(userId,1).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_USER));

        String imageURL;

        if(imageFile != null){
            String filename = imageFile.getOriginalFilename();
            imageURL = FILE_UPLOAD_DIRECTORY + "/users/" + user.getName()+user.getPhoneNumber()+filename;
            //TODO
            //imageFile.transferTo(new File(imageURI));
            user.setImageURL(imageURL);
        }
        if(!updatePassword.equals("empty")){
            user.setPassword(updatePassword);
        }

        if(!location.equals(user.getLocation()) || location.equals("default")) {
            user.setLocation(location);
        }

        userRepository.save(user);

        return UpdateUserRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .location(user.getLocation())
                .imageURL(user.getImageURL())
                .build();

    }

    public DeleteUserRes deleteUser(Long userId) throws BaseException {

        User user = userRepository.findByIdAndStatus(userId,1).
                orElseThrow(() -> new BaseException(FAILED_TO_GET_USER));
        user.setStatus(2);
        userRepository.save(user);
        return DeleteUserRes.builder()
                .userId(user.getId())
                .status(user.getStatus())
                .build();
    }

    /**
     * 로그인
     * @param email
     * @param password
     * @return
     */
    public PostLoginRes login(String email, String password) throws BaseException{
        User user= userRepository.findByEmailAndStatus(email,1)
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));

        // 2. UserInfo에서 password 추출
        String comparePassword;
        try {
            comparePassword = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_LOGIN);
        }

        // 3. 비밀번호 일치 여부 확인
        if (!password.equals(comparePassword)) {
            throw new BaseException(WRONG_PASSWORD);
        }

        // 3. Create JWT
        String jwt = jwtService.createJwt(user.getId(),user.getRole());

        return PostLoginRes.builder()
                .jwt(jwt)
                .build();
    }
}
