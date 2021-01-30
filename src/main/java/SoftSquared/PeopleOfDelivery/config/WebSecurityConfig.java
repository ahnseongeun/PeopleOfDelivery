package SoftSquared.PeopleOfDelivery.config;

import SoftSquared.PeopleOfDelivery.utils.JwtAuthenticationFilter;
import SoftSquared.PeopleOfDelivery.utils.JwtService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.Filter;
import java.util.Date;
import java.util.HashMap;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final HashMap<String, Date> tokenRepository;

    public WebSecurityConfig(HashMap<String, Date> tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    protected void configure(HttpSecurity http) throws Exception {
        Filter filter = new JwtAuthenticationFilter(
                authenticationManager(), new JwtService(tokenRepository));
        http
                .cors().disable()
                .csrf().disable()
//                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
//                .antMatchers("/*/login", "/*/users").permitAll() // 가입 및 인증 주소는 누구나 접근가능
//                .and()
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }



}


