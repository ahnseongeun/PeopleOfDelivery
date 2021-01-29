package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.secret.Secret;
import SoftSquared.PeopleOfDelivery.domain.user.PostLoginRes;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import SoftSquared.PeopleOfDelivery.domain.user.GetUserRes;
import SoftSquared.PeopleOfDelivery.utils.AES128;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
@Slf4j
public class UserProvider {

    private final UserRepository userRepository;

    @Autowired
    public UserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 전체 조회
     * @param name
     * @return
     * @throws BaseException
     */
    public List<GetUserRes> retrieveUserList(String name) throws BaseException {
        List<User> userList;
        try{
           if(name == null){ //전체조회
                userList = userRepository.findByStatus(1);
           }else{ //검색조회
                userList = userRepository.findByStatusAndNameIsContaining(1,name);
           }
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_GET_USER);
        }
        //User 객체를 getUsersRes로 변환.
        return userList.stream().map(user -> {
            Long id = user.getId();
            String userName = user.getName();
            String email = user.getEmail();
            String phoneNumber = user.getPhoneNumber();
            String imageURL = user.getImageURL();
            Integer status = user.getStatus();
            return GetUserRes.builder()
                    .id(id)
                    .name(userName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .imageURL(imageURL)
                    .status(status)
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * 회원 조회
     * @param userId
     * @return
     * @throws BaseException
     */
    public GetUserRes retrieveUser(Long userId) throws BaseException{
        User user;
        log.info("회원 조회");
        user = userRepository.findByIdAndStatus(userId,1).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_USER));

        if(user.getStatus() == 2){
            throw new BaseException(STOPED_USER);
        }

        if(user == null || user.getStatus() == 3){
            throw new BaseException(NOT_FOUND_USER);
        }
        log.info("회원 ID: "+user.getId());
        return GetUserRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .imageURL(user.getImageURL())
                .status(user.getStatus())
                .build();
    }


    /**
     * email로 중복된 회원 조회
     * @param email
     * @return
     */
    public User retrieveUserByEmail(String email) throws BaseException {

        List<User> existsUserList;

        // DB에 접근해서 email로 회원 정보 조회
        try{
            existsUserList = userRepository.findByStatusAndEmailIsContaining(1,email);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_GET_USER);
        }

        // userList에 중복된 회원이 있는지 검사
        User user;
        if (existsUserList != null && existsUserList.size() > 0) {
            user = existsUserList.get(0);
        } else {
            throw new BaseException(NOT_FOUND_USER);
        }

        return user;
    }


}
