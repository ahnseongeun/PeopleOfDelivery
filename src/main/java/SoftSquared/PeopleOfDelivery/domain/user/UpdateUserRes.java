package SoftSquared.PeopleOfDelivery.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class UpdateUserRes {

    private final Long id;
    private final String email;
    private final String phoneNumber;
    private final String imageURL;
    private final String location;

}