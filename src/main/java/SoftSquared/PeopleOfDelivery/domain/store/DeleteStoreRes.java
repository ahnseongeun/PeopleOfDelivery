package SoftSquared.PeopleOfDelivery.domain.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeleteStoreRes {

    private final Long storeId;
    private final Integer status;
}
