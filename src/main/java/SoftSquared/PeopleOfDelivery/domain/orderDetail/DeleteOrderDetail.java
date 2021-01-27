package SoftSquared.PeopleOfDelivery.domain.orderDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeleteOrderDetail {

    private final Long orderDetailId;
    private final Integer orderDetailStatus;
}
