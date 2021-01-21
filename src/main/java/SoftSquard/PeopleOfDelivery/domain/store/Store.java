package SoftSquard.PeopleOfDelivery.domain.store;

import SoftSquard.PeopleOfDelivery.config.BaseEntity;
import SoftSquard.PeopleOfDelivery.domain.categoryStore.CategoryStore;
import SoftSquard.PeopleOfDelivery.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
public class Store extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "lowBoundDelivery", nullable = false)
    private Long lowBoundDelivery;

    @Column(name = "deliveryFee", nullable = false)
    private Long deliveryFee;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "imageURI", nullable = false)
    private String imageURI;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("CategoryStore_store_id")
    private List<CategoryStore> products;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("menu_store_id")
    private List<Store> storeList;

    @ManyToOne
    @JsonBackReference("store_user_id")
    @JoinColumn(name = "user_id")
    private User user;

}
