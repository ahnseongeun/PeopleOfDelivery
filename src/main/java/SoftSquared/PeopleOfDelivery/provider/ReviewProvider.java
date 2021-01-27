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
                                //.hostReviewContent(userid,orderId)
                                .userReviewContent(review.getContent())
                                .orderId(review.getOrders().getId())
                                .createReviewTime(review.getCreatedTime())
                                .build()).collect(Collectors.toList()))
                .build();
    }

    /**
     * 내 리뷰에 조회에 필요한 가게 주인 리뷰 조회
     * @param storeId
     * @param orderId
     * @return
     * @throws BaseException
     */
    public GetOpponentReviewRes retrieveHostReview(Long storeId,
                                               Long orderId) throws BaseException{

        Store store = storeRepository.findByIdAndStatus(storeId,1).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_DETAIL_STORE));

        Orders orders = ordersRepository.findByIdAndStatus(orderId,2).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_ORDER));

        User user = userRepository.findByIdAndStatus(store.getUser().getId(),1).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_USER));

        log.info(String.valueOf(user.getId()));

        Review review = reviewRepository.findByStoreAndOrdersAndUserAndStatus(store,orders,user,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_REVIEW));

        return GetOpponentReviewRes.builder()
                .orderId(orders.getId())
                .opponentReviewContent(review.getContent())
                .build();

    }
}
