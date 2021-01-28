package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.review.GetOpponentReviewRes;
import SoftSquared.PeopleOfDelivery.domain.review.GetReviewRes;
import SoftSquared.PeopleOfDelivery.domain.review.GetStoreReviewRes;
import SoftSquared.PeopleOfDelivery.domain.review.PostReviewRes;
import SoftSquared.PeopleOfDelivery.provider.ReviewProvider;
import SoftSquared.PeopleOfDelivery.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(value = "role") Integer role,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "starCount",required = false,defaultValue = "0") Integer startCount,
            @RequestParam(value = "orderId") Long orderId,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "storeId") Long storeId) throws IOException {


        try{
            PostReviewRes postReviewRes = reviewService.createReview(
                    role,content,startCount,orderId,userId,storeId
            );
            return new BaseResponse<>(SUCCESS_POST_REVIEW, postReviewRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user-reviews/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "내 리뷰 조회", notes = "내 리뷰 조회")
    public BaseResponse<GetReviewRes> getReview(
            @PathVariable("userId") Long userId) throws BaseException{

        GetReviewRes getReviewRes;
        try{
            getReviewRes = reviewProvider.retrieveReview(userId);
            return new BaseResponse<>(SUCCESS_READ_REVIEW, getReviewRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     *  userId가 있으면 가게 리뷰를 구할때 user Review를 구할 때 사용
     *  userId가 없다면 내 리뷰를 구할때 가게주인 Review를 구할 때 사용
     * @param storeId
     * @param orderId
     * @param userId
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(value = "/opponent-reviews",method = RequestMethod.GET)
    @ApiOperation(value = "상대방 리뷰 내용 조회", notes = "내 리뷰 조회")
    public BaseResponse<GetOpponentReviewRes> getHostReview(
            @RequestParam(value = "storeId") Long storeId,
            @RequestParam(value = "orderId") Long orderId,
            @RequestParam(value = "userId",required = false,defaultValue = "0") Long userId // userId가 있으면 가게 리뷰를 구할때 user Review를 구할 때 사용
            ) throws BaseException{

        GetOpponentReviewRes getOpponentReviewRes;
        try{
            getOpponentReviewRes = reviewProvider.retrieveOpponentReview(storeId,orderId,userId);
            return new BaseResponse<>(SUCCESS_READ_Opponent_REVIEW, getOpponentReviewRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/store-reviews/{storeId}",method = RequestMethod.GET)
    @ApiOperation(value = "가게 리뷰 조회", notes = "가게 리뷰 조회")
    public BaseResponse<GetStoreReviewRes> getHostReview(
            @PathVariable("storeId") Long storeId) throws BaseException{

        GetStoreReviewRes getStoreReviewRes;
        try{
            getStoreReviewRes = reviewProvider.retrieveStoreReview(storeId);
            return new BaseResponse<>(SUCCESS_READ_STORE_REVIEW, getStoreReviewRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

//    전체 리뷰 조회
//    리뷰 수정
//    리뷰 삭제

}
