package SoftSquared.PeopleOfDelivery.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeleteOrderRes {

    private final Long orderId;
    private final Integer status;
}
