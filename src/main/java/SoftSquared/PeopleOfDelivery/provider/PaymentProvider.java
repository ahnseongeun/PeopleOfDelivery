package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.order.OrdersRepository;
import SoftSquared.PeopleOfDelivery.domain.payment.GetPaymentRes;
import SoftSquared.PeopleOfDelivery.domain.payment.Payment;
import SoftSquared.PeopleOfDelivery.domain.payment.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
@Slf4j
public class PaymentProvider {

    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;

    @Autowired
    public PaymentProvider(PaymentRepository paymentRepository,
                           OrdersRepository ordersRepository){

        this.paymentRepository = paymentRepository;
        this.ordersRepository = ordersRepository;
    }

    /**
     * 결제 내역 조회
     * @return
     */
    public List<GetPaymentRes> retrievePaymentList() throws BaseException {


        List<Payment> paymentList = paymentRepository.findByStatus(1);

        if(paymentList.size() == 0) {

            throw new BaseException(EMPTY_PAYMENT);

        }

        return paymentList.stream()
                .map(payment -> GetPaymentRes.builder()
                        .id(payment.getId())
                        .pgType(payment.getPgType())
                        .pgName(payment.getPgName())
                        .pgData(payment.getPgData())
                        .pgPrice(payment.getPgPrice())
                        .orderId(payment.getOrders().getId())
                        .build())
                .collect(Collectors.toList());


    }

    /**
     * 결재 내역 단일 조회
     * @param orderId
     * @return
     * @throws BaseException
     */
    public GetPaymentRes retrievePayment(Long orderId) throws BaseException{

        Orders orders = ordersRepository.findByIdAndStatus(orderId,2)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_ORDER));

        Payment payment = paymentRepository.findByOrdersAndStatus(orders,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_PAYMENT));

        return GetPaymentRes.builder()
                .id(payment.getId())
                .pgType(payment.getPgType())
                .pgName(payment.getPgName())
                .pgData(payment.getPgData())
                .pgPrice(payment.getPgPrice())
                .orderId(payment.getOrders().getId())
                .build();
    }
}
