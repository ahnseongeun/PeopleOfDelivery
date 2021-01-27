package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseEntity;
import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.store.*;
import SoftSquared.PeopleOfDelivery.domain.user.DeleteUserRes;
import SoftSquared.PeopleOfDelivery.domain.user.GetUserRes;
import SoftSquared.PeopleOfDelivery.domain.user.PostUserRes;
import SoftSquared.PeopleOfDelivery.provider.StoreProvider;
import SoftSquared.PeopleOfDelivery.service.StoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @RequestMapping(value = "/stores",method = RequestMethod.GET)
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
     * 상점 목록 조회
     * 1. 카테고리
     * 2. 주문 많은 순, 별점 높은 순, 배달팁 낮은순
     *    최소 주문 금액(1000 , 3000, 5000) 이상
     *    배달팁(1000,2000,3000) 이상
     *    별점(3, 4 ,4.5 이상)
     */
    @ResponseBody
    @RequestMapping(value = "/store-list/{categoryId}" , method = RequestMethod.GET)
    @ApiOperation(value = "상점 목록 조회", notes = "상점 목록 조회")
    public BaseResponse<List<GetStoreRes>> getDetailStores(
            @PathVariable Long categoryId,
            @RequestParam(value = "order",required = false,defaultValue = "true") boolean orderDescend,
            @RequestParam(value = "star",required = false,defaultValue = "false") boolean starDescend,
            @RequestParam(value = "deliveryfee",required = false,defaultValue = "false") boolean deliveryFeeAscend,
            @RequestParam(value = "LowBoundPrice",required = false,defaultValue = "0") Integer lowBoundPrice,
            @RequestParam(value = "delivery-fee-low-bound",required = false,defaultValue = "0") Integer deliveryFeeLowBound,
            @RequestParam(value = "star-low-bound",required = false,defaultValue = "0") Integer starLowBound) {

        List<GetStoreRes> getStoresList;

        if(orderDescend){ //주문 많은 순
            try{
                getStoresList = storeProvider.retrieveStoreListByOrder(categoryId);
                return new BaseResponse<>(SUCCESS_READ_STORES_BY_ORDER, getStoresList);
            }catch(BaseException exception){
                return new BaseResponse<>(exception.getStatus());
            }
        }

        //TODO 리뷰 추가 하면 같이 하기
//        if(starDescend){ //별점 높은 순
//            try{
//                getStoresList = storeProvider.retrieveStoreListByStar(categoryId);
//                return new BaseResponse<>(SUCCESS_READ_DETAIL_STORES, getStoresList);
//            }catch(BaseException exception){
//                return new BaseResponse<>(exception.getStatus());
//            }
//        }

        if(deliveryFeeAscend){ //배달팁이 낮은 순으로 모든 조회
            try{
                getStoresList = storeProvider.retrieveStoreListByDeliveryFeeDESC(categoryId);
                return new BaseResponse<>(SUCCESS_READ_STORES_BY_DELIVERY_FEE_ASC, getStoresList);
            }catch(BaseException exception){
                return new BaseResponse<>(exception.getStatus());
            }
        }

        if(lowBoundPrice > 0){ //최소 주문 금액(1000 , 3000, 5000) 이상
            try{
                getStoresList = storeProvider.retrieveStoreListByLowBoundPrice(categoryId, lowBoundPrice);
                return new BaseResponse<>(SUCCESS_READ_STORES_BY_LOW_BOUND_PRICE_ASC, getStoresList);
            }catch(BaseException exception){
                return new BaseResponse<>(exception.getStatus());
            }
        }

        if(deliveryFeeLowBound > 0){ //배달팁 기준 금액 이하
            try{
                getStoresList = storeProvider.retrieveStoreListByDeliveryFeeLowBound(categoryId,deliveryFeeLowBound);
                return new BaseResponse<>(SUCCESS_READ_STORES_BY_DeliveryFeeLowBound, getStoresList);
            }catch(BaseException exception){
                return new BaseResponse<>(exception.getStatus());
            }
        }
//
//        if(starLowBound > 0){ //별점(3, 4 ,4.5 이상)
//            try{
//                getStoresList = storeProvider.retrieveStoreListByStarLowBound(categoryId, starLowBound);
//                return new BaseResponse<>(SUCCESS_READ_DETAIL_STORES, getStoresList);
//            }catch(BaseException exception){
//                return new BaseResponse<>(exception.getStatus());
//            }
//        }

        return null;
    }


    /**
     * 상점 상세 조회
     *
     */
    @ResponseBody
    @RequestMapping(value = "/stores/{storeId}" , method = RequestMethod.GET)
    @ApiOperation(value = "상점 상세 조회", notes = "상점 상세 조회")
    public BaseResponse<GetDetailStoreRes> getDetailStore(
            @PathVariable Long storeId) {
        try{
            GetDetailStoreRes getDetailStoreRes = storeProvider.retrieveDetailStore(storeId);
            return new BaseResponse<>(SUCCESS_READ_DETAIL_STORES, getDetailStoreRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 상점 추가
     */
    @ResponseBody
    @RequestMapping(value = "/stores",method = RequestMethod.POST)
    @ApiOperation(value = "상점 추가 (가게 주인)", notes = "상점 추가")
    public BaseResponse<PostStoreRes> createStore (
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phone-number") String phoneNumber,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "low-bound-price") Integer lowBoundPrice,
            @RequestParam(value = "delivery-fee") Integer deliveryFee,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "user-id") Long userId,
            @RequestParam(value = "image-file",required = false) MultipartFile imageFile) throws IOException{

        try {
            PostStoreRes postStoreRes = storeService.createStore(
                    name,phoneNumber,location,lowBoundPrice,deliveryFee,description,userId,imageFile);
            return new BaseResponse<>(SUCCESS_POST_STORE, postStoreRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 상점 수정
     */
    @ResponseBody
    @RequestMapping(value = "/stores/{storeId}",method = RequestMethod.PATCH)
    @ApiOperation(value = "상점 수정 (가게 주인)", notes = "상점 수정")
    public BaseResponse<PostStoreRes> updateStore (
            @PathVariable Long storeId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phoneNumber") String phoneNumber,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "lowBoundPrice") Integer lowBoundPrice,
            @RequestParam(value = "deliveryFee") Integer deliveryFee,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "imageFile",required = false) MultipartFile imageFile) throws IOException{

        try {
            PostStoreRes postStoreRes = storeService.updateStore(
                    storeId,name,phoneNumber,location,lowBoundPrice,deliveryFee,description,userId,imageFile);
            return new BaseResponse<>(SUCCESS_UPDATE_STORE, postStoreRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 상점 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/stores/{storeId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "상점 삭제(가게점주 기능)", notes = "상점 삭제")
    public BaseResponse<DeleteStoreRes> DeleteUser(
            @PathVariable Long storeId) throws BaseException{

        // 2. Post UserInfo
        try {
            DeleteStoreRes deleteStoreRes = storeService.deleteStore(storeId);
            return new BaseResponse<>(SUCCESS_DELETE_STORE, deleteStoreRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
