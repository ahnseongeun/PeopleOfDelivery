package SoftSquared.PeopleOfDelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;

@Configuration
public class MyConfig {

    @Bean
    public HashMap<String, Date> tokenRepository(){
        HashMap<String, Date> map = new HashMap<String, Date>();
        return map;
    }


}
