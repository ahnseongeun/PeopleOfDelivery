package SoftSquared.PeopleOfDelivery.domain.payment;


import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Builder
@AllArgsConstructor
public class GetPaymentRes {

    private Long id;
    private String pgName;
    private String pgType;
    private String pgData;
    private Integer pgPrice;
    private Long orderId;

}
