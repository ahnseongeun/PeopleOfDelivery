package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.menu.GetMenusRes;
import SoftSquared.PeopleOfDelivery.domain.menu.PostMenuRes;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.provider.MenuProvider;
import SoftSquared.PeopleOfDelivery.provider.StoreProvider;
import SoftSquared.PeopleOfDelivery.service.MenuService;
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
public class MenuController {

    private final MenuService menuService;
    private final MenuProvider menuProvider;

    @Autowired
    public MenuController(MenuService menuService, MenuProvider menuProvider){
        this.menuProvider = menuProvider;
        this.menuService = menuService;
    }

    /**
     * 메뉴 전체 조회
     */
    @ResponseBody
    @RequestMapping(value = "/menus",method = RequestMethod.GET)
    @ApiOperation(value = "전체 메뉴 조회 (관리자 기능)", notes = "메뉴 목록 불러오기")
    public BaseResponse<List<GetMenusRes>> getMenus(){

        List<GetMenusRes> getMenusResList;

        try{
            getMenusResList = menuProvider.retrieveMenuList();
            return new BaseResponse<>(SUCCESS_READ_MENUS, getMenusResList);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

//    /**
//     * 메뉴 상세 조회
//     */
//    @ResponseBody
//    @RequestMapping(value = "/menus/{menuId}",method = RequestMethod.GET)
//    @ApiOperation(value = "메뉴 상세 조회 (회원 기능)", notes = "메뉴 상세 조회하기")
//    public BaseResponse<List<GetMenusRes>> getMenus(
//            @PathVariable String menuId){
//
//        List<GetMenusRes> getMenusResList;
//
//        try{
//            getMenusResList = menuProvider.retrieveMenuList();
//            return new BaseResponse<>(SUCCESS_READ_MENUS, getMenusResList);
//        }catch(BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }


    /**
     * 메뉴 추가
     */
    @ResponseBody
    @RequestMapping(value = "/menus",method = RequestMethod.POST)
    @ApiOperation(value = "메뉴 추가하기", notes = "메뉴 추가하기")
    public BaseResponse<PostMenuRes> CreateMenu(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "price") Integer price,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "store-id") Long storeId,
            @RequestParam(value = "image-file",required = false) MultipartFile imageFile) throws IOException{

    try{
            PostMenuRes postMenuRes = menuService.createMenu(
                    name,price,description,storeId,imageFile
            );
            return new BaseResponse<>(SUCCESS_POST_MENU, postMenuRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
