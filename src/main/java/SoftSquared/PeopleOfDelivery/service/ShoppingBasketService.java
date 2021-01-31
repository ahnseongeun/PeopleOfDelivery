package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.menu.MenuRepository;
import SoftSquared.PeopleOfDelivery.domain.shoppingBasket.*;
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

    /**
     * 장바구니에 메뉴 추가
     * @param userId
     * @param menuId
     * @return
     * @throws BaseException
     */
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

    /**
     * 장바구니 수정
     * @param basketId
     * @param count
     * @param operationCheck
     * @return
     * @throws BaseException
     */
    public UpdateShoppingBasketRes updateShoppingBasket(Long userId,
                                                        Long basketId,
                                                        boolean operationCheck) throws BaseException{

        ShoppingBasket shoppingBasket = shoppingBasketRepository.findByIdAndStatus(basketId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_BASKET));

        if(!shoppingBasket.getUser().getId().equals(userId))
            throw new BaseException(NOT_EQUAL_RQEUSTUSER_AND_BASKETUSER);


        int menuCount = shoppingBasket.getMenuCount();

        if(operationCheck) {//플러스
            menuCount += 1;
            shoppingBasket.setMenuCount(menuCount);
        }else{
            if(menuCount > 1) {
                menuCount -= 1;
                shoppingBasket.setMenuCount(menuCount);
            }
        }

        try{
            shoppingBasketRepository.save(shoppingBasket);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_UPDATE_BASKET);
        }

        return UpdateShoppingBasketRes.builder()
                .shoppingBasketId(shoppingBasket.getId())
                .menuId(shoppingBasket.getMenu().getId())
                .menuName(shoppingBasket.getMenu().getName())
                .menuCount(shoppingBasket.getMenuCount())
                .totalMenuPrice(shoppingBasket.getMenu().getPrice()*(menuCount))
                .build();
    }

    /**
     * 장바구니 삭제
     * @param basketId
     * @return
     */
    public DeleteShoppingBasketRes deleteShoppingBasket(Long userId,
                                                        Long basketId) throws BaseException {

        ShoppingBasket shoppingBasket = shoppingBasketRepository.findByIdAndStatus(basketId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_BASKET));

        if(!shoppingBasket.getUser().getId().equals(userId))
            throw new BaseException(NOT_EQUAL_RQEUSTUSER_AND_BASKETUSER);

        shoppingBasket.setStatus(2);

        try{
            shoppingBasketRepository.save(shoppingBasket);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_DELETE_BASKET);
        }

        return DeleteShoppingBasketRes.builder()
                .shoppingBasketId(shoppingBasket.getId())
                .status(shoppingBasket.getStatus())
                .build();
    }
}
