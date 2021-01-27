package SoftSquared.PeopleOfDelivery.domain.shoppingBasket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetTotalPriceRes {

    private Integer usedCoupon;
    private Integer orderPrice;
    private Integer deliveryFee;
    private Long storeId;
}
