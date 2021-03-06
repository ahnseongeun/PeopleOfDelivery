package SoftSquared.PeopleOfDelivery.domain.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetStoresRes {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String describe;
    private final String imageURL;
    private final Long storeId;

}
