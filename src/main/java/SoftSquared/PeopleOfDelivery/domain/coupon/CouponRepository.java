package SoftSquared.PeopleOfDelivery.domain.coupon;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CouponRepository extends CrudRepository<Coupon,Long> {

    Optional<Coupon> findById(Long id);
}
