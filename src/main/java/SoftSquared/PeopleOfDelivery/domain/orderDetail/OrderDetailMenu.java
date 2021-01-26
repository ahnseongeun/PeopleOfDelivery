package SoftSquared.PeopleOfDelivery.domain.orderDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderDetailMenu {

    private final Long menuId;
    private final Integer menuCount;
}
