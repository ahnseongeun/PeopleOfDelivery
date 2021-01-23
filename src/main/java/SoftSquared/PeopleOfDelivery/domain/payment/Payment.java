package SoftSquared.PeopleOfDelivery.domain.payment;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
import SoftSquared.PeopleOfDelivery.domain.order.Orders;
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
@Table(name = "payment")
public class Payment extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * pg사 이름
     */
    @Column(name = "pg_name", nullable = false)
    private String pgName;

    /**
     * 결제방법
     */
    @Column(name = "pg_type", nullable = false)
    private String pgType;

    /**
     * 결제내용
     */
    @Column(name = "pg_data", nullable = false,columnDefinition = "TEXT")
    private String pgData;

    /**
     * 결제금액
     */
    @Column(name = "pg_price", nullable = false)
    private Long pgPrice;

    @OneToOne
    @JsonBackReference("payment_order_id")
    @JoinColumn(name = "order_id",nullable = false)
    private Orders orders;

    /**
     * 1은 처리완료, 2는 삭제
     */
    @Column(name = "status",nullable = false)
    private Byte status;


}
