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
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

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
    public BaseResponse<List<GetCouponRes>> getCoupons(){

        List<GetCouponRes> getCouponResList;

        try{
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
    @RequestMapping(value = "/coupons/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "내 쿠폰 조회" , notes = "내 쿠폰 목록 불러오기")
    public BaseResponse<GetCouponRes> getCoupon(
            @PathVariable Long userId){

        GetCouponRes getCouponRes;

        try{
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
    @ApiOperation(value = " 쿠폰 수정하기 ", notes = " 쿠폰 수정하기")
    public BaseResponse<GetCouponRes> updateCoupon(
            @PathVariable Long userId,
            @RequestParam(value = "coupon1000Count",required = false,defaultValue = "false") boolean coupon1000Count,
            @RequestParam(value = "coupon3000Count",required = false,defaultValue = "false") boolean coupon3000Count,
            @RequestParam(value = "coupon5000Count",required = false,defaultValue = "false") boolean coupon5000Count) throws BaseException{

        GetCouponRes getCouponRes;

        try{
            getCouponRes = couponService.updateCoupon(
                    userId, coupon1000Count, coupon3000Count,coupon5000Count
            );
            return new BaseResponse<>(SUCCESS_PATCH_COUPON, getCouponRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
