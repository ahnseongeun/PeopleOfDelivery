package SoftSquared.PeopleOfDelivery.domain.store;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoreRepository extends CrudRepository<Store,Long> {
    List<Store> findByStatus(Integer status);
}
