package SoftSquard.PeopleOfDelivery.domain.order;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.coupon.Coupon;
import SoftSquard.PeopleOfDelivery.domain.orderDetail.OrderDetail;
import SoftSquard.PeopleOfDelivery.domain.payment.Payment;
import SoftSquard.PeopleOfDelivery.domain.review.Review;
import SoftSquard.PeopleOfDelivery.domain.store.Store;
import SoftSquard.PeopleOfDelivery.domain.user.User;
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
@Table(name = "orders")
public class Orders extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_content",columnDefinition = "TEXT")
    private String request_content;

    /**
     * null값이 들어오면 기본 유저정보에서 주소 등록
     */
    @Column(name = "address")
    private String address;

    /**
     * 1. 처리완료
     * 2. 처리중
     * 3. 처리실패
     * 4. 삭제
     */
    @Column(name = "status",nullable = false)
    private Byte status;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference("orders_user_id")
    private User user;

    @OneToMany(mappedBy = "orders",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference("review_order_id")
    private List<Review> reviews;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("order_detail_order_id")
    private List<OrderDetail> orderDetails;

    @OneToOne(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("payment_order_id")
    private Payment payment;

}
