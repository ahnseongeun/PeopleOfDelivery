package SoftSquared.PeopleOfDelivery.config;

import SoftSquared.PeopleOfDelivery.domain.store.Store;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
//    @Getter
//    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
//    @Temporal(TIMESTAMP)
    @CreationTimestamp
    @Column(name = "createdTime", nullable = false, updatable = false)
    private Date createdTime;

//    @Getter
//    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
//    @Temporal(TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updatedTime", nullable = false)
    private Date updatedTime;



//    @PrePersist
//    void prePersist() {
//        this.createdAt = this.updatedAt = new Date();
//    }
//
//    @PreUpdate
//    void preUpdate() {
//        this.updatedAt = new Date();
//    }
}