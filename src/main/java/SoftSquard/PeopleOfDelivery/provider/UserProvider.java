package SoftSquard.PeopleOfDelivery.provider;

import SoftSquard.PeopleOfDelivery.config.BaseException;
import SoftSquard.PeopleOfDelivery.config.BaseResponseStatus;
import SoftSquard.PeopleOfDelivery.domain.user.User;
import SoftSquard.PeopleOfDelivery.domain.user.UserRepository;
import SoftSquard.PeopleOfDelivery.domain.user.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProvider {

    private final UserRepository userRepository;

    @Autowired
    public UserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<GetUserRes> retrieveUserList(String name) throws BaseException {
        List<User> userList;
        try{
           if(name == null){ //전체조회
                userList = userRepository.findByStatus(1);
           }else{ //검색조회
                userList = userRepository.findByStatusAndNameIsContaining(1,name);
           }
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.FAILED_TO_GET_USER);
        }
        //User 객체를 getUsersRes로 변환.
        return userList.stream().map(user -> {
            Long id = user.getId();
            String userName = user.getName();
            String email = user.getEmail();
            String phoneNumber = user.getPhoneNumber();
            String imageUrI = user.getImageURI();
            return new GetUserRes(id,userName,email,phoneNumber,imageUrI);
        }).collect(Collectors.toList());
    }

    public GetUserRes retrieveUser(Integer userId) throws BaseException{
        User user;
        try{
            user = userRepository.findById(userId);
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.FAILED_TO_GET_USER);
        }


        if(user == null || user.getStatus() == 3){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_USER);
        }

        return GetUserRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .image_url(user.getImageURI())
                .build();
    }
}
