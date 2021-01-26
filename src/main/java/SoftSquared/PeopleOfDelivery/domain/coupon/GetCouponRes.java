package SoftSquared.PeopleOfDelivery.domain.coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetCouponRes {
    private final Long id;
    private final Long userId;
    private final Integer coupon1000Count;
    private final Integer coupon3000Count;
    private final Integer coupon5000Count;
}
