package SoftSquared.PeopleOfDelivery.domain.shoppingBasket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostShoppingBasketRes {

    private final Long shoppingBasketId;
    private final Integer menuCount;
    private final Long userId;
    private final Long menuId;

}
