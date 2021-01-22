package SoftSquard.PeopleOfDelivery.controller;

import SoftSquard.PeopleOfDelivery.config.BaseException;
import SoftSquard.PeopleOfDelivery.config.BaseResponse;
import SoftSquard.PeopleOfDelivery.domain.user.GetUserRes;
import SoftSquard.PeopleOfDelivery.provider.UserProvider;
import SoftSquard.PeopleOfDelivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static SoftSquard.PeopleOfDelivery.config.BaseResponseStatus.*;

@Controller
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;
    private final UserProvider userProvider;


    @Autowired
    public UserController(UserService userService, UserProvider userProvider){
        this.userProvider = userProvider;
        this.userService = userService;
    }

    /**
     * 회원 전체 조회 API
     * [GET] /users
     * 회원 닉네임 검색 조회 API
     * [GET] /users?name=
     * @return BaseResponse<List<GetUsersRes>>
     */
    @ResponseBody
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public BaseResponse<List<GetUserRes>> getUsers(
            @RequestParam(value = "name",required = false) String name) {
       try{
           List<GetUserRes> getUsersResList = userProvider.retrieveUserList(name);
           if (name == null) {
               return new BaseResponse<>(SUCCESS_READ_USERS, getUsersResList);
           } else {
               return new BaseResponse<>(SUCCESS_READ_SEARCH_USERS, getUsersResList);
           }
       }catch(BaseException exception){
           return new BaseResponse<>(exception.getStatus());
       }
    }

    /**
     * 회원 조회 API
     * [GET] /users/:userId
     * @PathVariable userId
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @RequestMapping(value = "/users/{userId}",method = RequestMethod.GET)
    public BaseResponse<GetUserRes> getUser(
            @PathVariable Integer userId){
        if(userId <= 0) {
            return new BaseResponse<>(EMPTY_USERID);
        }

        try{
            GetUserRes getUserRes = userProvider.retrieveUser(userId);
            return new BaseResponse<>(SUCCESS_READ_USER, getUserRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }
}
