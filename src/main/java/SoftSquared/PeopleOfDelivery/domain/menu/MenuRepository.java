package SoftSquared.PeopleOfDelivery.domain.menu;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends CrudRepository<Menu,Long> {
    List<Menu> findByStatusNot(Integer status);

    Optional<Menu> findByIdAndStatusNot(Long id, Integer status);

    Optional<Menu> findByIdAndStatus(Long id, Integer status);
}
