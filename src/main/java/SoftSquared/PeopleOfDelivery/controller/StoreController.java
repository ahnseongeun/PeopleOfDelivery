package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.store.PostStoreRes;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.user.GetUserRes;
import SoftSquared.PeopleOfDelivery.domain.user.PostUserRes;
import SoftSquared.PeopleOfDelivery.provider.StoreProvider;
import SoftSquared.PeopleOfDelivery.service.StoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

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
     * 전체 조회
     */
    @ResponseBody
    @RequestMapping(value = "stores",method = RequestMethod.GET)
    @ApiOperation(value = "전체 상점 조회 (관리자 기능)", notes = "상점 목록 불러오기")
    public BaseResponse<List<Store>> getStores(){
        try{
            List<Store> getStoresList = storeProvider.retrieveStoreList();
            return new BaseResponse<>(SUCCESS_READ_STORES, getStoresList);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 상점 상세 조회
     * 1. 카테고리
     * 2. 주문 많은 순, 별점 높은 순, 배달팁 낮은순
     *    최소 주문 금액(1000 , 3000, 5000)
     *    배달팁(1000,2000,3000이상)
     *    별점(3, 4 ,4.5 이상)
     */

    /**
     * 상점 추가
     */
    @ResponseBody
    @RequestMapping(value = "stores",method = RequestMethod.POST)
    @ApiOperation(value = "상점 추가 (가게 주인)", notes = "상점 추가")
    public BaseResponse<PostStoreRes> createStore(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phoneNumber") String phoneNumber,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "lowBoundDelivery") Integer lowBoundDelivery,
            @RequestParam(value = "deliveryFee") Integer deliveryFee,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "imageFile",required = false) MultipartFile imageFile){

//        try {
//            PostStoreRes postStoreRes = storeService.createStore(
//                    name,phoneNumber,location,lowBoundDelivery,deliveryFee,description,userId,imageFile);
//            return new BaseResponse<>(SUCCESS_POST_STORE, postStoreRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
        return null;
    }


}
