package SoftSquared.PeopleOfDelivery.domain.review;

import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review,Long> {
    List<Review> findByUserAndStatus(User user, Integer status);

    Optional<Review> findByStoreAndOrdersAndUserAndStatus(Store store, Orders orders, User user, Integer status);

    List<Review> findByStoreAndUserNotAndStatus(Store store,User user,Integer status);
}
