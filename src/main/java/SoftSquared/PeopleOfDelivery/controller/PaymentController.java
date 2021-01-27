package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.provider.PaymentProvider;
import SoftSquared.PeopleOfDelivery.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentProvider paymentProvider;

    @Autowired
    public PaymentController(PaymentService paymentService,
                             PaymentProvider paymentProvider){

        this.paymentService = paymentService;
        this.paymentProvider = paymentProvider;
    }

}
