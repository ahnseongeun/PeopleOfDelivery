package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.menu.MenuRepository;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.PostShoppingBasketRes;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasket;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.ShoppingBasketRepository;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
public class ShoppingBasketService {

    private final ShoppingBasketRepository shoppingBasketRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public ShoppingBasketService(ShoppingBasketRepository shoppingBasketRepository,
                                  UserRepository userRepository,
                                  MenuRepository menuRepository){
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    public PostShoppingBasketRes createShoppingBasket(Long userId, Long menuId)
            throws BaseException {

        User user = userRepository.findByIdAndStatus(userId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_USER));

        Menu menu = menuRepository.findByIdAndStatusNot(menuId,10)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_MENU));

        ShoppingBasket shoppingBasket = ShoppingBasket.builder()
                .user(user)
                .menu(menu)
                .menuCount(1) //default는 1
                .status(1)
                .build();

        ShoppingBasket newShoppingBasket;

        try{
            newShoppingBasket = shoppingBasketRepository.save(shoppingBasket);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_SHOPPING_BASKET);
        }

        return PostShoppingBasketRes.builder()
                .shoppingBasketId(newShoppingBasket.getId())
                .userId(newShoppingBasket.getUser().getId())
                .menuId(newShoppingBasket.getMenu().getId())
                .menuCount(newShoppingBasket.getMenuCount())
                .build();

    }
}
