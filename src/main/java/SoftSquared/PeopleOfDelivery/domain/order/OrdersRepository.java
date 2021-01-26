package SoftSquared.PeopleOfDelivery.domain.order;

import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.Order;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends CrudRepository<Orders,Long> {

    Optional<Orders> findByIdAndStatus(Long id, Integer status);

    List<Orders> findUserAndStatus(User user, Integer status);

    //List<OrderS> findUserAndStatus(User user,Integer status);
}
