package SoftSquared.PeopleOfDelivery.domain.order;

import SoftSquared.PeopleOfDelivery.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostOrderRes {

    private final Long id;
    private final String name;
    private final String phoneNumber;
    private final String location;
    private final Integer lowBoundDelivery;
    private final Integer deliveryFee;
    private final String description;
    private final Integer userId;
    private final String imageURL;
    private final User user;
}
