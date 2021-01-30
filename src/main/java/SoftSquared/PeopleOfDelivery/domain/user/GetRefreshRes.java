package SoftSquared.PeopleOfDelivery.domain.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetRefreshRes {

    private final Long userId;
    private final String accessToken;
    private final String refreshToken;

}
