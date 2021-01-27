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
public class GetOrderDetailRes {

    private final Long storeId; // store id, name , phone_number 조회가능
    private final Long userId; // user_address, user_phone_number 조회가능
    private final List<OrderDetailMenu> orderDetailMenuList;
    private final String requestContent;
    private final Integer orderPrice;
    private final String payType;
    private final Integer deliveryFee;
    private final Date orderDate;
    private final Integer deliveryStatus;
}
