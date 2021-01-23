package SoftSquared.PeopleOfDelivery.service;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.domain.store.PostStoreRes;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import SoftSquared.PeopleOfDelivery.provider.StoreProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.NOT_FOUND_USER;

@Service
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

    @Transactional
    public PostStoreRes createStore(String name, String phoneNumber, String location, Integer lowBoundDelivery,
                                    Integer deliveryFee,String description, Long userId, MultipartFile imageFile)
                                    throws BaseException {

        User user = userrepository.findById(userId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));

        //이미지가 없을 경우 default 경로
        String imageURI = null;

//        if(imageFile == null){
//            imageURI = "/img/stores/default";
//        }else{
//            String filename = imageFile.getOriginalFilename();
//            String filePath = fileUrl + "\\" + filename;
//            file.transferTo(new File(filePath));
//            imageURI =
//        }

        Store store = Store.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .location(location)
                .lowBoundDelivery(lowBoundDelivery)
                .deliveryFee(deliveryFee)
                .description(description)
                .user(user)
                //.imageURI(image)
                .build();
        return null;
    }
}
