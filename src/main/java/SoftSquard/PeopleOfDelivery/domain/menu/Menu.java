package SoftSquard.PeopleOfDelivery.domain.menu;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasket;
import SoftSquard.PeopleOfDelivery.domain.store.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "menu")
public class Menu extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "description",nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_uri", nullable = false, columnDefinition = "TEXT")
    private String imageURI;

    /**
     * 1은 사용, 2는 삭제
     */
    @Column(name = "status", nullable = false)
    private String status;

    /**
     * 1은 사용, 2는 삭제
     */
    @Column(name = "image_status", nullable = false)
    private String imageStatus;

    @ManyToOne
    @JsonBackReference("menu_store_id")
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "menu",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonBackReference("shopping_basket_menu_id")
    private List<ShoppingBasket> shoppingBaskets;


}
