package SoftSquared.PeopleOfDelivery.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostReviewRes {

    private final Long reviewId;
    private final String content;
    private final Integer starCount;
    private final Long orderId;
    private final Long userId;
    private final Long storeId;
}
