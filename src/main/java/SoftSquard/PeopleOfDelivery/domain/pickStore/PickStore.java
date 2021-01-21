package SoftSquard.PeopleOfDelivery.domain.pickStore;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.catrgory.Category;
import SoftSquard.PeopleOfDelivery.domain.store.Store;
import SoftSquard.PeopleOfDelivery.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "pick_store")
public class PickStore extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status",nullable = false)
    private Byte status;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference("PickStore_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id",nullable = false)
    @JsonBackReference("PickStore_store_id")
    private Store store;
}
