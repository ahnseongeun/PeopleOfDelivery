package SoftSquard.PeopleOfDelivery.domain.order;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.store.Store;
import SoftSquard.PeopleOfDelivery.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "order")
public class Order extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_content",columnDefinition = "TEXT")
    private String request_content;

    @Column(name = "address")
    private String address;

    @Column(name = "status",nullable = false)
    private Byte status;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference("Order_user_id")
    private User user;

}
