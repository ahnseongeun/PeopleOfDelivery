package SoftSquared.PeopleOfDelivery.controller;


import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.GetShoppingBasketRes;
import SoftSquared.PeopleOfDelivery.provider.ShoppingBasketProvider;
import SoftSquared.PeopleOfDelivery.service.ShoppingBasketService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.SUCCESS_READ_MENU;
import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.SUCCESS_READ_SHOPPING_BASKET;

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
    @RequestMapping(value = "/baskets/{user-id}",method = RequestMethod.GET)
    @ApiOperation(value = "회원 장바구니 조회 (회원 기능)", notes = "회원 장바구니 조회")
    public BaseResponse<GetShoppingBasketRes> getBasket(
            @PathVariable("user-id") Long userId){
        GetShoppingBasketRes getShoppingBasketRes;
        try{
            getShoppingBasketRes = shoppingBasketProvider.retrieveShoppingBasket(userId);
            return new BaseResponse<>(SUCCESS_READ_SHOPPING_BASKET, getShoppingBasketRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 장바구니 추가
     */
}
