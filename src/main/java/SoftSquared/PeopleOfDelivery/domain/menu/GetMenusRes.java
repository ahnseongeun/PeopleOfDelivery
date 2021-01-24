package SoftSquared.PeopleOfDelivery.domain.menu;

import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetMenusRes {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String describe;
    private final String imageURL;
    private final Long storeId;

}
