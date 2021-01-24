package SoftSquared.PeopleOfDelivery.domain.menu;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasket;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
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
    private Integer price;

    @Column(name = "description",nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageURL;

    /**
     * 1은 사용, 2는 삭제
     */
    @Column(name = "status", nullable = false)
    private Integer status;

    /**
     * 1은 사용, 2는 삭제
     */
    @Column(name = "image_status", nullable = false)
    private Integer imageStatus;

    /**
     * 1은 일반 메뉴, 2는 대표 메뉴, 3은 인기 메뉴
     */
    @Column(name = "popular_check", nullable = false, columnDefinition = "Integer default 1")
    private Integer popularCheck;

    @ManyToOne
    @JsonBackReference("menu_store_id")
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "menu",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonBackReference("shopping_basket_menu_id")
    private List<ShoppingBasket> shoppingBaskets;


}
