package SoftSquared.PeopleOfDelivery.domain.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetMenuRes {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String description;
    private final String imageURL;
    private final Long storeId;
}
