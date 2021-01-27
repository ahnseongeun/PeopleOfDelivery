package SoftSquared.PeopleOfDelivery.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class GetOrderReviewRes {

    private final Long reviewId;
    private final Long storeId;
    private final Long orderId;
    private final String storeName;
    private final String userReviewContent;
    //private final String hostReviewContent; API 추가하는 것이 효율적
    private final Date createReviewTime;

}
