package SoftSquard.PeopleOfDelivery.domain.user;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.coupon.Coupon;
import SoftSquard.PeopleOfDelivery.domain.order.Orders;
import SoftSquard.PeopleOfDelivery.domain.review.Review;
import SoftSquard.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasket;
import SoftSquard.PeopleOfDelivery.domain.store.Store;
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

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "birthdate", nullable = false)
    private String birthdate;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "image_uri", nullable = false)
    private String imageURI;

    /**
     * 0은 여자, 1은 남자
     */
    @Column(name = "gender", nullable = false)
    private Integer gender;

    /**
     * 1은 일반유저, 50은 가게주인, 100은 사장님
     */
    @Column(name = "role", nullable = false)
    private Integer role;

    /**
     * 1은 사용중
     * 2는 정지중
     * 3은 탈퇴
     */
    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("coupon_user_id")
    private Coupon coupon;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference("store_user_id")
    private List<Store> stores;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference("shopping_basket_user_id")
    private List<ShoppingBasket> shoppingBaskets;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference("orders_user_id")
    private List<Orders> orders;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference("review_user_id")
    private List<Review> reviews;


    public User(String name,String email, String password,String phoneNumber
                ,String location, Integer role, Integer status, String birthdate, Integer gender, String imageURI){
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.role = role;
        this.status = status;
        this.birthdate = birthdate;
        this.gender = gender;
        this.imageURI = imageURI;
    }


}
