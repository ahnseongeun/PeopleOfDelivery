package SoftSquard.PeopleOfDelivery.domain.categoryStore;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.catrgory.Category;
import SoftSquard.PeopleOfDelivery.domain.store.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "category_store")
public class CategoryStore extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    @JsonBackReference("CategoryStore_category_id")
    private Category category;


    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference("CategoryStore_store_id")
    private Store store;

}
