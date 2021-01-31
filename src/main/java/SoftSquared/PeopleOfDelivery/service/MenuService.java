package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.menu.DeleteMenuRes;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.menu.MenuRepository;
import SoftSquared.PeopleOfDelivery.domain.menu.PostMenuRes;
import SoftSquared.PeopleOfDelivery.domain.store.PostStoreRes;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;
import static SoftSquared.PeopleOfDelivery.config.secret.Secret.FILE_UPLOAD_DIRECTORY;

@Service
@Slf4j
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, StoreRepository storeRepository){
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
    }


    public PostMenuRes createMenu(String name, Integer price, String description,
                                  Long storeId, MultipartFile imageFile) throws BaseException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_STORE));

        //이미지가 없을 경우 default 경로
        String imageURL;
        int imageStatus;
        log.info("이미지 파일: " + String.valueOf(imageFile));
        if(imageFile == null){
            imageStatus = 2; //사용안함
            imageURL = FILE_UPLOAD_DIRECTORY +"/menu/default";
        }else{
            imageStatus = 1; //사용함
            String filename = imageFile.getOriginalFilename();
            imageURL = FILE_UPLOAD_DIRECTORY + "/menus/" + name+price+filename;
            //TODO
            //imageFile.transferTo(new File(imageURI));
        }

        Menu menu = Menu.builder()
                .name(name)
                .price(price)
                .description(description)
                .popularCheck(1)
                .imageStatus(imageStatus)
                .imageURL(imageURL)
                .store(store)
                .status(1)
                .build();

        //Menu 정보 저장
        Menu newMenu;
        try{
            newMenu = menuRepository.save(menu);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_MENU);
        }

        //PostMenuRes 반환
        return PostMenuRes.builder()
                .id(newMenu.getId())
                .name(newMenu.getName())
                .price(newMenu.getPrice())
                .description(newMenu.getDescription())
                .imageURL(newMenu.getImageURL())
                .storeId(store.getId())
                .imageStatus(newMenu.getImageStatus())
                .build();
    }

    public PostMenuRes updateMenu(Long menuId,
                                  Long userId,
                                  String name,
                                  Integer price,
                                  Integer popularCheck,
                                  String description,
                                  Long storeId,
                                  MultipartFile imageFile) throws BaseException  {

        Menu menu = menuRepository.findByIdAndStatus(menuId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_MENU));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_STORE));

        if(!store.getUser().getId().equals(userId))
            throw new BaseException(NOT_EQUAL_HOST_AND_USER);
        //이미지가 없을 경우 default 경로
        String imageURL;
        int imageStatus;

        log.info("이미지 파일: " + String.valueOf(imageFile));

        if(imageFile == null){
            imageStatus = 2; //사용안함
            imageURL = FILE_UPLOAD_DIRECTORY +"/menu/default";
            menu.setImageURL(imageURL);
            menu.setImageStatus(2) ; //사용안함
            
        }else{
            imageStatus = 1; //사용함
            String filename = imageFile.getOriginalFilename();
            imageURL = FILE_UPLOAD_DIRECTORY + "/menus/" + name+price+filename;
            //TODO
            //imageFile.transferTo(new File(imageURI));
            menu.setImageURL(imageURL);
            menu.setImageStatus(1) ; //사용함
        }

        menu.setName(name)
                .setPrice(price)
                .setPopularCheck(popularCheck)
                .setDescription(description)
                .setStore(store);

        Menu newMenu;
        try{
            newMenu = menuRepository.save(menu);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_UPDATE_MENU);
        }

        //MenuRes 반환
        return PostMenuRes.builder()
                .id(newMenu.getId())
                .name(newMenu.getName())
                .price(newMenu.getPrice())
                .description(newMenu.getDescription())
                .imageURL(newMenu.getImageURL())
                .storeId(newMenu.getStore().getId())
                .imageStatus(newMenu.getImageStatus())
                .build();

    }

    public DeleteMenuRes deleteMenu(Long menuId, Long userId ,boolean imageStatus) throws BaseException {

        Menu menu = menuRepository.findByIdAndStatus(menuId,1)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_MENU));

        if(!menu.getStore().getUser().getId().equals(userId))
            throw new BaseException(NOT_EQUAL_HOST_AND_USER);

        if(imageStatus){
            menu.setImageStatus(2);
            return getDeleteMenuRes(menu);
        }

        menu.setStatus(2);

        return getDeleteMenuRes(menu);

    }

    private DeleteMenuRes getDeleteMenuRes(Menu menu) throws BaseException {

        Menu newMenu;
        try {
            newMenu = menuRepository.save(menu);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_DELETE_MENU);
        }

        return DeleteMenuRes.builder()
                .menuId(newMenu.getId())
                .MenuStatus(newMenu.getStatus())
                .MenuImageStatus(newMenu.getImageStatus())
                .build();
    }
}
