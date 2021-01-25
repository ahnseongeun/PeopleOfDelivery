package SoftSquared.PeopleOfDelivery.domain.shoppingBasket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetShoppingBasketMenuRes {
    private final Long id;
    private final String name;
    private final Integer price;
    private final Integer menuCount;
    private final Long storeId;
}
