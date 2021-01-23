package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.FAILED_TO_GET_STORE;

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
}
