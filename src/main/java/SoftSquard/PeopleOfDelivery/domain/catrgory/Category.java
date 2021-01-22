package SoftSquard.PeopleOfDelivery.domain.catrgory;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.category.CategoryStore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC) // Unit Test 를 위해 PUBLIC
@EqualsAndHashCode(callSuper = false)
@Table(name = "category")
public class Category extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category_image_uri",nullable = false, columnDefinition = "TEXT")
    private String categoryImageURI;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("category_store_category_id")
    private List<CategoryStore> categoryStores;

    public Category(String name, String categoryImageURI) {
        this.name = name;
        this.categoryImageURI = categoryImageURI;
    }

//    public void categoryInfo(String name,String categoryImageURI) {
//        this.name = name;
//        this.categoryImageURI = categoryImageURI;
//    }

}
