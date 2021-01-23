package SoftSquared.PeopleOfDelivery.domain.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostStoreRes {

    private final Long id;
    private final String name;
    private final String phoneNumber;
    private final String location;
    private final Integer lowBoundDelivery;
    private final Integer deliveryFee;
    private final String description;
    private final Integer userId;
    private final String imageUrl;

}
