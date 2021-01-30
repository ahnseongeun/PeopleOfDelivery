package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.order.DeleteOrderRes;
import SoftSquared.PeopleOfDelivery.domain.order.GetOrderDetailRes;
import SoftSquared.PeopleOfDelivery.domain.order.GetOrderRes;
import SoftSquared.PeopleOfDelivery.domain.order.PostOrderRes;
import SoftSquared.PeopleOfDelivery.provider.OrderProvider;
import SoftSquared.PeopleOfDelivery.service.OrderService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Slf4j
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
    public BaseResponse<List<GetOrderRes>> getOrders(
            Authentication authentication
    ){

        List<GetOrderRes> getOrderResList;

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);

            log.info("전체 주문 내역 조회");

            if(role != 100) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

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
            @PathVariable Long orderId,
            Authentication authentication){

        GetOrderDetailRes getOrderDetailResList;

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);

            log.info("주문 내역 상세조회");

            if(role != 1) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

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
    @RequestMapping(value = "/orders/me",method = RequestMethod.GET)
    @ApiOperation(value = "회원 주문내역 조회", notes = "회원 주문내역 조회")
    public BaseResponse<List<GetOrderRes>> getUserOrders(
            Authentication authentication){

        List<GetOrderRes> getOrderResList;

        try{
            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            long userId = claims.get("userId",Integer.class);

            log.info("회원 주문내역 조회");

            if(role != 1) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }


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
            Authentication authentication,
            @RequestParam(name = "requestContent") String requestContent,
            //@RequestParam(name = "userId") Long userId,
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

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            long userId = claims.get("userId",Integer.class);

            log.info("주문하기");

            if(role != 1) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

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
            @PathVariable Long orderId,
            Authentication authentication){

        DeleteOrderRes deleteOrderRes;

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);

            log.info("회원 주문 삭제하기");

            if(role != 1) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            deleteOrderRes = orderService.DeleteOrder(orderId);
            return new BaseResponse<>(SUCCESS_DELETE_ORDER, deleteOrderRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
