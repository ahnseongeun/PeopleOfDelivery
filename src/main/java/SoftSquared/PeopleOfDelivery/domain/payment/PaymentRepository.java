package SoftSquared.PeopleOfDelivery.domain.payment;

import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment,Long> {

    List<Payment> findByStatus(Integer status);

    Optional<Payment> findByOrdersAndStatus(Orders orders, Integer status);

    Optional<Payment> findByIdAndStatus(Long paymentId, Integer status);
}
