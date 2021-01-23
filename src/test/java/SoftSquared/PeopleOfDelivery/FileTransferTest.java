package SoftSquared.PeopleOfDelivery;

//private final String fileUrl =  System.getProperty("user.home") + "\\files";

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;



@SpringBootTest
@Configuration
public class FileTransferTest {

    @Value("${file.upload.directory}")
    private static String FILE_UPLOAD_DIRECTORY;

    public static void main(String[] args){

        System.out.println(FILE_UPLOAD_DIRECTORY);
    }
}
