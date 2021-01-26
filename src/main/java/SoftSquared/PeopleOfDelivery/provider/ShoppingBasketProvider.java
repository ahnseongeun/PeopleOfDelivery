package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.menu.MenuRepository;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.*;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
public class ShoppingBasketProvider {

    private final ShoppingBasketRepository shoppingBasketRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public ShoppingBasketProvider(ShoppingBasketRepository shoppingBasketRepository,
                                  UserRepository userRepository,
                                  MenuRepository menuRepository){
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
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

        ShoppingBasket shoppingBasketStore;

        if(shoppingBasketList.size() == 0) {
            throw new BaseException(EMPTY_MENU);
        }
        shoppingBasketStore = shoppingBasketList.get(0);

        return GetShoppingBasketRes.builder()
                .storeName(shoppingBasketStore.getMenu().getStore().getName())
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


    public GetTotalPriceRes retrieveTotalPrice(Long userId) throws BaseException {

        User user = userRepository.findByIdAndStatus(userId,1).orElseThrow(()
                -> new BaseException(FAILED_TO_GET_USER));

        List<ShoppingBasket> shoppingBasketList = shoppingBasketRepository.findByUserAndStatus(user,1);

        if(shoppingBasketList.size() == 0) {
            throw new BaseException(EMPTY_MENU);
        }
        return GetTotalPriceRes.builder()
                .totalPrice(shoppingBasketList.stream()
                        .map(shoppingBasket
                                -> shoppingBasket.getMenu().getPrice() * shoppingBasket.getMenuCount())
                        .mapToInt(value -> value).sum())
                .build();
    }
}
