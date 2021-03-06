package SoftSquared.PeopleOfDelivery.domain.store;

import io.swagger.models.auth.In;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface StoreRepository extends CrudRepository<Store,Long> {
    List<Store> findByStatus(Integer status);

    Optional<Store> findByIdAndStatus(Long storeId, Integer status);

    List<Store> findByStatus(Integer status, Sort sort);

    List<Store> findByStatusAndLowBoundPriceGreaterThanEqual(Integer status, Integer lowBoundPrice, Sort sort);

    List<Store> findByStatusAndDeliveryFeeLessThanEqual(Integer status, Integer deliveryFeeLowBound, Sort sort);
}
