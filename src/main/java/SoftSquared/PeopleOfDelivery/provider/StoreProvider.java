package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.store.GetDetailStoreRes;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
public class StoreProvider {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreProvider(StoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    /**
     * 전체 상점 조회
     * @return storeList
     * @throws BaseException
     */
    public List<Store> retrieveStoreList() throws BaseException {

        List<Store> storeList;
        try{
            storeList = storeRepository.findByStatus(1);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_GET_STORE);
        }
        return storeList;
    }

    /**
     * 상점 상세 조회
     * @return GetDetailStoreRes
     * @throws BaseException

     */
    public GetDetailStoreRes retrieveDetailStore(Long storeId) throws BaseException{
        Store store = storeRepository.findByIdAndStatus(storeId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_DETAIL_STORE));

        return GetDetailStoreRes.builder()
                .id(store.getId())
                .name(store.getName())
                .phoneNumber(store.getPhoneNumber())
                .description(store.getDescription())
                .lowBoundPrice(store.getLowBoundPrice())
                .deliveryFee(store.getDeliveryFee())
                .choiceCheck(true) //userId와 storeId를 이용해서 pick_store에 있는 지 확인 후 true/false
                .reviewCount(store.getReviews().size())
                .hostReviewCount(0) //review에서 제공
                .totalStarAverage((float) 3.8) //review에서 제공
                .pickStoreCount(10) // storeId를 이용해서 pick_store에 있는 지 확인 후 true/false
                //.menuList()
                .build();
    }
}
