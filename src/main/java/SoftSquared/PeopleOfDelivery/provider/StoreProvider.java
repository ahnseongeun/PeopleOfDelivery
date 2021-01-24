package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.categoryStore.CategoryStoreRepository;
import SoftSquared.PeopleOfDelivery.domain.catrgory.Category;
import SoftSquared.PeopleOfDelivery.domain.catrgory.CategoryRepository;
import SoftSquared.PeopleOfDelivery.domain.order.OrdersRepository;
import SoftSquared.PeopleOfDelivery.domain.store.GetDetailStoreRes;
import SoftSquared.PeopleOfDelivery.domain.store.GetStoreRes;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
@Slf4j
public class StoreProvider {

    private final CategoryStoreRepository categoryStoreRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final OrdersRepository ordersRepository;

    @Autowired
    public StoreProvider(CategoryStoreRepository categoryStoreRepository, StoreRepository storeRepository,
                         CategoryRepository categoryRepository, OrdersRepository ordersRepository){
        this.categoryStoreRepository = categoryStoreRepository;
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
        this.ordersRepository = ordersRepository;
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
            throw new BaseException(FAILED_TO_GET_STORES);
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
                .imageURI(store.getImageURI())
                .choiceCheck(true) //userId와 storeId를 이용해서 pick_store에 있는 지 확인 후 true/false
                .reviewCount(store.getReviews().size())
                .hostReviewCount(0) //review에서 제공
                .totalStarAverage((float) 3.8) //review에서 제공
                .pickStoreCount(10) // storeId를 이용해서 pick_store에 있는 지 확인 후 true/false
                //.menuList()
                .build();
    }



    public List<GetStoreRes> retrieveStoreListByOrder(Long categoryId) throws BaseException{

        //TODO 카테고리 더미데이터 넣으면 추가
        //Optional<Category> category = categoryRepository.findById(categoryId);
        //List<Store> storeList = (List<Store>) categoryStoreRepository.findAllByCategory(category);
        List<Store> storeList = (List<Store>) storeRepository.findAll();
        log.info("조회 성공");
        storeList.sort(new Comparator<Store>() {
            @Override
            public int compare(Store o1, Store o2) {
                return o2.getOrders().size()-o1.getOrders().size();
            }
        });
        log.info("sort 성공");
        return storeList.stream().map(store -> {
            Long id = store.getId();
            String name = store.getName();
            String location = store.getLocation();
            Integer lowBoundPrice = store.getLowBoundPrice();
            Integer deliveryFee = store.getDeliveryFee();
            Float totalStarAverage = 5.0F; //가게 평점 구하기
            String imageURI = store.getImageURI();
            return GetStoreRes.builder()
                    .id(id)
                    .name(name)
                    .location(location)
                    .lowBoundPrice(lowBoundPrice)
                    .deliveryFee(deliveryFee)
                    .totalStarAverage(totalStarAverage) //가게 평점 해야된다.
                    .imageURI(imageURI)
                    .build();
        }).collect(Collectors.toList());

    }


    /**
     * deliveryFeeLowBound 이하 출력
     * @param categoryId
     * @return
     */
    public List<GetStoreRes> retrieveStoreListByDeliveryFeeLowBound(Long categoryId, Integer deliveryFeeLowBound)
            throws BaseException{

        //TODO 카테고리 더미데이터 넣으면 추가
        //Optional<Category> category = categoryRepository.findById(categoryId);
        //List<Store> storeList = (List<Store>) categoryStoreRepository.findAllByCategory(category);
        List<Store> storeList = (List<Store>) storeRepository.findByStatus(1,sortByDeliveryFee());
        return storeList.stream()
                .filter(store -> store.getDeliveryFee() <= deliveryFeeLowBound)
                .map(store -> {
            Long id = store.getId();
            String name = store.getName();
            String location = store.getLocation();
            Integer lowBoundPrice = store.getLowBoundPrice();
            Integer storeDeliveryFee = store.getDeliveryFee();
            Float totalStarAverage = 5.0F; //가게 평점 구하기
            String imageURI = store.getImageURI();
            return GetStoreRes.builder()
                    .id(id)
                    .name(name)
                    .location(location)
                    .lowBoundPrice(lowBoundPrice)
                    .deliveryFee(storeDeliveryFee)
                    .totalStarAverage(totalStarAverage) //가게 평점 해야된다.
                    .imageURI(imageURI)
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * deliveryFee 낮은순으로 모두 출력
     * @param categoryId
     * @return
     */
    public List<GetStoreRes> retrieveStoreListByDeliveryFeeDESC(Long categoryId) throws BaseException {
        Sort sort = sortByDeliveryFee();
        //TODO 카테고리 더미데이터 넣으면 추가
        //Optional<Category> category = categoryRepository.findById(categoryId);
        //List<Store> storeList = (List<Store>) categoryStoreRepository.findAllByCategory(category);
        List<Store> storeList = (List<Store>) storeRepository.findByStatus(1,sort);

        return storeList.stream()
                .map(store -> {
                    Long id = store.getId();
                    String name = store.getName();
                    String location = store.getLocation();
                    Integer lowBoundPrice = store.getLowBoundPrice();
                    Integer storeDeliveryFee = store.getDeliveryFee();
                    Float totalStarAverage = 5.0F; //가게 평점 구하기
                    String imageURI = store.getImageURI();
                    return GetStoreRes.builder()
                            .id(id)
                            .name(name)
                            .location(location)
                            .lowBoundPrice(lowBoundPrice)
                            .deliveryFee(storeDeliveryFee)
                            .totalStarAverage(totalStarAverage) //가게 평점 해야된다.
                            .imageURI(imageURI)
                            .build();
                }).collect(Collectors.toList());
    }

    private Sort sortByDeliveryFee() {
        return Sort.by(Sort.Direction.DESC, "deliveryFee");
    }
}

