package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api")
public class StoreController {

    private final StoreService storeService;
    private final StoreProvider storeProvider;

    @Autowired
    public StoreController(StoreService storeService,StoreProvider storeProvider){
        this.storeProvider = storeProvider;
        this.storeService = storeService;
    }

    /**
     * 상점 전체 조회
     */
    @ResponseBody
    @RequestMapping(value = "stores",method = RequestMethod.GET)
    public List<BaseEntity<GetStoreRes>> getStores{

    }

    /**
     * 상점 상세 조회
     */

    /**
     * 상점 추가
     */

}
