package SoftSquared.PeopleOfDelivery.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetUserRes {

    private final Long id;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String image_url;
}
