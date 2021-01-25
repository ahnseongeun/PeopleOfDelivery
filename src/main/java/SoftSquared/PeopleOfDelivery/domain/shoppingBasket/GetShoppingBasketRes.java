package SoftSquared.PeopleOfDelivery.domain.shoppingBasket;

import SoftSquared.PeopleOfDelivery.domain.menu.GetMenuRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
/*
 */
public class GetShoppingBasketRes {

    private final String storeName;
    private final Long userId;
    private final List<GetShoppingBasketMenuRes> getShoppingBasketMenuResList;
    private final Integer totalPrice;
}