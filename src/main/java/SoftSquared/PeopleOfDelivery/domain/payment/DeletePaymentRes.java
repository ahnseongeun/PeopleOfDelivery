package SoftSquared.PeopleOfDelivery.domain.payment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeletePaymentRes {

    private final Long paymentId;
    private final Integer status;
}
