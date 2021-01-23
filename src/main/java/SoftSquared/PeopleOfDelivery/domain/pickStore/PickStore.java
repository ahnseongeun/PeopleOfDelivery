package SoftSquared.PeopleOfDelivery.domain.pickStore;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
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
@Table(name = "pick_store")
public class PickStore extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 1은 사용중
     * 2는 삭제
     */
    @Column(name = "status",nullable = false)
    private Byte status;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference("pick_store_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id",nullable = false)
    @JsonBackReference("pick_store_store_id")
    private Store store;
}
