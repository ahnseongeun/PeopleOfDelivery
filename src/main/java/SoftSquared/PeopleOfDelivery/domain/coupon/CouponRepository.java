package SoftSquared.PeopleOfDelivery.domain.coupon;

import SoftSquared.PeopleOfDelivery.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CouponRepository extends CrudRepository<Coupon,Long> {

    Optional<Coupon> findById(Long id);

    Optional<Coupon> findByUser(User user);
}
