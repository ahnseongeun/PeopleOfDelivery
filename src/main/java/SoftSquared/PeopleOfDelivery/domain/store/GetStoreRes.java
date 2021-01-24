package SoftSquared.PeopleOfDelivery.domain.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetStoreRes {

    private final Long id;
    private final String name;
    private final String location;
    private final Integer lowBoundPrice;
    private final Integer deliveryFee;
    private final Float totalStarAverage;
    private final String imageURL;

}
