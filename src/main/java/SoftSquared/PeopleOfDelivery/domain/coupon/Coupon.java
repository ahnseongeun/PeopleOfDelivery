package SoftSquared.PeopleOfDelivery.domain.coupon;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
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
@Table(name = "coupon")
public class Coupon extends BaseEntity{

    @Id
    @Column(name = "id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 쿠폰의 디폴트 값은 0으로 설정한다.
     */
    @Column(name = "coupon1000",columnDefinition = "integer default 0")
    private Integer coupon1000;

    @Column(name = "coupon3000",columnDefinition = "integer default 0")
    private Integer coupon3000;

    @Column(name = "coupon5000",columnDefinition = "integer default 0")
    private Integer coupon5000;

    @OneToOne
    @JsonBackReference("coupon_user_id")
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
