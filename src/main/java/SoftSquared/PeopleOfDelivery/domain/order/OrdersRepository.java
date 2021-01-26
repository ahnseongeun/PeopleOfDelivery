package SoftSquared.PeopleOfDelivery.domain.order;

import SoftSquared.PeopleOfDelivery.domain.store.Store;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends CrudRepository<Orders,Long> {

    Optional<Orders> findByIdAndStatus(Long id, Integer status);
}
