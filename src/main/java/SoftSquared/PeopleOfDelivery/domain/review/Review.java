package SoftSquared.PeopleOfDelivery.domain.review;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
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
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "star_count", nullable = false)
    private Integer starCount;

    /**
     * 1은 사용, 2는 삭제
     */
    @Column(name = "status",nullable = false)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    @JsonBackReference("review_order_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference("review_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id",nullable = false)
    @JsonBackReference("review_store_id")
    private Store store;
}
