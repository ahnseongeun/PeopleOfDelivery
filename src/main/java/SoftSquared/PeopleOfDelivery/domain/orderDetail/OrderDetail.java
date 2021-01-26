package SoftSquared.PeopleOfDelivery.domain.orderDetail;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasket;
import SoftSquared.PeopleOfDelivery.domain.user.User;
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
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menuCount",columnDefinition = "Integer default 1")
    private Integer menuCount;

    @Column(name = "status",columnDefinition = "Integer default 1")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference("order_detail_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_id",nullable = false)
    @JsonBackReference("order_detail_menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    @JsonBackReference("order_detail_order_id")
    private Orders orders;


//    @ManyToOne
//    @JoinColumn(name = "shoppingBasket_id",nullable = false)
//    @JsonBackReference("order_detail_shopping_basket_id")
//    private ShoppingBasket shoppingBasket;

}
