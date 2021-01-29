package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.user.PostLoginRes;
import SoftSquared.PeopleOfDelivery.provider.UserProvider;
import SoftSquared.PeopleOfDelivery.service.UserService;
import SoftSquared.PeopleOfDelivery.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;
import static SoftSquared.PeopleOfDelivery.utils.ValidationRegex.isRegexEmail;

@Controller
@RequestMapping(value = "/api")
public class LoginController {

    private final JwtService jwtService;
    private final UserProvider userProvider;
    private final UserService userService;

    @Autowired
    public LoginController(JwtService jwtService,
                           UserProvider userProvider,
                           UserService userService) {
        this.jwtService = jwtService;
        this.userProvider = userProvider;
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "로그인 하기", notes = "로그인 하기")
    public BaseResponse<PostLoginRes> login(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) throws BaseException{
        // 1. Body Parameter Validation
        if (email == null || email.length() == 0) {
            return new BaseResponse<>(EMPTY_EMAIL);
        } else if (!isRegexEmail(email)) {
            return new BaseResponse<>(INVALID_EMAIL);
        } else if (password == null || password.length() == 0) {
            return new BaseResponse<>(EMPTY_PASSWORD);
        }

        // 2. Login
        try {
            PostLoginRes postLoginRes = userService.login(email,password);
            return new BaseResponse<>(SUCCESS_LOGIN, postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


//    /**
//     * JWT 검증 API
//     * [GET] /users/jwt
//     * @return BaseResponse<Void>
//     */
//    @GetMapping("/jwt")
//    public BaseResponse<Void> jwt() {
//        try {
//            GetUserInfo getUserInfo = jwtService.getUserInfo();
//            userInfoProvider.retrieveUserInfo(userId);
//            return new BaseResponse<>(SUCCESS_JWT);
//        } catch (BaseException | BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
}
