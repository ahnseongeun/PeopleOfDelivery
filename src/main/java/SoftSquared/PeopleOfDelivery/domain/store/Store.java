package SoftSquared.PeopleOfDelivery.domain.store;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
import SoftSquared.PeopleOfDelivery.domain.categoryStore.CategoryStore;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.review.Review;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "store")
public class Store extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "low_bound_price", nullable = false)
    private Integer lowBoundPrice;

    @Column(name = "delivery_fee", nullable = false)
    private Integer deliveryFee;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_uri", nullable = false)
    private String imageURI;


    /**
     * 1은 사용 , 2는 삭제
     */
    @Column(name = "status",nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("category_store_store_id")
    private List<CategoryStore> categoryStores;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("menu_store_id")
    private List<Menu> menus;

    @OneToMany(mappedBy = "store",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference("review_store_id")
    private List<Review> reviews;

    @ManyToOne
    @JsonBackReference("store_user_id")
    @JoinColumn(name = "user_id")
    private User user;



}
