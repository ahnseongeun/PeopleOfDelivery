package SoftSquared.PeopleOfDelivery.domain.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findByStatus(Integer status);
    List<User> findByStatusAndNameIsContaining(Integer status, String name);
    List<User> findByStatusAndEmailIsContaining(Integer status, String email);
    Optional<User> findById(Long userId);

    Optional<User> findByIdAndStatus(Long userId, Integer status);

    Optional<User> findByEmailAndStatus(String email,Integer status);
}
