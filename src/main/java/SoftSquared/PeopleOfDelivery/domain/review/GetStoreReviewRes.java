package SoftSquared.PeopleOfDelivery.domain.review;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetStoreReviewRes {

    private final Long storeId;
    private final String storeName;
    private final Double reviewTotalAvg;

    //사용자 Review 조회 용도
    private final List<ReviewRes> userIdAndOrderIdReviewList;
    private final Long review1Count;
    private final Long review2Count;
    private final Long review3Count;
    private final Long review4Count;
    private final Long review5Count;

}
