package SoftSquared.PeopleOfDelivery.domain.shoppingBasket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateShoppingBasketRes {

    private final Long shoppingBasketId;
    private final Long menuId;
    private final String menuName;
    private final Integer menuCount;
    private final Integer totalMenuPrice;
}
