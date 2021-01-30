package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.user.*;
import SoftSquared.PeopleOfDelivery.provider.UserProvider;
import SoftSquared.PeopleOfDelivery.service.UserService;
import SoftSquared.PeopleOfDelivery.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.Relation;
import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;
import static SoftSquared.PeopleOfDelivery.utils.ValidationRegex.isRegexEmail;

@Slf4j
@Controller
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserController(UserService userService, UserProvider userProvider,
                          JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
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
           log.info("11");
           GetUserInfo getUserInfo = jwtService.getUserInfo();

           log.info("전체 회원 조회 " + String.valueOf(getUserInfo.getRole()));
           
           if(!getUserInfo.getRole().equals(100))
               throw new BaseException(FAILED_TO_GET_AUTHENTICATION);

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
            @PathVariable("userId") Long userId) throws BaseException {

        try{
            GetUserInfo getUserInfo = jwtService.getUserInfo();

            log.info("회원 프로필 조회 " + String.valueOf(getUserInfo.getRole()));
                    
            if(!getUserInfo.getRole().equals(1))
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);

            if(userId <= 0) {
                return new BaseResponse<>(EMPTY_USERID);
            }

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
    @RequestMapping(value = "/users", method = RequestMethod.POST)
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
     * 로그인
     */

    /**
     * 유저 프로필 수정
     * 사진, 위치
     */
    @ResponseBody
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PATCH)
    @ApiOperation(value = "프로필 수정(회원 기능)", notes = "프로필 수정")
    public BaseResponse<UpdateUserRes> UpdateUser(
            @PathVariable Long userId,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "confirmPassword") String confirmPassword,
            @RequestParam(name = "updatePassword",required = false,defaultValue = "empty") String updatePassword,
            @RequestParam(name = "location") String location,
            @RequestParam(value = "imageFile",required = false) MultipartFile imageFile )
            throws BaseException{

        // 1. Body Parameter Validation
        if (password == null || password.length() == 0) {
            return new BaseResponse<>(EMPTY_PASSWORD);
        }
        if (confirmPassword == null || confirmPassword.length() == 0) {
            return new BaseResponse<>(EMPTY_CONFIRM_PASSWORD);
        }
        if (!password.equals(confirmPassword)) {
            return new BaseResponse<>(DO_NOT_MATCH_PASSWORD);
        }

        // 2. Post UserInfo
        try {
            UpdateUserRes updateUserRes = userService.updateUser(
                    userId,updatePassword, location, imageFile
            );
            return new BaseResponse<>(SUCCESS_UPDATE_USER, updateUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "회원 삭제(회원 기능)", notes = "회원 삭제")
    public BaseResponse<DeleteUserRes> DeleteUser(
            @PathVariable Long userId) throws BaseException{

        // 2. Post UserInfo
        try {
            DeleteUserRes deleteUserRes = userService.deleteUser(userId);
            return new BaseResponse<>(SUCCESS_DELETE_USER, deleteUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

















