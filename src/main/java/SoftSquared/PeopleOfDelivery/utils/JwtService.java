package SoftSquared.PeopleOfDelivery.utils;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.BaseResponseStatus;
import SoftSquared.PeopleOfDelivery.config.secret.Secret;
import SoftSquared.PeopleOfDelivery.domain.user.GetUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.EMPTY_JWT;
import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.INVALID_JWT;


@Service
@Slf4j
public class JwtService {

    private final long ACCESS_TOKEN_VALID_TIME = 1 * 20 * 1000L;   // 1분
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1주

    /**
     * JWT 생성
     * @param userId
     * @return String
     */
    public String createAccessToken(Long userId,Integer role,String name) {
        Date now = new Date();
        long curTime = System.currentTimeMillis();
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role",role)
                .claim("name",name)
                .setIssuedAt(now) //토큰을 만든 시간
                .setExpiration(new Date(curTime + ACCESS_TOKEN_VALID_TIME)) //토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /**
     * Refresh token생성
     * @param userId
     * @return String
     */
    public String createRefreshToken(Long userId,Integer role) {
        Date now = new Date();
        long curTime = System.currentTimeMillis();
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role",role)
                .setIssuedAt(now) //토큰을 만든 시간
                .setExpiration(new Date(curTime + REFRESH_TOKEN_VALID_TIME)) //토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /**
     * Header에서 X-ACCESS-TOKEN 으로 JWT 추출
     * @return String
     */
    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    /**
     * JWT에서 userId 추출
     * @return int
     * @throws BaseException
     */
    public GetUserInfo getUserInfo() throws BaseException {
        // 1. JWT 추출
        String accessToken = getJwt();
        if (accessToken == null || accessToken.length() == 0) {
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
            log.info(claims.toString());
        } catch (Exception exception) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. userInfo 추출
        return GetUserInfo.builder()
                .userid((long)claims.getBody().get("userId", Integer.class))
                .role(claims.getBody().get("role", Integer.class))
                .name(claims.getBody().get("name",String.class))
                .build();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Secret.JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
