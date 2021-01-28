package SoftSquared.PeopleOfDelivery.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetReviewsRes {

    private final Long reviewId;
    private final String reviewContent;
    private final Integer reviewStar;
    private final Long userId;
    private final Long orderId;
    private final Long storeId;
}
