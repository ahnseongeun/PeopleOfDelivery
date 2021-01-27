package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.menu.PostMenuRes;
import SoftSquared.PeopleOfDelivery.domain.payment.GetPaymentRes;
import SoftSquared.PeopleOfDelivery.domain.review.GetReviewRes;
import SoftSquared.PeopleOfDelivery.domain.review.PostReviewRes;
import SoftSquared.PeopleOfDelivery.provider.ReviewProvider;
import SoftSquared.PeopleOfDelivery.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Controller
@RequestMapping(value = "/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewProvider reviewProvider;

    @Autowired
    public ReviewController(ReviewService reviewService,
                            ReviewProvider reviewProvider) {
        this.reviewService = reviewService;
        this.reviewProvider = reviewProvider;
    }

    /**
     * 리뷰 추가
     * @param content
     * @param startCount
     * @param orderId
     * @param userId
     * @param storeId
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/reviews",method = RequestMethod.POST)
    @ApiOperation(value = "리뷰 추가하기", notes = "리뷰 추가하기")
    public BaseResponse<PostReviewRes> createReview(
            @RequestParam(value = "content") String content,
            @RequestParam(value = "starCount") Integer startCount,
            @RequestParam(value = "orderId") Long orderId,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "storeId") Long storeId) throws IOException {

        try{
            PostReviewRes postReviewRes = reviewService.createReview(
                    content,startCount,orderId,userId,storeId
            );
            return new BaseResponse<>(SUCCESS_POST_REVIEW, postReviewRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/reviews/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "내 리뷰 조회", notes = "내 리뷰 조회")
    public BaseResponse<List<GetReviewRes>> getPayment(
            @PathVariable("userId") Long userId) throws BaseException{

        GetPaymentRes getPaymentRes;
        try{
            getPaymentRes = paymentProvider.retrievePayment(orderId);
            return new BaseResponse<>(SUCCESS_READ_PAYMENT, getPaymentRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
//    내 리뷰 조회
//    전체 리뷰 조회
//    가게 리뷰 조회
//    리뷰 수정
//    리뷰 삭제

}
