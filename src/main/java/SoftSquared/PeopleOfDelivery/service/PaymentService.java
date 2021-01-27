package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.payment.DeletePaymentRes;
import SoftSquared.PeopleOfDelivery.domain.payment.GetPaymentRes;
import SoftSquared.PeopleOfDelivery.domain.payment.Payment;
import SoftSquared.PeopleOfDelivery.domain.payment.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * 결제 수정
     * @param paymentId
     * @param pgName
     * @param pgType
     * @param pgData
     * @param pgPrice
     * @return
     * @throws BaseException
     */
    public GetPaymentRes updatePayment(Long paymentId,
                                       String pgName,
                                       String pgType,
                                       String pgData,
                                       Integer pgPrice) throws BaseException {


        Payment payment = paymentRepository.findByIdAndStatus(paymentId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_PAYMENT));

        payment.setPgName(pgName)
                .setPgType(pgType)
                .setPgData(pgData)
                .setPgPrice(pgPrice);
        try{
            payment = paymentRepository.save(payment);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_UPDATE_PAYMENT);
        }

        return GetPaymentRes.builder()
                .id(payment.getId())
                .pgType(payment.getPgType())
                .pgName(payment.getPgName())
                .pgData(payment.getPgData())
                .pgPrice(payment.getPgPrice())
                .orderId(payment.getOrders().getId())
                .build();

    }

    /**
     * 결재 내역 삭제
     * @param paymentId
     * @return
     * @throws BaseException
     */
    public DeletePaymentRes deletePayment(Long paymentId) throws BaseException {
        Payment payment = paymentRepository.findByIdAndStatus(paymentId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_PAYMENT));

        payment.setStatus(2);

        try{
            payment = paymentRepository.save(payment);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_DELETE_PAYMENT);
        }

        return DeletePaymentRes.builder()
                .paymentId(payment.getId())
                .status(payment.getStatus())
                .build();
    }
}
