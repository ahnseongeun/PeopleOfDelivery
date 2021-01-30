package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.payment.DeletePaymentRes;
import SoftSquared.PeopleOfDelivery.domain.payment.GetPaymentRes;
import SoftSquared.PeopleOfDelivery.provider.PaymentProvider;
import SoftSquared.PeopleOfDelivery.service.PaymentService;
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
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentProvider paymentProvider;

    @Autowired
    public PaymentController(PaymentService paymentService,
                             PaymentProvider paymentProvider){

        this.paymentService = paymentService;
        this.paymentProvider = paymentProvider;
    }

    /**
     * 결제 내역 전체 조회
     */
    @ResponseBody
    @RequestMapping(value = "/payments",method = RequestMethod.GET)
    @ApiOperation(value = "결재 내역 조회 (관리자 기능)", notes = "결재 내역 조회")
    public BaseResponse<List<GetPaymentRes>> getPayments(
            Authentication authentication
        ) throws BaseException{

        List<GetPaymentRes> getPaymentResList;

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);

            log.info("결재 내역 전체 조회");

            if(role != 100) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            getPaymentResList = paymentProvider.retrievePaymentList();
            return new BaseResponse<>(SUCCESS_READ_PAYMENTS, getPaymentResList);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     *  주문번호를 이용한 결제 내역 조회
     */
    @ResponseBody
    @RequestMapping(value = "/payments/{orderId}",method = RequestMethod.GET)
    @ApiOperation(value = "결재 내역 주문번호 조회 (관리자 기능)", notes = "결재 내역 주문번호 조회")
    public BaseResponse<GetPaymentRes> getPayment(
            @PathVariable("orderId") Long orderId,
            Authentication authentication) throws BaseException{
        GetPaymentRes getPaymentRes;
        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);

            log.info("결재 내역 주문 번호 조회");

            if(role != 100) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            getPaymentRes = paymentProvider.retrievePayment(orderId);
            return new BaseResponse<>(SUCCESS_READ_PAYMENT, getPaymentRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 결재내역 수정
     */
    @ResponseBody
    @RequestMapping(value = "/payments/{paymentId}",method = RequestMethod.PATCH)
    @ApiOperation(value = "결재 내역 수정", notes = "결재 내역 수정")
    public BaseResponse<GetPaymentRes> updatePayment(
            @PathVariable Long paymentId,
            @RequestParam(name = "pgName") String pgName,
            @RequestParam(name = "pgType") String pgType,
            @RequestParam(name = "pgData") String pgData,
            @RequestParam(name = "pgPrice") Integer pgPrice,
            Authentication authentication
    ) throws BaseException {

        GetPaymentRes getPaymentRes;

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);

            log.info("결재 내역 수정");

            if(role != 100) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            getPaymentRes = paymentService.updatePayment(paymentId,pgName,pgType,pgData,pgPrice);
            return new BaseResponse<>(SUCCESS_UPDATE_PAYMENT, getPaymentRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 결제 내역 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/payments/{paymentId}",method = RequestMethod.DELETE)
    @ApiOperation(value = "결재 내역 삭제", notes = "결재 내역 삭제")
    public BaseResponse<DeletePaymentRes> deletePayment(
            @PathVariable Long paymentId,
            Authentication authentication
    ) throws BaseException {

        DeletePaymentRes deletePaymentRes;

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);

            log.info("결재 내역 삭제");

            if(role != 100) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }
            
            
            deletePaymentRes = paymentService.deletePayment(paymentId);
            return new BaseResponse<>(SUCCESS_DELETE_PAYMENT, deletePaymentRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
