package SoftSquared.PeopleOfDelivery.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetReviewRes {

    private final Long userId;
    private final Integer reviewCount;
    private final List<GetOrderReviewRes> getOrderReviewResList;

}
