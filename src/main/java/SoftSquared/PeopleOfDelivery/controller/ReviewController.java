package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.provider.ReviewProvider;
import SoftSquared.PeopleOfDelivery.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewProvider reviewProvider;

    @Autowired
    public ReviewController(ReviewService reviewService,
                            ReviewProvider reviewProvider) {
        this.reviewService = reviewService;
        this.reviewProvider = reviewProvider;
    }
}
