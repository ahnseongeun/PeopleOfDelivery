package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.coupon.Coupon;
import SoftSquared.PeopleOfDelivery.domain.coupon.CouponRepository;
import SoftSquared.PeopleOfDelivery.domain.coupon.GetCouponRes;
import SoftSquared.PeopleOfDelivery.domain.menu.GetMenusRes;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.FAILED_TO_GET_COUPON;
import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.FAILED_TO_GET_MENUS;

@Service
public class CouponProvider {

    private final CouponRepository couponRepository;

    @Autowired
    public CouponProvider(CouponRepository couponRepository){

        this.couponRepository = couponRepository;
    }

    /**
     * 전체 쿠폰 조회
     * @return GetCouponRes
     * @throws BaseException
     */
    public List<GetCouponRes> retrieveCouponList()  throws BaseException {

        List<Coupon> couponList;
        try{
            //삭제를 제외 하고 조회
            couponList = (List<Coupon>) couponRepository.findAll();
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_GET_COUPON);
        }

        return couponList.stream().map(coupon -> GetCouponRes.builder()
                .id(coupon.getId())
                .coupon1000Count(coupon.getCoupon1000())
                .coupon3000Count(coupon.getCoupon3000())
                .coupon5000Count(coupon.getCoupon5000())
                .userId(coupon.getUser().getId())
                .build())
                .collect(Collectors.toList());
    }

    /**
     * 내 쿠폰 조회
     * @return GetCouponRes
     * @throws BaseException
     */
    public GetCouponRes retrieveCoupon(Long userId) throws BaseException {
        Coupon coupon;

            coupon = couponRepository.findById(userId)
                    .orElseThrow(() -> new BaseException(FAILED_TO_GET_COUPON));


        return GetCouponRes.builder()
                .id(coupon.getId())
                .coupon1000Count(coupon.getCoupon1000())
                .coupon3000Count(coupon.getCoupon3000())
                .coupon5000Count(coupon.getCoupon5000())
                .userId(coupon.getUser().getId())
                .build();
    }
}
