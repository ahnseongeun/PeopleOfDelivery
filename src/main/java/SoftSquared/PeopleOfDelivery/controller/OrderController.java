package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.order.DeleteOrderRes;
import SoftSquared.PeopleOfDelivery.domain.order.GetOrderDetailRes;
import SoftSquared.PeopleOfDelivery.domain.order.GetOrderRes;
import SoftSquared.PeopleOfDelivery.domain.order.PostOrderRes;
import SoftSquared.PeopleOfDelivery.provider.OrderProvider;
import SoftSquared.PeopleOfDelivery.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

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
    @ResponseBody
    @RequestMapping(value = "/orders",method = RequestMethod.GET)
    @ApiOperation(value = "전체 주문내역 조회", notes = "전체 주문내역 조회")
    public BaseResponse<List<GetOrderRes>> getOrders(){

        List<GetOrderRes> getOrderResList;

        try{
            getOrderResList = orderProvider.retrieveOrders();
            return new BaseResponse<>(SUCCESS_READ_ORDERLIST, getOrderResList);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 회원 주문 상세 조회
     */
    @ResponseBody
    @RequestMapping(value = "/order-detail/{orderId}",method = RequestMethod.GET)
    @ApiOperation(value = "주문 내역 상세조회", notes = "주문 내역 상세 조회")
    public BaseResponse<GetOrderDetailRes> getOrderDetail(
            @PathVariable Long orderId){

        GetOrderDetailRes getOrderDetailResList;

        try{
            getOrderDetailResList = orderProvider.retrieveOrderDetail(orderId);
            return new BaseResponse<>(SUCCESS_READ_ORDER_DETAIL, getOrderDetailResList);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 주문 내역 조회
     */
    @ResponseBody
    @RequestMapping(value = "/orders/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "회원 주문내역 조회", notes = "회원 주문내역 조회")
    public BaseResponse<List<GetOrderRes>> getUserOrders(
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
            @RequestParam(name = "orderPrice") Integer orderPrice,
            @RequestParam(name = "DeliveryFee") Integer deliveryFee,
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
                    requestContent,userId,address,storeId,orderPrice,deliveryFee,BasketId,pgName,pgType,pgData,couponType);
            return new BaseResponse<>(SUCCESS_READ_ORDER, postOrderRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 주문 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/orders/{orderId}",method = RequestMethod.DELETE)
    @ApiOperation(value = "회원 주문 삭제하기", notes = "회원 주문 삭제하기")
    public BaseResponse<DeleteOrderRes> DeleteOrder(
            @PathVariable Long orderId){

        DeleteOrderRes deleteOrderRes;

        try{
            deleteOrderRes = orderService.DeleteOrder(orderId);
            return new BaseResponse<>(SUCCESS_DELETE_ORDER, deleteOrderRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
