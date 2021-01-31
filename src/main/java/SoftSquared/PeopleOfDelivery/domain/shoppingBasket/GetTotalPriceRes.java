package SoftSquared.PeopleOfDelivery.domain.shoppingBasket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetTotalPriceRes {

    private final Integer usedCoupon;
    private final Integer orderPrice;
    private final Integer deliveryFee;
    private final Long storeId;
}
