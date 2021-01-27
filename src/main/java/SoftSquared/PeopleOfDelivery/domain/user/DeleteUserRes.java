package SoftSquared.PeopleOfDelivery.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeleteUserRes {

    private final Long userId;
    private final Integer status;
}
