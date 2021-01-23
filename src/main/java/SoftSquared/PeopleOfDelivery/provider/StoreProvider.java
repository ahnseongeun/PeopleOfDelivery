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
     *     private final Long id;
     *     private final String name;
     *     private final String phoneNumber;
     *     private final String description;
     *     private final Integer lowBoundPrice;
     *     private final Integer deliveryFee;
     *     //TODO
     *     private final boolean choiceCheck;
     *     private final Integer reviewCount;
     *     private final Integer hostReviewCount;
     *     private final Float totalStarAverage;
     *     private final Integer pickStoreCount;
     *     private final List<Menu> menuList;
     */
    /*
            memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    //s – the String that contains a detailed message
                    throw new IllegalStateException("이미 존재하는회원이다");
                });
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
                .reviewCount(3) //
                .hostReviewCount(5)
                .totalStarAverage((float) 3.8)
                .pickStoreCount(10)
                //.menuList()
                .build();
    }
}
