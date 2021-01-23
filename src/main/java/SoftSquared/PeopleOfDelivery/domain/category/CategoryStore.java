package SoftSquared.PeopleOfDelivery.domain.category;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
import SoftSquared.PeopleOfDelivery.domain.catrgory.Category;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC) // Unit Test 를 위해 PUBLIC
@EqualsAndHashCode(callSuper = false)
@Table(name = "category_store")
public class CategoryStore extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    @JsonBackReference("category_store_category_id")
    private Category category;


    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference("category_store_store_id")
    private Store store;

}
