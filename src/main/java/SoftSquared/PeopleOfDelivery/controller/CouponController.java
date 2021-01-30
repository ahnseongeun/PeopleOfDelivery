package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.coupon.GetCouponRes;
import SoftSquared.PeopleOfDelivery.domain.menu.GetMenuRes;
import SoftSquared.PeopleOfDelivery.domain.menu.GetMenusRes;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.menu.PostMenuRes;
import SoftSquared.PeopleOfDelivery.provider.CouponProvider;
import SoftSquared.PeopleOfDelivery.provider.MenuProvider;
import SoftSquared.PeopleOfDelivery.service.CouponService;
import SoftSquared.PeopleOfDelivery.service.MenuService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Slf4j
@Controller
@RequestMapping(value = "/api")
public class CouponController {

    private final CouponService couponService;
    private final CouponProvider couponProvider;

    @Autowired
    public CouponController(CouponService couponService, CouponProvider couponProvider){
        this.couponService = couponService;
        this.couponProvider = couponProvider;
    }

    /**
     * 쿠폰 전체 조회
     */
    @ResponseBody
    @RequestMapping(value = "/coupons",method = RequestMethod.GET)
    @ApiOperation(value = "전체 쿠폰 조회 (관리자 기능)", notes = "전체 쿠폰 목록 불러오기")
    public BaseResponse<List<GetCouponRes>> getCoupons(
            Authentication authentication
    ){

        List<GetCouponRes> getCouponResList;

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            log.info("전체 쿠폰 조회 " + String.valueOf(role));

            if(role!=100) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }


            getCouponResList = couponProvider.retrieveCouponList();
            return new BaseResponse<>(SUCCESS_READ_COUPON, getCouponResList);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 내 쿠폰 조회
     * @throws BaseException

     */
    @ResponseBody
    @RequestMapping(value = "/coupons/me",method = RequestMethod.GET)
    @ApiOperation(value = "내 쿠폰 조회 (회원)" , notes = "내 쿠폰 목록 불러오기")
    public BaseResponse<GetCouponRes> getCoupon(
            Authentication authentication
            ){

        GetCouponRes getCouponRes;

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }

            Claims claims= (Claims) authentication.getPrincipal();
            long userId = claims.get("userId", Long.class);
            log.info("내 쿠폰 조회 " + userId);

            getCouponRes = couponProvider.retrieveCoupon(userId);
            return new BaseResponse<>(SUCCESS_READ_COUPON, getCouponRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 쿠폰 수정하기
     */
    @ResponseBody
    @RequestMapping(value = "/coupons/{userId}",method = RequestMethod.PATCH)
    @ApiOperation(value = " 쿠폰 수정하기 (관리자 권한)", notes = " 쿠폰 수정하기")
    public BaseResponse<GetCouponRes> updateCoupon(
            @PathVariable Long userId,
            Authentication authentication,
            //0은 plus,1 은 minus
            @RequestParam(value = "plusOrMinus",defaultValue = "true") boolean operationCheck,
            @RequestParam(value = "coupon1000Count",required = false,defaultValue = "0") Integer coupon1000Count,
            @RequestParam(value = "coupon3000Count",required = false,defaultValue = "0") Integer coupon3000Count,
            @RequestParam(value = "coupon5000Count",required = false,defaultValue = "0") Integer coupon5000Count) throws BaseException{

        GetCouponRes getCouponRes;

        try{
            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            log.info("쿠폰 수정 하기");

            if(role!=100) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }


            getCouponRes = couponService.updateCoupon(
                    userId,operationCheck, coupon1000Count, coupon3000Count,coupon5000Count
            );
            return new BaseResponse<>(SUCCESS_PATCH_COUPON, getCouponRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 쿠폰 삭제하기 (초기화)
     */
    @ResponseBody
    @RequestMapping(value = "/coupons/{userId}",method = RequestMethod.DELETE)
    @ApiOperation(value = " 쿠폰 초기화 하기 (관리자 권한) ", notes = " 쿠폰 초기화")
    public BaseResponse<GetCouponRes> deleteCoupon(
            @PathVariable Long userId,
            Authentication authentication,
            //0은 plus,1 은 minus
            @RequestParam(value = "coupon1000Count",required = false,defaultValue = "true") boolean coupon1000Count,
            @RequestParam(value = "coupon3000Count",required = false,defaultValue = "true") boolean coupon3000Count,
            @RequestParam(value = "coupon5000Count",required = false,defaultValue = "true") boolean coupon5000Count) throws BaseException{

        GetCouponRes getCouponRes;

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            log.info("쿠폰 초기화 하기");

            if(role!=100) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }


            getCouponRes = couponService.deleteCoupon(
                    userId, coupon1000Count, coupon3000Count,coupon5000Count
            );
            return new BaseResponse<>(SUCCESS_DELETE_COUPON, getCouponRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
