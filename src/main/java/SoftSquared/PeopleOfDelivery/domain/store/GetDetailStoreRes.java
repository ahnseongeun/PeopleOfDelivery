package SoftSquared.PeopleOfDelivery.domain.store;

import SoftSquared.PeopleOfDelivery.domain.menu.GetMenuRes;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetDetailStoreRes {

    private final Long id;
    private final String name;
    private final String phoneNumber;
    private final String description;
    private final Integer lowBoundPrice;
    private final Integer deliveryFee;
    private final boolean choiceCheck;
    private final Integer UserReviewCount;
    private final Integer HostReviewCount;
    private final Float totalStarAverage;
    private final Integer pickStoreCount;
    private final String imageURL;
    private final List<GetMenuRes> menuList;

}
