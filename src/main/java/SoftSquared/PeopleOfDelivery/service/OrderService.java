package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.order.OrdersRepository;
import SoftSquared.PeopleOfDelivery.domain.order.PostOrderRes;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.FAILED_TO_GET_STORES;
import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.FAILED_TO_GET_USER;

@Service
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public OrderService(OrdersRepository ordersRepository,
                        UserRepository userRepository,
                        StoreRepository storeRepository){
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }


    public PostOrderRes createOrder(String requestContent, Long userId,
                                    String address, Long storeId, List<Long> basketId) throws BaseException {

        User user = userRepository.findByIdAndStatus(userId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_USER));

        Store store = storeRepository.findByIdAndStatus(storeId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_STORES));

        //사용자가 주소를 지정하지 않으면 기존 주소로 대체
        address = Optional.ofNullable(address).orElse(user.getLocation());

        //총 결제 금액
/*
        결제 완료
         */
        Orders orders = Orders.builder()
                .requestContent(requestContent)
                .user(user)
                .address(address)
                .status(2) //처리중
                .build();
        return null;
    }
}
