package SoftSquard.PeopleOfDelivery.domain.menu;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.categoryStore.CategoryStore;
import SoftSquard.PeopleOfDelivery.domain.store.Store;
import SoftSquard.PeopleOfDelivery.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @Column(name = "describe",nullable = false, columnDefinition = "TEXT")
    private String describe;

    @Column(name = "imageURI", nullable = false, columnDefinition = "TEXT")
    private String imageURI;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "imageStatus", nullable = false)
    private String imageStatus;

    @ManyToOne
    @JsonBackReference("menu_store_id")
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "menu",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonBackReference("ShoppingBasket_menu_id")
    private List<Menu> menus;


}
