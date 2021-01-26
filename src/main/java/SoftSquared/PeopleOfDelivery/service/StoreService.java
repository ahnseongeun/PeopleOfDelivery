package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.store.PostStoreRes;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import SoftSquared.PeopleOfDelivery.provider.StoreProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;
import static SoftSquared.PeopleOfDelivery.config.secret.Secret.FILE_UPLOAD_DIRECTORY;

@Service
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreProvider storeProvider;
    private final UserRepository userrepository;

    @Autowired
    public StoreService(StoreRepository storeRepository, StoreProvider storeProvider,
                        UserRepository userrepository) {
        this.storeRepository = storeRepository;
        this.storeProvider = storeProvider;
        this.userrepository = userrepository;
    }

    /**
     * 가게 가입
     * @param name
     * @param phoneNumber
     * @param location
     * @param lowBoundPrice
     * @param deliveryFee
     * @param description
     * @param userId
     * @param imageFile
     * @return PostStoreRes
     * @throws BaseException
     * @throws IOException
     */
    @Transactional
    public PostStoreRes createStore(String name, String phoneNumber, String location, Integer lowBoundPrice,
                                    Integer deliveryFee,String description, Long userId, MultipartFile imageFile)
            throws BaseException, IOException {

        User user = userrepository.findById(userId)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_USER));

        //이미지가 없을 경우 default 경로
        String imageURL = null;
        log.info(String.valueOf(imageFile));

        if(imageFile == null){
            imageURL = FILE_UPLOAD_DIRECTORY +"/stores/default";
        }else{
            String filename = imageFile.getOriginalFilename();
            imageURL = FILE_UPLOAD_DIRECTORY + "/stores/" + name+phoneNumber+filename;
            //TODO
            //imageFile.transferTo(new File(imageURI));
        }

        Store store = Store.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .location(location)
                .lowBoundPrice(lowBoundPrice)
                .deliveryFee(deliveryFee)
                .description(description)
                .user(user)
                .imageURL(imageURL)
                .status(1)
                .build();

        //store 정보 저장
        Store newStore;
        try{
            newStore = storeRepository.save(store);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_STORE);
        }

        //PostStoreRes 반환
        return PostStoreRes.builder()
                .id(newStore.getId())
                .name(newStore.getName())
                .phoneNumber(newStore.getPhoneNumber())
                .location(newStore.getLocation())
                .lowBoundDelivery(newStore.getLowBoundPrice())
                .deliveryFee(newStore.getDeliveryFee())
                .description(newStore.getDescription())
                .user(newStore.getUser())
                .imageURL(newStore.getImageURL())
                .build();
    }

}
