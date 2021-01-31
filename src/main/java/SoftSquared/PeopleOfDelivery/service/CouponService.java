package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponseStatus;
import SoftSquared.PeopleOfDelivery.domain.coupon.Coupon;
import SoftSquared.PeopleOfDelivery.domain.coupon.CouponRepository;
import SoftSquared.PeopleOfDelivery.domain.coupon.GetCouponRes;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

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


    /**
     * 내 쿠폰 수정하기
     *
     * @param operationCheck
     * @param coupon1000Count
     * @param coupon3000Count
     * @param coupon5000Count
     * @return
     * @throws BaseException
     */
    public GetCouponRes updateCoupon(Long userId,
                                     boolean operationCheck,
                                     Integer coupon1000Count,
                                     Integer coupon3000Count,
                                     Integer coupon5000Count) throws BaseException {


        User user = userRepository.findByIdAndStatus(userId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_USER));

        Coupon coupon = couponRepository.findByUser(user)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_COUPON));

        if(!coupon.getUser().getId().equals(userId))
            throw new BaseException(NOT_EQUAL_HOST_AND_USER);

        if(coupon1000Count > 0){
            if(operationCheck) { //0은 플러스, 1은 마이너스
                coupon.setCoupon1000(coupon.getCoupon1000() + coupon1000Count);
            }else{
                if(coupon.getCoupon1000() - coupon1000Count > 0){
                    coupon.setCoupon1000(coupon.getCoupon1000() - coupon1000Count);
                }else{
                    coupon.setCoupon1000(0);
                }
            }
        }

        if(coupon3000Count > 0){
            if(operationCheck) { //0은 플러스, 1은 마이너스
                coupon.setCoupon3000(coupon.getCoupon3000() + coupon3000Count);
            }else{
                if(coupon.getCoupon3000() - coupon3000Count > 0){
                    coupon.setCoupon3000(coupon.getCoupon3000() - coupon3000Count);
                }else{
                    coupon.setCoupon3000(0);
                }
            }
        }

        if(coupon5000Count > 0){
            if(operationCheck) { //0은 플러스, 1은 마이너스
                coupon.setCoupon5000(coupon.getCoupon5000() + coupon5000Count);
            }else{
                if(coupon.getCoupon5000() - coupon5000Count > 0){
                    coupon.setCoupon5000(coupon.getCoupon5000() - coupon5000Count);
                }else{
                    coupon.setCoupon5000(0);
                }
            }
        }

        try{
            couponRepository.save(coupon);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_UPDATE_COUPON);
        }

        return GetCouponRes.builder()
                .id(coupon.getId())
                .coupon1000Count(coupon.getCoupon1000())
                .coupon3000Count(coupon.getCoupon3000())
                .coupon5000Count(coupon.getCoupon5000())
                .userId(coupon.getUser().getId())
                .build();
    }

    public GetCouponRes deleteCoupon(Long userId,
                                     boolean coupon1000Count,
                                     boolean coupon3000Count,
                                     boolean coupon5000Count) throws BaseException {

        User user = userRepository.findByIdAndStatus(userId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_USER));

        Coupon coupon = couponRepository.findByUser(user)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_COUPON));

        if(!coupon.getUser().getId().equals(userId))
            throw new BaseException(NOT_EQUAL_HOST_AND_USER);

        if(coupon1000Count){
            coupon.setCoupon1000(0);
        }

        if(coupon3000Count){
            coupon.setCoupon3000(0);
        }

        if(coupon5000Count){
            coupon.setCoupon5000(0);
        }

        try{
            couponRepository.save(coupon);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_DELETE_COUPON);
        }

        return GetCouponRes.builder()
                .id(coupon.getId())
                .coupon1000Count(coupon.getCoupon1000())
                .coupon3000Count(coupon.getCoupon3000())
                .coupon5000Count(coupon.getCoupon5000())
                .userId(coupon.getUser().getId())
                .build();

    }
}
