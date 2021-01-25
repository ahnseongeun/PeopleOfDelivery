package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.GetShoppingBasketMenuRes;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.GetShoppingBasketRes;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasket;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasketRepository;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
public class ShoppingBasketProvider {

    private final ShoppingBasketRepository shoppingBasketRepository;
    private final UserRepository userRepository;

    @Autowired
    public ShoppingBasketProvider(ShoppingBasketRepository shoppingBasketRepository,
                                  UserRepository userRepository){
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.userRepository = userRepository;
    }

    /**
     * 회원 장바구니 조회
     * @param userId
     * @return
     */
    /*
        private final String storeName;
    private final Integer menuCount;
    private final Long userId;
    private final List<GetMenuRes> getMenuResList;
    private final Integer totalPrice;
     */
    public GetShoppingBasketRes retrieveShoppingBasket(Long userId) throws BaseException{
        User user = userRepository.findByIdAndStatus(userId,1).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_USER));

        List<ShoppingBasket> shoppingBasketList = shoppingBasketRepository.findByUserAndStatus(user,1);
        ShoppingBasket shoppingBasketStore = shoppingBasketList.get(0);

        return GetShoppingBasketRes.builder()
                .storeName(shoppingBasketStore.getMenu().getStore().getName())
                .totalPrice(10000)
                .getShoppingBasketMenuResList(shoppingBasketList.stream().map(shoppingBasket ->
                        GetShoppingBasketMenuRes.builder()
                                .id(shoppingBasket.getMenu().getId())
                                .name(shoppingBasket.getMenu().getName())
                                .price(shoppingBasket.getMenu().getPrice())
                                .storeId(shoppingBasket.getMenu().getStore().getId())
                                .menuCount(shoppingBasket.getMenuCount())
                                .build()).collect(Collectors.toList()))
                .userId(shoppingBasketStore.getUser().getId())
                .build();
    }
}
