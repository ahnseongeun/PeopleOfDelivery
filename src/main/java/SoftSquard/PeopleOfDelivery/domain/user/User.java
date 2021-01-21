package SoftSquard.PeopleOfDelivery.domain.user;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.coupon.Coupon;
import SoftSquard.PeopleOfDelivery.domain.menu.Menu;
import SoftSquard.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasket;
import SoftSquard.PeopleOfDelivery.domain.store.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "birthdate", nullable = false)
    private String birthdate;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "imageURI", nullable = false)
    private String imageURI;

    @Column(name = "gender", nullable = false)
    private Byte gender;

    @Column(name = "role", nullable = false)
    private Byte role;

    @Column(name = "status", nullable = false)
    private Byte status;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("coupon_user_id")
    private Coupon coupon;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference("store_user_id")
    private List<Store> stores;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference("ShoppingBasket_user_id")
    private List<ShoppingBasket> shoppingBaskets;

}
