package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.user.GetUserRes;
import SoftSquared.PeopleOfDelivery.domain.user.PostUserRes;
import SoftSquared.PeopleOfDelivery.provider.UserProvider;
import SoftSquared.PeopleOfDelivery.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;
import static SoftSquared.PeopleOfDelivery.utils.ValidationRegex.isRegexEmail;

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
    @ApiOperation(value = "전체 회원 조회 (관리자 기능)", notes = "회원 목록 불러오기")
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
    @ApiOperation(value = "회원 프로필 조회 (회원 기능)", notes = "회원 프로필 조회")
    public BaseResponse<GetUserRes> getUser(
            @PathVariable Long userId){
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

    /**
     * 회원가입 API
     * [POST] /users
     * @RequestBody PostUserReq
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @RequestMapping(name = "/users", method = RequestMethod.POST)
    @ApiOperation(value = "회원 가입 (회원 기능)", notes = "회원 가입")
    public BaseResponse<PostUserRes> CreateUser(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "confirm-password") String confirmPassword,
            @RequestParam(name = "phone-number") String phoneNumber,
            @RequestParam(name = "role") Integer role,
            @RequestParam(name = "birthdate") String birthdate,
            @RequestParam(name = "gender") Integer gender){

        // 1. Body Parameter Validation
        if (email == null || email.length() == 0) {
            return new BaseResponse<>(EMPTY_EMAIL);
        }
        if (!isRegexEmail(email)){
            return new BaseResponse<>(INVALID_EMAIL);
        }
        if (password == null || password.length() == 0) {
            return new BaseResponse<>(EMPTY_PASSWORD);
        }
        if (confirmPassword == null || confirmPassword.length() == 0) {
            return new BaseResponse<>(EMPTY_CONFIRM_PASSWORD);
        }
        if (!password.equals(confirmPassword)) {
            return new BaseResponse<>(DO_NOT_MATCH_PASSWORD);
        }
        if (name == null || name.length() == 0) {
            return new BaseResponse<>(EMPTY_NICKNAME);
        }

        // 2. Post UserInfo
        try {
            PostUserRes postUserRes = userService.createUser(
                    name, email, password, phoneNumber, role, birthdate, gender
            );
            return new BaseResponse<>(SUCCESS_POST_USER, postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 업데이트
     * 사진, 위치
     */

    /**
     * 삭제
     */

}

















