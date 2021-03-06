package SoftSquared.PeopleOfDelivery.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetOpponentReviewRes {

    private final Long reviewId;
    private final String opponentReviewContent;
}
