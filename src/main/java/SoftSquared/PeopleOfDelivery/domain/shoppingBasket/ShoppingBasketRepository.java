package SoftSquared.PeopleOfDelivery.domain.shoppingBasket;

import SoftSquared.PeopleOfDelivery.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingBasketRepository extends CrudRepository<ShoppingBasket,Long> {
    Optional<ShoppingBasket> findByIdAndStatus(Long shoppingBasketId, Integer status);
    List<ShoppingBasket> findByUserAndStatus(User user, Integer status);
}
