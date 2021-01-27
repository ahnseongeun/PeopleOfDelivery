package SoftSquared.PeopleOfDelivery.controller;


import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.*;
import SoftSquared.PeopleOfDelivery.provider.ShoppingBasketProvider;
import SoftSquared.PeopleOfDelivery.service.ShoppingBasketService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Controller
@RequestMapping(value = "/api")
public class ShoppingBasketController {

    private final ShoppingBasketService shoppingBasketService;
    private final ShoppingBasketProvider shoppingBasketProvider;

    @Autowired
    public ShoppingBasketController(ShoppingBasketService shoppingBasketService
            , ShoppingBasketProvider shoppingBasketProvider){
        this.shoppingBasketService = shoppingBasketService;
        this.shoppingBasketProvider = shoppingBasketProvider;
    }

    /**
     * 장바구니 전체 조회
     */

    /**
     * 회원 장바구니 조회
     */
    @ResponseBody
    @RequestMapping(value = "/baskets/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "회원 장바구니 조회 (회원 기능)", notes = "회원 장바구니 조회")
    public BaseResponse<GetShoppingBasketRes> getBasket(
            @PathVariable("userId") Long userId) throws BaseException{
        GetShoppingBasketRes getShoppingBasketRes;
        try{
            getShoppingBasketRes = shoppingBasketProvider.retrieveShoppingBasket(userId);
            return new BaseResponse<>(SUCCESS_READ_SHOPPING_BASKET, getShoppingBasketRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 장바구니 총금액 조회
     */
    @ResponseBody
    @RequestMapping(value = "/baskets/total-price/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "회원 장바구니 총금액 조회 (회원 기능)", notes = "회원 장바구니 총금액 조회")
    public BaseResponse<GetTotalPriceRes> getBasketTotalPrice(
            @PathVariable("userId") Long userId,
            @RequestParam(name = "couponType",required = false,defaultValue = "0") Integer couponType
            //1은 1000원 , 2는 3000원, 3은 5000원
    ) throws BaseException{
        GetTotalPriceRes getTotalPriceRes;
        try{
            getTotalPriceRes = shoppingBasketProvider.retrieveTotalPrice(userId,couponType);
            return new BaseResponse<>(SUCCESS_READ_SHOPPING_BASKET_TOTAL_PRICE, getTotalPriceRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 장바구니 추가
     */
    @ResponseBody
    @RequestMapping(value = "/baskets",method = RequestMethod.POST)
    @ApiOperation(value = "장바구니 메뉴 추가 (회원 기능)", notes = "장바구니 메뉴 추가")
    public BaseResponse<PostShoppingBasketRes> createBasket(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "menuId") Long menuId ) throws BaseException {
        PostShoppingBasketRes postShoppingBasketRes;

        try{
            postShoppingBasketRes = shoppingBasketService.createShoppingBasket(userId, menuId);
            return new BaseResponse<>(SUCCESS_READ_SHOPPING_BASKET, postShoppingBasketRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 장바구니 수정
     */
    @ResponseBody
    @RequestMapping(value = "/baskets/{basketId}",method = RequestMethod.PATCH)
    @ApiOperation(value = "장바구니 메뉴 수정 (회원 기능)", notes = "장바구니 메뉴 수정")
    public BaseResponse<UpdateShoppingBasketRes> updateBasket(
            @PathVariable Long basketId,
            @RequestParam(value = "operationCheck",required = false,defaultValue = "true") boolean operationCheck
            ) throws BaseException {

        UpdateShoppingBasketRes updateShoppingBasketRes;

        try{
            updateShoppingBasketRes = shoppingBasketService.updateShoppingBasket(basketId, operationCheck);
            return new BaseResponse<>(SUCCESS_UPDATE_SHOPPING_BASKET, updateShoppingBasketRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 장바구니 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/baskets/{basketId}",method = RequestMethod.DELETE)
    @ApiOperation(value = "장바구니 메뉴 삭제 (회원 기능)", notes = "장바구니 메뉴 삭제")
    public BaseResponse<DeleteShoppingBasketRes> deleteBasket(
            @PathVariable Long basketId
    ) throws BaseException {

        DeleteShoppingBasketRes deleteShoppingBasketRes;

        try{
            deleteShoppingBasketRes = shoppingBasketService.deleteShoppingBasket(basketId);
            return new BaseResponse<>(SUCCESS_DELETE_SHOPPING_BASKET, deleteShoppingBasketRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    
}
