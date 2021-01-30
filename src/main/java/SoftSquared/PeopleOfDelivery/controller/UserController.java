package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.user.*;
import SoftSquared.PeopleOfDelivery.provider.UserProvider;
import SoftSquared.PeopleOfDelivery.service.UserService;
import SoftSquared.PeopleOfDelivery.utils.JwtService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
            Authentication authentication,
            @RequestParam(value = "name",required = false) String name) {
       try{

           if(authentication == null){
               throw new BaseException(EMPTY_AUTHENTICATION);
           }
           Claims claims= (Claims) authentication.getPrincipal();
           Integer role = claims.get("role", Integer.class);

           log.info("전체 회원 조회 " + String.valueOf(role));
           
           if(!role.equals(100))
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
    @RequestMapping(value = "/users/me",method = RequestMethod.GET)
    @ApiOperation(value = "내 프로필 조회 (전체 기능)", notes = "내 프로필 조회")
    public BaseResponse<GetUserRes> getUser(
            //@PathVariable("userId") Long userId,
            Authentication authentication) throws BaseException {

        try{
            //GetUserInfo getUserInfo = jwtService.getUserInfo();

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            long userId = claims.get("userId",Integer.class);

            log.info("회원 프로필 조회 " + String.valueOf(role));
                    
            if(!(role == 1 || role == 50 || role== 100)) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

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
    @ApiOperation(value = "회원 가입 ", notes = "회원 가입")
    public BaseResponse<PostUserRes> CreateUser(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "confirmPassword") String confirmPassword,
            @RequestParam(name = "phoneNumber") String phoneNumber,
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
    @RequestMapping(value = "/users/me", method = RequestMethod.PATCH)
    @ApiOperation(value = "내 프로필 수정(전체 기능)", notes = "내 프로필 수정")
    public BaseResponse<UpdateUserRes> UpdateUser(
            //@PathVariable Long userId,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "confirmPassword") String confirmPassword,
            @RequestParam(name = "updatePassword",required = false,defaultValue = "empty") String updatePassword,
            @RequestParam(name = "location",required = false) String location,
            @RequestParam(value = "imageFile",required = false) MultipartFile imageFile,
            Authentication authentication)
            throws BaseException{

        if(authentication == null){
            throw new BaseException(EMPTY_AUTHENTICATION);
        }
        Claims claims= (Claims) authentication.getPrincipal();
        int role = claims.get("role", Integer.class);
        long userId = claims.get("userId",Integer.class);

        log.info("회원 프로필 수정 " + String.valueOf(role));

        if(!(role == 1 || role == 50 || role== 100)) {
            throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
        }

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

        if(userId <= 0) {
            return new BaseResponse<>(EMPTY_USERID);
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
    @RequestMapping(value = "/users/delete", method = RequestMethod.PATCH)
    @ApiOperation(value = "내 정보 삭제(전체 기능)", notes = "내 정보 삭제")
    public BaseResponse<DeleteUserRes> DeleteUser(
            //@PathVariable Long userId,
            Authentication authentication) throws BaseException{

        if(authentication == null){
            throw new BaseException(EMPTY_AUTHENTICATION);
        }

        Claims claims= (Claims) authentication.getPrincipal();
        int role = claims.get("role", Integer.class);
        long userId = claims.get("userId",Integer.class);

        log.info("회원 프로필 삭제 " + String.valueOf(role));

        if(!(role == 1 || role == 50 || role== 100)) {
            throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
        }

        //JWT와 userId가 일치하는지??
        if(userId <= 0) {
            return new BaseResponse<>(EMPTY_USERID);
        }

        // 2. Post UserInfo
        try {
            DeleteUserRes deleteUserRes = userService.deleteUser(userId);
            return new BaseResponse<>(SUCCESS_DELETE_USER, deleteUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

















