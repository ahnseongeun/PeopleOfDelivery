package SoftSquared.PeopleOfDelivery.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetOpponentReviewRes {

    private final Long orderId;
    private final String opponentReviewContent;
}
