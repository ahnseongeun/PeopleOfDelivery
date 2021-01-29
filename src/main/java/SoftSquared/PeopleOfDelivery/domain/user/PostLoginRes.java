package SoftSquared.PeopleOfDelivery.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostLoginRes {

    private final String jwt;
}
