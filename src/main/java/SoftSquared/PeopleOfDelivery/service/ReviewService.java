package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.order.OrdersRepository;
import SoftSquared.PeopleOfDelivery.domain.review.PostReviewRes;
import SoftSquared.PeopleOfDelivery.domain.review.Review;
import SoftSquared.PeopleOfDelivery.domain.review.ReviewRepository;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import ch.qos.logback.classic.spi.IThrowableProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         OrdersRepository ordersRepository,
                         UserRepository userRepository,
                         StoreRepository storeRepository) {
        this.reviewRepository = reviewRepository;
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }


    public PostReviewRes createReview(String content,
                                      Integer startCount,
                                      Long orderId,
                                      Long userId,
                                      Long storeId) throws BaseException {

        User user = userRepository.findByIdAndStatus(userId,1).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_USER));

        Store store = storeRepository.findByIdAndStatus(storeId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_STORES));

        Orders orders = ordersRepository.findByIdAndStatus(orderId,2)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_ORDER));

        //TODO
        Review review = reviewRepository.findByStoreAndOrdersAndUserAndStatus(store,orders,user,1)
                .orElse(null);

        if(review != null)
            throw new BaseException(DUPLICATED_REVIEW);

        review = Review.builder()
                .content(content)
                .starCount(startCount)
                .orders(orders)
                .user(user)
                .store(store)
                .status(1)
                .build();

        try{
            review = reviewRepository.save(review);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_POST_REVIEW);
        }

        return PostReviewRes.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .starCount(review.getStarCount())
                .orderId(review.getOrders().getId())
                .storeId(review.getStore().getId())
                .userId(review.getUser().getId())
                .build();
    }
}
