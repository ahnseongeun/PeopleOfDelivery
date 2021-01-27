package SoftSquared.PeopleOfDelivery.provider;


import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.order.GetOrderDetailRes;
import SoftSquared.PeopleOfDelivery.domain.order.GetOrderRes;
import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.order.OrdersRepository;
import SoftSquared.PeopleOfDelivery.domain.orderDetail.OrderDetailMenu;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.List;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
@Slf4j
public class OrderProvider {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderProvider(OrdersRepository ordersRepository,
                         UserRepository userRepository){

        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
    }

    /**
     * 전체 주문 조회
     * @return
     */
    public List<GetOrderRes> retrieveOrders() throws BaseException {

        List<Orders> orderList;

        try{
            //삭제를 제외 하고 조회
            orderList = ordersRepository.findByStatus(2);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_GET_ORDERS);
        }
        return getGetOrderRes(orderList);

    }

    /**
     * 내 주문 내역 조회
     * @param userId
     * @return
     */
    public List<GetOrderRes> retrieveOrderList(Long userId) throws BaseException{

        User user = userRepository.findByIdAndStatus(userId,1).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_USER));

        List<Orders> orderList = ordersRepository.findByUserAndStatus(user,2);
        log.info(String.valueOf(orderList.size()));

        if(orderList.size() == 0) {
            throw new BaseException(EMPTY_ORDERLIST);
        }

        return getGetOrderRes(orderList);
    }

    /**
     * 주문 상세 조회
     * @return
     */
    public GetOrderDetailRes retrieveOrderDetail(Long orderId) throws BaseException {

        Orders orders = ordersRepository.findByIdAndStatus(orderId,2)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_ORDER));

        return GetOrderDetailRes.builder()
                .orderId(orders.getId())
                .address(orders.getAddress())
                .storeId(orders.getStore().getId())
                .userId(orders.getUser().getId())
                .orderDetailMenuList(orders.getOrderDetails().stream().map(orderDetail ->
                        OrderDetailMenu.builder()
                                .menuId(orderDetail.getMenu().getId())
                                .menuCount(orderDetail.getMenuCount())
                                .build())
                        .collect(Collectors.toList()))
                .orderPrice(orders.getOrderPrice())
                .deliveryFee(orders.getDeliveryFee())
                .payType(orders.getPayment().getPgType())
                .requestContent(orders.getRequestContent())
                .orderDate(orders.getCreatedTime())
                .deliveryStatus(1)
                .build();
    }

    private List<GetOrderRes> getGetOrderRes(List<Orders> orderList) {
        return orderList.stream()
                .map(order -> GetOrderRes.builder()
                        .orderId(order.getId())
                        .storeName(order.getStore().getName())
                        .orderDetailMenuList(order.getOrderDetails().stream().map(orderDetail ->
                                OrderDetailMenu.builder()
                                        .menuId(orderDetail.getMenu().getId())
                                        .menuCount(orderDetail.getMenuCount())
                                        .build())
                                .collect(Collectors.toList()))
                        .orderDate(order.getCreatedTime())
                        .totalPrice(order.getPayment().getPgPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
