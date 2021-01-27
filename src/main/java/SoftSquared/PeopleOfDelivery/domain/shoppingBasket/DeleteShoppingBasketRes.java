package SoftSquared.PeopleOfDelivery.domain.shoppingBasket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DeleteShoppingBasketRes {

    private final Long shoppingBasketId;
    private final Integer status;
}
