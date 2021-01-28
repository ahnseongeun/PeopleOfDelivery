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

    private final Long shoppingBasketId;
    private final String storeName;
    private final Long userId;
    private final Long menuId;
    private final String menuName;
    private final Integer menuPrice;
    private final Integer menuCount;
    private final Long storeId;
    //private final List<GetShoppingBasketMenuRes> getShoppingBasketMenuResList;
}