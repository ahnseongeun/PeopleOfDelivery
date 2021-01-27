package SoftSquared.PeopleOfDelivery.domain.orderDetail;

import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import org.hibernate.criterion.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,Long> {
    Optional<OrderDetail> findByIdAndStatus(Long id, Integer status);

    List<OrderDetail> findByOrdersAndStatus(Orders orders, Integer status);
}
