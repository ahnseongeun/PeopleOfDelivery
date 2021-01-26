package SoftSquared.PeopleOfDelivery.domain.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostMenuReq {

    private final Long menuId;
    private final Integer menuCount;

}
