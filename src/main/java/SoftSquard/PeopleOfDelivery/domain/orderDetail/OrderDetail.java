package SoftSquard.PeopleOfDelivery.domain.orderDetail;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.order.Orders;
import SoftSquard.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasket;
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

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    @JsonBackReference("order_detail_order_id")
    private Orders orders;


    @ManyToOne
    @JoinColumn(name = "shoppingBasket_id",nullable = false)
    @JsonBackReference("order_detail_shopping_basket_id")
    private ShoppingBasket shoppingBasket;

}
