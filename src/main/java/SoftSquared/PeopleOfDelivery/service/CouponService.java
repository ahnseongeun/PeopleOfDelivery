package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.domain.coupon.CouponRepository;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CouponService {

    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    @Autowired
    public CouponService(UserRepository userRepository,
                         CouponRepository couponRepository){
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
    }



}
