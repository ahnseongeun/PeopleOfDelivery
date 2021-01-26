package SoftSquared.PeopleOfDelivery.domain.orderDetail;

import org.hibernate.criterion.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,Long> {
    Optional<OrderDetail> findByIdAndStatus(Long id, Integer status);
}
