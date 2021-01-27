package SoftSquared.PeopleOfDelivery.domain.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class DeleteMenuRes {

    private final Long menuId;
    private final Integer MenuStatus;
    private final Integer MenuImageStatus;
}
