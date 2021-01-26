package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.order.OrdersRepository;
import SoftSquared.PeopleOfDelivery.domain.order.PostOrderRes;
import SoftSquared.PeopleOfDelivery.domain.orderDetail.OrderDetail;
import SoftSquared.PeopleOfDelivery.domain.orderDetail.OrderDetailRepository;
import SoftSquared.PeopleOfDelivery.domain.payment.Payment;
import SoftSquared.PeopleOfDelivery.domain.payment.PaymentRepository;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasket;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasketRepository;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
@Slf4j
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ShoppingBasketRepository shoppingBasketRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public OrderService(OrdersRepository ordersRepository,
                        OrderDetailRepository orderDetailRepository,
                        UserRepository userRepository,
                        StoreRepository storeRepository,
                        ShoppingBasketRepository shoppingBasketRepository,
                        PaymentRepository paymentRepository){
        this.ordersRepository = ordersRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.paymentRepository = paymentRepository;
    }


    public PostOrderRes createOrder(String requestContent, Long userId,
                                    String address, Long storeId,
                                    Integer totalPrice,List<Long> basketId,
                                    String pgName , String pgType ,
                                    String pgData) throws BaseException {

        User user = userRepository.findByIdAndStatus(userId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_USER));

        Store store = storeRepository.findByIdAndStatus(storeId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_STORES));

        //사용자가 주소를 지정하지 않으면 기존 주소로 대체
        address = Optional.ofNullable(address).orElse(user.getLocation());

        Orders orders = Orders.builder()
                .requestContent(requestContent)
                .user(user)
                .address(address)
                .store(store)
                .status(1) //1 처리중 2 주문처리완료
                .build();

        Orders newOrder;
        try{
            newOrder = ordersRepository.save(orders);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_STORE);
        }

        //예외 처리를 어떻게 할까?
        List<Optional<OrderDetail>> orderDetailList =  basketId.stream()
                .map(aLong -> shoppingBasketRepository.findByIdAndStatus(aLong, 1)
                        .map(shoppingBasket -> OrderDetail.builder()
                                .menu(shoppingBasket.getMenu())
                                .menuCount(shoppingBasket.getMenuCount())
                                .user(shoppingBasket.getUser())
                                .status(1)
                                .build()))
                .collect(Collectors.toList());

        List<OrderDetail> newOrderDetailList = new LinkedList<>();
        
        for(Optional<OrderDetail> orderDetail: orderDetailList){
            newOrderDetailList.add(orderDetailRepository.save(
                    orderDetail.orElseThrow(() ->new BaseException(FAILED_TO_POST_ORDER_DETAIL))));
        }

        /*
        결제 과정
         */
        Payment payment = Payment.builder()
                .pgName(pgName)
                .pgType(pgType)
                .pgPrice(totalPrice)
                .pgData(pgData)
                .status(1) //1은 결재완료 //2가 삭제
                .orders(newOrder)
                .build();

        Payment postPayment;
        try{
            postPayment = paymentRepository.save(payment);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_PAYMENT);
        }

        /*
        결제가 완료되면 주문 테이블과 주문 상세 테이블 처리완료로 상태 업데이트 및 장바구니 삭제 처리
         */

        //주문 테이블 업데이트
        Orders updateOrder = ordersRepository.findByIdAndStatus(newOrder.getId(),1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_ORDER));

        updateOrder.setStatus(2);

        try{
            updateOrder = ordersRepository.save(updateOrder);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_UPDATE_ORDER);
        }

        //주문 상세 테이블 업데이트
        List<Long> updateOrderDetailList = new LinkedList<>();

        for(OrderDetail OrderDetail: newOrderDetailList){
            OrderDetail updateOrderDetail = orderDetailRepository
                    .findByIdAndStatus(OrderDetail.getId(),1)
                    .orElseThrow(() -> new BaseException(FAILED_TO_GET_ORDER_DETAIL));

            updateOrderDetail.setStatus(2);

            try{
                updateOrderDetailList.add(orderDetailRepository.save(updateOrderDetail).getId());
            }catch (Exception exception){
                throw new BaseException(FAILED_TO_UPDATE_ORDER_DETAIL);
            }
        }

        //장바구니 삭제
        for(Long id: basketId){
            ShoppingBasket updateBasket = shoppingBasketRepository
                    .findByIdAndStatus(id,1)
                    .orElseThrow(() -> new BaseException(FAILED_TO_GET_BASKET));

            updateBasket.setStatus(2);

            try{
                shoppingBasketRepository.save(updateBasket);
            }catch (Exception exception){
                throw new BaseException(FAILED_TO_DELETE_BASKET);
            }
        }
        log.info("장바구니 삭제에 완료했습니다.");



        return PostOrderRes.builder()
                .orderId(updateOrder.getId())
                .orderDetailIdList(updateOrderDetailList)
                .paymentId(postPayment.getId())
                .build();
    }
}