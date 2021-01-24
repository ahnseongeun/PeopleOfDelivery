package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.categoryStore.CategoryStoreRepository;
import SoftSquared.PeopleOfDelivery.domain.catrgory.CategoryRepository;
import SoftSquared.PeopleOfDelivery.domain.menu.GetMenusRes;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.menu.MenuRepository;
import SoftSquared.PeopleOfDelivery.domain.order.OrdersRepository;
import SoftSquared.PeopleOfDelivery.domain.store.GetStoreRes;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
@Slf4j
public class MenuProvider {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuProvider(MenuRepository menuRepository){
        this.menuRepository = menuRepository;
    }

    /**
     * 전체 메뉴 조회
     * @return menuList
     * @throws BaseException
     */
    public List<GetMenusRes> retrieveMenuList() throws BaseException {

        List<Menu> menuList;
        try{
            //삭제를 제외 하고 조회
            menuList = menuRepository.findByStatusNot(10);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_GET_MENUS);
        }

        return menuList.stream().map(menu -> GetMenusRes.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .describe(menu.getDescription())
                .imageURL(menu.getImageURL())
                .storeId(menu.getStore().getId())
                .build())
                .collect(Collectors.toList());
    }
}
