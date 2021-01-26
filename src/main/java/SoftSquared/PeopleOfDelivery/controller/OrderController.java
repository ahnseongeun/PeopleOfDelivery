package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.order.GetOrderRes;
import SoftSquared.PeopleOfDelivery.domain.order.PostOrderRes;
import SoftSquared.PeopleOfDelivery.provider.OrderProvider;
import SoftSquared.PeopleOfDelivery.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.SUCCESS_READ_ORDER;
import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.SUCCESS_READ_ORDERLIST_BY_USER;

@Controller
@RequestMapping(value = "/api")
public class OrderController {

    private final OrderService orderService;
    private final OrderProvider orderProvider;

    @Autowired
    public OrderController(OrderService orderService,
                           OrderProvider orderProvider){
        this.orderService = orderService;
        this.orderProvider = orderProvider;
    }

    /**
     * 전체 주문 조회
     */

    /**
     * 회원 주문내역 조회
     */
    @ResponseBody
    @RequestMapping(value = "/orders/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "회원 주문내역 조회", notes = "회원 주문내역 조회")
    public BaseResponse<List<GetOrderRes>> getOrders(
            @PathVariable Long userId){

        List<GetOrderRes> getOrderResList;

        try{
            getOrderResList = orderProvider.retrieveOrderList(userId);
            return new BaseResponse<>(SUCCESS_READ_ORDERLIST_BY_USER, getOrderResList);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

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
            @RequestParam(name = "address",required = false) String address,
            @RequestParam(name = "totalPrice") Integer totalPrice,
            @RequestParam(name = "couponType") Integer couponType,
            @RequestParam(name = "BasketId") List<Long> BasketId,
            @RequestParam(name = "pgName") String pgName,
            @RequestParam(name = "pgType") String pgType,
            @RequestParam(name = "pgData") String pgData
            //coupon1000 1, coupon 3000 2, coupon 5000 3
    ) throws BaseException {

        PostOrderRes postOrderRes;

        try{
            postOrderRes = orderService.createOrder(
                    requestContent,userId,address,storeId,totalPrice,BasketId,pgName,pgType,pgData);
            return new BaseResponse<>(SUCCESS_READ_ORDER, postOrderRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
