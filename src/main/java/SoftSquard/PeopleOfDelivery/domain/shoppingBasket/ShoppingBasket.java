package SoftSquard.PeopleOfDelivery.domain.shoppingBasket;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.menu.Menu;
import SoftSquard.PeopleOfDelivery.domain.store.Store;
import SoftSquard.PeopleOfDelivery.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "shopping_basket")
public class ShoppingBasket extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menuCount",nullable = false,columnDefinition = "integer default 1")
    private Integer menuCount;

    @Column(name = "status",nullable = false)
    private Byte status;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference("ShoppingBasket_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_id",nullable = false)
    @JsonBackReference("ShoppingBasket_menu_id")
    private Menu menu;
}
