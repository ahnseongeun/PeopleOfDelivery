package SoftSquared.PeopleOfDelivery.utils;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.EMPTY_JWT;
import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.INVALID_JWT;


@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private final JwtService jwtService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtService jwtService) {
        super(authenticationManager);

        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        log.info("33");
        Authentication authentication = null;
        try {
            authentication = getAuthentication(request);

        } catch (BaseException baseException) {
            baseException.printStackTrace();
        }
        //JWT 검사
        if(authentication!=null) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }

        chain.doFilter(request,response);

    }

    private Authentication getAuthentication(HttpServletRequest request) throws BaseException{
        log.info("44");
        String token= request.getHeader("Authorization");
        if(token == null){
            return null;
        }
        Claims claims;
        try {
            //log.info("getClaims "+jwtService.getClaims(token));
            log.info("getJWT "+jwtService.getJwt());
            //claims = jwtService.getJwt();
            claims = jwtService.getClaims(token);
            log.info("getClaims "+jwtService.getClaims(token));
        } catch (JwtException e) {
            throw new BaseException(INVALID_JWT);
        }
        return new UsernamePasswordAuthenticationToken(claims,null);
    }
}
