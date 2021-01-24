package SoftSquared.PeopleOfDelivery.domain.categoryStore;

import SoftSquared.PeopleOfDelivery.domain.catrgory.Category;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryStoreRepository extends CrudRepository<CategoryStore,Long> {

    List<Store> findAllByCategory(Optional<Category> category);
}
