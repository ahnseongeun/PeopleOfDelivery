package SoftSquared.PeopleOfDelivery.provider;


import SoftSquared.PeopleOfDelivery.config.BaseException;
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

    /*
        private final String storeName;
    private final String menuName;
    private final String menuCount;
    private final String totalPrice;
    private final Date orderDate;
     */

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
/*
    private final String storeName;
    private final String menuName;
    private final String menuCount;
    private final String totalPrice;
    private final Date orderDate;
 */
        if(orderList.size() == 0) {
            throw new BaseException(EMPTY_ORDERLIST);
        }

        return orderList.stream()
                .map(order -> GetOrderRes.builder()
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
