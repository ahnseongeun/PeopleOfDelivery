package SoftSquared.PeopleOfDelivery.domain.review;

import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review,Long> {
    List<Review> findByUserAndStatus(User user, Integer status);

    Optional<Review> findByStoreAndOrdersAndUserAndStatus(Store store, Orders orders, User user, Integer status);

    List<Review> findByStoreAndStatus(Store store,Integer status);

    List<Review> findByStatus(Integer status);

    Optional<Review> findByIdAndStatus(Long ReviewId,Integer status);

    Optional<Review> findByStoreAndOrdersAndUserNotAndStatus(Store store, Orders order, User user, Integer status);

    List<Review> findByStoreAndUserAndStatus(Store store,Long hostId, Integer status);
}
