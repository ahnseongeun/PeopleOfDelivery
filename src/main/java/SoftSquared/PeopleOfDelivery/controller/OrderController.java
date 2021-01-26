package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.menu.PostMenuReq;
import SoftSquared.PeopleOfDelivery.domain.order.PostOrderRes;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.PostShoppingBasketRes;
import SoftSquared.PeopleOfDelivery.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.SUCCESS_READ_SHOPPING_BASKET;

public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    /**
     * 전체 주문 조회
     */

    /**
     * 회원 주문내역 조회
     */

    /**
     * 주문 추가
     */
    @ResponseBody
    @RequestMapping(value = "/orders",method = RequestMethod.POST)
    @ApiOperation(value = "주문하기 (회원 기능)", notes = "주문하기")
    public BaseResponse<PostOrderRes> createOrder(
            @RequestParam(name = "requestContent") String requestContent,
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "storeId") Long storeId,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "totalPrice") Integer totalPrice,
            @RequestParam(name = "BasketId") List<Long> BasketId,
            @RequestParam(name = "pgName") String pgName,
            @RequestParam(name = "pgType") String pgType,
            @RequestParam(name = "pgData") String pgData
    ) throws BaseException {

        PostOrderRes postOrderRes;

        try{
            postOrderRes = orderService.createOrder(
                    requestContent,userId,address,storeId,totalPrice,BasketId,pgName,pgType,pgData);
            return new BaseResponse<>(SUCCESS_READ_SHOPPING_BASKET, postOrderRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
