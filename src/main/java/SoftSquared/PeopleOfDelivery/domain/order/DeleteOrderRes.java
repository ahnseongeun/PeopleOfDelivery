package SoftSquared.PeopleOfDelivery.domain.order;

import SoftSquared.PeopleOfDelivery.domain.orderDetail.DeleteOrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DeleteOrderRes {

    private final Long orderId;
    private final Integer status;
    private final List<DeleteOrderDetail> orderDetailList;
}
