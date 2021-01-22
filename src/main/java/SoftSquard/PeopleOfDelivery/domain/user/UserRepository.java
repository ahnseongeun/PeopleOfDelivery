package SoftSquard.PeopleOfDelivery.domain.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findByStatus(Integer status);
    List<User> findByStatusAndNameIsContaining(Integer status, String name);
    User findById(Integer userId);
}
