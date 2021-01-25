package SoftSquared.PeopleOfDelivery.domain.shoppingBasket;

import SoftSquared.PeopleOfDelivery.domain.menu.GetMenuRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetShoppingBasketsRes {

    private final Long id;
    private final String name;
    private final Integer menuCount;
    private final Long userId;
    private final Long menuId;

}