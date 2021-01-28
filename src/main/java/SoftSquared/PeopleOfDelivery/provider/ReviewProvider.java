package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.order.OrdersRepository;
import SoftSquared.PeopleOfDelivery.domain.review.*;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
@Slf4j
public class ReviewProvider {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrdersRepository ordersRepository;

    @Autowired
    public ReviewProvider(ReviewRepository reviewRepository,
                          UserRepository userRepository,
                          StoreRepository storeRepository,
                          OrdersRepository ordersRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.ordersRepository = ordersRepository;
    }

    /**
     * 내 리뷰 조회
     * @param userId
     * @return
     * @throws BaseException
     */
    public GetReviewRes retrieveReview(Long userId) throws BaseException {

        User user = userRepository.findByIdAndStatus(userId,1).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_USER));

        List<Review> reviewList = reviewRepository.findByUserAndStatus(user,1);

        /*
        가게 하나당 회원 리뷰 하나만 등록가능
         */
        if(reviewList.size() == 0)
            throw new BaseException(EMPTY_REVIEW);

        return GetReviewRes.builder()
                .userId(user.getId())
                .reviewCount(reviewList.size())
                .getOrderReviewResList(reviewList.stream()
                        .map(review -> GetOrderReviewRes.builder()
                                .reviewId(review.getId())
                                .storeId(review.getStore().getId())
                                .storeName(review.getStore().getName())
                                .reviewStar(review.getStarCount())
                                //.hostReviewContent(userid,orderId)
                                .userReviewContent(review.getContent())
                                .orderId(review.getOrders().getId())
                                .createReviewTime(review.getCreatedTime())
                                .build()).collect(Collectors.toList()))
                .build();
    }

    /**
     * 회원 리뷰에 조회에 필요한 가게 주인 리뷰 조회
     * @return
     * @throws BaseException
     */
    public GetOpponentReviewRes retrieveOpponentReview(Long reviewId) throws BaseException{

        
//        Store store = storeRepository.findByIdAndStatus(storeId,1).orElseThrow(()
//                -> new BaseException(FAILED_TO_GET_DETAIL_STORE));
//
//        Orders orders = ordersRepository.findByIdAndStatus(orderId,2).orElseThrow(()
//                -> new BaseException(FAILED_TO_GET_ORDER));
//
//        if(userId == 0L){ //내 리뷰를 구할 때 가게 주인 리뷰 조회
//            userId = store.getUser().getId();
//        }
//
//        User user = userRepository.findByIdAndStatus(userId,1).orElseThrow(()
//                -> new BaseException(FAILED_TO_GET_USER));
//
//        log.info(String.valueOf(user.getId()));
        Review review;
        review = reviewRepository.findByIdAndStatus(reviewId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_REVIEW));

        Store store = review.getStore();
        Orders order = review.getOrders();
        User user = review.getUser();

        //반대 사람 review 내용 조회
            review = reviewRepository.findByStoreAndOrdersAndUserNotAndStatus(store,order,user,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_REVIEW));

        return GetOpponentReviewRes.builder()
                .reviewId(review.getId())
                .opponentReviewContent(review.getContent())
                .build();

    }


    /*
    private final Long storeId;
    private final Long storeName;
    private final Float reviewTotalAvg;
    private final Integer review1Count;
    private final Integer review2Count;
    private final Integer review3Count;
    private final Integer review4Count;
    private final Integer review5Count;
     */

    /**
     * 가게 리뷰 조회
     * @param storeId
     * @return
     * @throws BaseException
     */
    public GetStoreReviewRes retrieveStoreReview(Long storeId) throws BaseException {

        Store store = storeRepository.findByIdAndStatus(storeId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_DETAIL_STORE));

        User user = userRepository.findByIdAndStatus(store.getUser().getId(),1).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_USER));

        List<Review> reviewList;

        /*
        user review
         */
        try{
            reviewList = reviewRepository.findByStoreAndUserNotAndStatus(store,user,1);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_GET_REVIEW);
        }

        return GetStoreReviewRes.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .userIdAndOrderIdReviewList(reviewList.stream()
                        .map(review -> ReviewRes.builder()
                                .userId(review.getUser().getId())
                                .orderId(review.getOrders().getId())
                                .reviewId(review.getId())
                                .build())
                        .collect(Collectors.toList()))
                .reviewTotalAvg(reviewList.stream().mapToInt(Review::getStarCount).average().orElse(0))
                .review1Count(reviewList.stream()
                        .filter(review -> review.getStarCount() == 1)
                        .count())
                .review2Count(reviewList.stream()
                        .filter(review -> review.getStarCount() == 2)
                        .count())
                .review3Count(reviewList.stream()
                        .filter(review -> review.getStarCount() == 3)
                        .count())
                .review4Count(reviewList.stream()
                        .filter(review -> review.getStarCount() == 4)
                        .count())
                .review5Count(reviewList.stream()
                        .filter(review -> review.getStarCount() == 5)
                        .count())
                .build();

    }

    /**
     * 전체 리뷰 조회
     * @return
     * @throws BaseException
     */
    public List<GetReviewsRes> retrieveReviewList() throws BaseException {

        List<Review> reviewList;

        try{
            reviewList = reviewRepository.findByStatus(1);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_GET_REVIEWS);
        }

        return reviewList.stream().map(review -> GetReviewsRes.builder()
                .reviewId(review.getId())
                .reviewContent(review.getContent())
                .reviewStar(review.getStarCount())
                .userId(review.getUser().getId())
                .orderId(review.getOrders().getId())
                .storeId(review.getStore().getId())
                .build())
                .collect(Collectors.toList());
    }
}
