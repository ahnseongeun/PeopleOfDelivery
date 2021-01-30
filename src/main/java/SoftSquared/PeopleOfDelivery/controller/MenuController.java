package SoftSquared.PeopleOfDelivery.controller;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponse;
import SoftSquared.PeopleOfDelivery.domain.menu.DeleteMenuRes;
import SoftSquared.PeopleOfDelivery.domain.menu.GetMenuRes;
import SoftSquared.PeopleOfDelivery.domain.menu.GetMenusRes;
import SoftSquared.PeopleOfDelivery.domain.menu.PostMenuRes;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.provider.MenuProvider;
import SoftSquared.PeopleOfDelivery.provider.StoreProvider;
import SoftSquared.PeopleOfDelivery.service.MenuService;
import SoftSquared.PeopleOfDelivery.service.StoreService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;

@Slf4j
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
    public BaseResponse<List<GetMenusRes>> getMenus(
            Authentication authentication
    ){

        List<GetMenusRes> getMenusResList;

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            long userId = claims.get("userId",Integer.class);

            log.info("전체 메뉴 조회");

            if(role != 100) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            getMenusResList = menuProvider.retrieveMenuList();
            return new BaseResponse<>(SUCCESS_READ_MENUS, getMenusResList);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 메뉴 상세 조회
     */
    @ResponseBody
    @RequestMapping(value = "/menus/{menuId}",method = RequestMethod.GET)
    @ApiOperation(value = "메뉴 상세 조회 (회원 기능)", notes = "메뉴 상세 조회하기")
    public BaseResponse<GetMenuRes> getMenus(
            @PathVariable Long menuId){
        GetMenuRes getMenuRes;
        try{
            getMenuRes = menuProvider.retrieveMenu(menuId);
            return new BaseResponse<>(SUCCESS_READ_MENU, getMenuRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 메뉴 추가
     */
    @ResponseBody
    @RequestMapping(value = "/menus",method = RequestMethod.POST)
    @ApiOperation(value = "메뉴 추가하기 (가게주인)", notes = "메뉴 추가하기")
    public BaseResponse<PostMenuRes> CreateMenu(
            Authentication authentication,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "price") Integer price,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "storeId") Long storeId,
            @RequestParam(value = "imageFile",required = false) MultipartFile imageFile) throws IOException{

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            long userId = claims.get("userId",Integer.class);

            log.info("메뉴 추가 하기");

            if(role != 50) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            PostMenuRes postMenuRes = menuService.createMenu(
                    name,price,description,storeId,imageFile
            );

            return new BaseResponse<>(SUCCESS_POST_MENU, postMenuRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 메뉴 수정
     */
    @ResponseBody
    @RequestMapping(value = "/menus/{menuId}",method = RequestMethod.PATCH)
    @ApiOperation(value = "메뉴 수정하기", notes = "메뉴 수정하기")
    public BaseResponse<PostMenuRes> UpdateMenu(
            @PathVariable Long menuId,
            Authentication authentication,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "price") Integer price,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "storeId") Long storeId,
            @RequestParam(value = "popularCheck") Integer popularCheck,
            @RequestParam(value = "imageFile",required = false) MultipartFile imageFile) throws IOException{

        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            long userId = claims.get("userId",Integer.class);

            log.info("메뉴 수정");

            if(role != 50) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }
            
            PostMenuRes postMenuRes = menuService.updateMenu(
                    menuId,name,price,popularCheck,description,storeId,imageFile
            );
            return new BaseResponse<>(SUCCESS_UPDATE_MENU, postMenuRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 메뉴 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/menus/{menuId}",method = RequestMethod.DELETE)
    @ApiOperation(value = "메뉴 삭제하기", notes = "메뉴 삭제하기")
    public BaseResponse<DeleteMenuRes> DeleteMenu(
            @PathVariable Long menuId,
            Authentication authentication,
            @RequestParam(name = "imageStatus",required = false,defaultValue = "false") boolean imageStatus
            ) throws BaseException {

        DeleteMenuRes deleteMenuRes;
        try{

            if(authentication == null){
                throw new BaseException(EMPTY_AUTHENTICATION);
            }
            Claims claims= (Claims) authentication.getPrincipal();
            int role = claims.get("role", Integer.class);
            long userId = claims.get("userId",Integer.class);

            log.info("메뉴 삭제");

            if(role != 50) {
                throw new BaseException(FAILED_TO_GET_AUTHENTICATION);
            }

            deleteMenuRes = menuService.deleteMenu(
                    menuId , imageStatus
            );
            return new BaseResponse<>(SUCCESS_DELETE_MENU, deleteMenuRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
