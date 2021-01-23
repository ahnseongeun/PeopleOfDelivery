package SoftSquared.PeopleOfDelivery.domain.shoppingBasket;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.orderDetail.OrderDetail;
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
@Table(name = "shopping_basket")
public class ShoppingBasket extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 최소수량 1
     */
    @Column(name = "menu_count",nullable = false,columnDefinition = "integer default 1")
    private Integer menuCount;

    /**
     * 1 등록
     * 2 처리완료
     * 3 삭제
     */
    @Column(name = "status",nullable = false)
    private Byte status;

    @OneToMany(mappedBy = "shoppingBasket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("order_detail_shopping_basket_id")
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference("shopping_basket_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_id",nullable = false)
    @JsonBackReference("shopping_basket_menu_id")
    private Menu menu;
}
