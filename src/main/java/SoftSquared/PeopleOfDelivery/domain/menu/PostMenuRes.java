package SoftSquared.PeopleOfDelivery.domain.menu;

import SoftSquared.PeopleOfDelivery.domain.store.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostMenuRes {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String description;
    private final String imageURL;
    private final Long storeId;
    private final Integer imageStatus;

}
