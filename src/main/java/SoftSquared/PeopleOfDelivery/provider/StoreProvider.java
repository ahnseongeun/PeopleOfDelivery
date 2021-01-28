package SoftSquared.PeopleOfDelivery.provider;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.categoryStore.CategoryStoreRepository;
import SoftSquared.PeopleOfDelivery.domain.catrgory.CategoryRepository;
import SoftSquared.PeopleOfDelivery.domain.menu.GetMenuRes;
import SoftSquared.PeopleOfDelivery.domain.order.OrdersRepository;
import SoftSquared.PeopleOfDelivery.domain.review.Review;
import SoftSquared.PeopleOfDelivery.domain.review.ReviewRepository;
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
import java.util.stream.Collectors;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Service
@Slf4j
public class StoreProvider {

    private final CategoryStoreRepository categoryStoreRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final OrdersRepository ordersRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public StoreProvider(CategoryStoreRepository categoryStoreRepository, StoreRepository storeRepository,
                         CategoryRepository categoryRepository, OrdersRepository ordersRepository,
                         ReviewRepository reviewRepository){
        this.categoryStoreRepository = categoryStoreRepository;
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
        this.ordersRepository = ordersRepository;
        this.reviewRepository = reviewRepository;
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

        Long hostId = store.getUser().getId();
        int reviewCount = reviewRepository.findByStoreAndStatus(store, 1).size();
        int hostReviewCount = reviewRepository.findByStoreAndUserAndStatus(store,hostId, 1).size();
        return GetDetailStoreRes.builder()
                .id(store.getId())
                .name(store.getName())
                .phoneNumber(store.getPhoneNumber())
                .description(store.getDescription())
                .lowBoundPrice(store.getLowBoundPrice())
                .deliveryFee(store.getDeliveryFee())
                .imageURL(store.getImageURL())
                .choiceCheck(true) //userId와 storeId를 이용해서 pick_store에 있는 지 확인 후 true/false
                .userReviewCount(reviewCount - hostReviewCount)
                .hostReviewCount(hostReviewCount) //review에서 제공
                //.totalStarAverage((float) 3.8) //review에서 제공
                .pickStoreCount(10) // storeId를 이용해서 pick_store에 있는 있는 개수 만큼 count
                .menuList(store.getMenus().stream()
                        .filter(menu -> menu.getStatus() == 1) //Status가 1인 것만 출력
                        .map(menu -> GetMenuRes.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .description(menu.getDescription())
                        .storeId(menu.getStore().getId())
                        .imageURL(menu.getImageURL())
                        .imageStatus(menu.getImageStatus())
                        .popularStatus(menu.getPopularCheck())
                        .build()).collect(Collectors.toList()))
                .build();
    }



    /**
     * 주문 많은순으로 조회
     * @return GetStoreRes
     * @throws BaseException
     */
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
        return getGetStoreRes(storeList);

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
        List<Store> storeList =
                (List<Store>) storeRepository.findByStatusAndDeliveryFeeLessThanEqual(1,deliveryFeeLowBound,sortByDeliveryFee());
        return getGetStoreRes(storeList);
        /*
        return storeList.stream()
                //.filter(store -> store.getDeliveryFee() <= deliveryFeeLowBound)
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
        */
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

        return getGetStoreRes(storeList);
    }

    /**
     * 최소 주문 금액 이상 조회 오름차순
     * @param categoryId
     * @param lowBoundPrice
     * @return
     */
    public List<GetStoreRes> retrieveStoreListByLowBoundPrice(Long categoryId, Integer lowBoundPrice) throws BaseException {
        Sort sort = sortByLowBoundPrice();

        //TODO 카테고리 더미데이터 넣으면 추가
        //Optional<Category> category = categoryRepository.findById(categoryId);
        //List<Store> storeList = (List<Store>) categoryStoreRepository.findAllByCategory(category);
        List<Store> storeList =
                (List<Store>) storeRepository.findByStatusAndLowBoundPriceGreaterThanEqual(1,lowBoundPrice,sort);

        return getGetStoreRes(storeList);

    }


    private List<GetStoreRes> getGetStoreRes(List<Store> storeList) {
        return storeList.stream().map(store -> {
            Long id = store.getId();
            String name = store.getName();
            String location = store.getLocation();
            Integer lowBoundPrice = store.getLowBoundPrice();
            Integer deliveryFee = store.getDeliveryFee();
            Float totalStarAverage = 5.0F; //가게 평점 구하기
            String imageURL = store.getImageURL();
            return GetStoreRes.builder()
                    .id(id)
                    .name(name)
                    .location(location)
                    .lowBoundPrice(lowBoundPrice)
                    .deliveryFee(deliveryFee)
                    .totalStarAverage(totalStarAverage) //가게 평점 해야된다.
                    .imageURL(imageURL)
                    .build();
        }).collect(Collectors.toList());
    }

    private Sort sortByDeliveryFee() {
        return Sort.by(Sort.Direction.ASC, "deliveryFee");
    }

    private Sort sortByLowBoundPrice() {
        return Sort.by(Sort.Direction.ASC, "lowBoundPrice");
    }

}

