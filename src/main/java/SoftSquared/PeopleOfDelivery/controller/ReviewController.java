package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.review.*;
import SoftSquared.PeopleOfDelivery.provider.ReviewProvider;
import SoftSquared.PeopleOfDelivery.service.ReviewService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Slf4j
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
     * @param storeId
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/reviews",method = RequestMethod.POST)
    @ApiOperation(value = "리뷰 추가하기", notes = "리뷰 추가하기")
    public BaseResponse<PostReviewRes> createReview(
            //@RequestParam(value = "role") Integer role,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "starCount",required = false,defaultValue = "3") Integer startCount,
            @RequestParam(value = "orderId") Long orderId,
            //@RequestParam(value = "userId") Long userId,
            @RequestParam(value = "storeId") Long storeId,
            Authentication authentication
    ) throws IOException {


        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            long userId = claims.get("userId",Integer.class);
            log.info("리뷰 추가하기");

            if(role != 1 && role != 50) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            PostReviewRes postReviewRes = reviewService.createReview(
                    role,content,startCount,orderId,userId,storeId
            );
            return new BaseResponse<>(SUCCESS_POST_REVIEW, postReviewRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 전체 리뷰 조회
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(value = "/reviews",method = RequestMethod.GET)
    @ApiOperation(value = "전체 리뷰 조회 (관리자 기능)", notes = "전체 리뷰 조회")
    public BaseResponse<List<GetReviewsRes>> getReviews(
            Authentication authentication
    ) throws BaseException{

        List<GetReviewsRes> getReviewsResList;
        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            log.info("전체 리뷰 조회");

            if(role != 100) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            getReviewsResList = reviewProvider.retrieveReviewList();
            return new BaseResponse<>(SUCCESS_READ_REVIEWS, getReviewsResList);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user-reviews/me",method = RequestMethod.GET)
    @ApiOperation(value = "내 리뷰 조회", notes = "내 리뷰 조회")
    public BaseResponse<GetReviewRes> getMyReview(
            //@PathVariable("userId") Long userId
            Authentication authentication
             ) throws BaseException{

        GetReviewRes getReviewRes;
        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            long userId = claims.get("userId",Integer.class);
            log.info("내 리뷰 조회");

            if(role != 1 && role != 50) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            getReviewRes = reviewProvider.retrieveMyReview(userId);
            return new BaseResponse<>(SUCCESS_READ_MY_REVIEW, getReviewRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     *  userId가 있으면 가게 리뷰를 구할때 user Review를 구할 때 사용
     *  userId가 없다면 내 리뷰를 구할때 가게주인 Review를 구할 때 사용
     * @return
     * @throws BaseException
     */
    //            @RequestParam(value = "storeId") Long storeId,
//            @RequestParam(value = "orderId") Long orderId,
//            @RequestParam(value = "userId",required = false,defaultValue = "0") Long userId // userId가 있으면 가게 리뷰를 구할때 user Review를 구할 때 사용
    @ResponseBody
    @RequestMapping(value = "/reviews/{reviewId}",method = RequestMethod.GET)
    @ApiOperation(value = " 리뷰 조회", notes = " 리뷰 조회")
    public BaseResponse<GetReviewsRes> getReview(
            @PathVariable Long reviewId) throws BaseException{

        GetReviewsRes getReviewsRes;
        try{
            getReviewsRes = reviewProvider.retrieveReview(reviewId);
            return new BaseResponse<>(SUCCESS_READ_REVIEW, getReviewsRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 가게 리뷰 조회
     * @param storeId
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(value = "/store-reviews/{storeId}",method = RequestMethod.GET)
    @ApiOperation(value = "가게 리뷰 조회", notes = "가게 리뷰 조회")
    public BaseResponse<GetStoreReviewRes> getStoreReview(
            @PathVariable("storeId") Long storeId) throws BaseException{

        GetStoreReviewRes getStoreReviewRes;
        try{
            getStoreReviewRes = reviewProvider.retrieveStoreReview(storeId);
            return new BaseResponse<>(SUCCESS_READ_STORE_REVIEW, getStoreReviewRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 리뷰 수정하기
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(value = "/reviews/{reviewId}",method = RequestMethod.PATCH)
    @ApiOperation(value = "리뷰 수정", notes = "리뷰 수정")
    public BaseResponse<GetReviewsRes> updateReview(
            @PathVariable("reviewId") Long reviewId,
            //@RequestParam(value = "role") Integer role,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "starCount",required = false,defaultValue = "3") Integer startCount,
            Authentication authentication
            ) throws BaseException{

        GetReviewsRes getReviewsRes;
        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            long userId = claims.get("userId", Integer.class);
            int role = claims.get("role", Integer.class);
            log.info("리뷰 수정");

            if(role != 1 && role != 50) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            getReviewsRes = reviewService.updateReview(reviewId,userId,role,content,startCount);
            return new BaseResponse<>(SUCCESS_UPDATE_REVIEW, getReviewsRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 리뷰 삭제하기
     * @param reviewId
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(value = "/reviews/delete/{reviewId}",method = RequestMethod.PATCH)
    @ApiOperation(value = "리뷰 삭제", notes = "리뷰 삭제")
    public BaseResponse<DeleteReviewRes> deleteReview(
            @PathVariable("reviewId") Long reviewId,
            Authentication authentication
    ) throws BaseException{

        DeleteReviewRes DeleteReviewRes;
        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            long userId = claims.get("userId", Integer.class);
            int role = claims.get("role", Integer.class);
            log.info("리뷰 삭제");

            if(role != 1 && role != 50) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            DeleteReviewRes = reviewService.deleteReview(reviewId,userId);
            return new BaseResponse<>(SUCCESS_DELETE_REVIEW, DeleteReviewRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
