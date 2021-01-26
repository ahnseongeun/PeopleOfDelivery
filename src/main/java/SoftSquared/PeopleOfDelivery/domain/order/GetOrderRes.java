package SoftSquared.PeopleOfDelivery.domain.order;


import SoftSquared.PeopleOfDelivery.domain.orderDetail.OrderDetailMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetOrderRes {

    private final String storeName;
    private final List<OrderDetailMenu> orderDetailMenuList;
    private final Integer totalPrice;
    private final Date orderDate;

}
