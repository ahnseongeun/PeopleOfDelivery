package SoftSquared.PeopleOfDelivery.utils;

import SoftSquared.PeopleOfDelivery.config.BaseException;
import SoftSquared.PeopleOfDelivery.config.secret.Secret;
import SoftSquared.PeopleOfDelivery.domain.user.GetUserInfo;
import SoftSquared.PeopleOfDelivery.domain.user.PostLoginRes;
import io.jsonwebtoken.*;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

import static SoftSquared.PeopleOfDelivery.config.BaseResponseStatus.*;


@Service
@Slf4j
public class JwtService {

    private final long ACCESS_TOKEN_VALID_TIME = 1 * 20 * 1000L;   // 1분
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1주
    private final HashMap<String,Date> tokenRepository;

    public JwtService(HashMap<String, Date> tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * JWT 생성
     * @param userId
     * @return String
     */
    public String createAccessToken(Long userId,Integer role) {
        Date now = new Date();
        long curTime = System.currentTimeMillis();
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role",role)
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
        log.info("JWT 검증 시작");
        String accessToken = getJwt();
        if (accessToken == null || accessToken.length() == 0) {
            throw new BaseException(EMPTY_JWT);
        }

        // 1.5 logout 확인
        if(tokenRepository.get(accessToken) != null){
            log.info("로그아웃 확인: " +String.valueOf(tokenRepository.get(accessToken)));
            throw new BaseException(ALREADY_LOGOUT);
        }


        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);

        } catch (ExpiredJwtException exception) {
            log.info("JWT가 만료되었습니다.");
            throw new BaseException(EXPIRED_JWT);
        }catch (Exception e){
            log.info("유효하지 않은 JWT 입니다.");
            throw new BaseException(INVALID_JWT);
        }


        // 3. userInfo 추출
        return GetUserInfo.builder()
                .userid((long)claims.getBody().get("userId", Integer.class))
                .role(claims.getBody().get("role", Integer.class))
                .build();
    }

    public Claims getClaims(String token) throws BaseException {
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(token);

        } catch (ExpiredJwtException exception) {
            log.info("JWT가 만료되었습니다.");
            throw new BaseException(EXPIRED_JWT);
        }catch (Exception e){
            log.info("유효하지 않은 JWT 입니다.");
            throw new BaseException(INVALID_JWT);
        }
        return Jwts.parser()
                .setSigningKey(Secret.JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }



    public PostLoginRes refreshJwt() throws BaseException{
        log.info("Refresh JWT 검증 시작");
        String refreshToken = getJwt();
        if (refreshToken == null || refreshToken.length() == 0) {
            throw new BaseException(EMPTY_JWT);
        }
        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(refreshToken);

        } catch (ExpiredJwtException exception) {
            log.info("Refresh JWT가 만료되었습니다.\n"+ "로그인을 다시 해주세요.");
            throw new BaseException(EXPIRED_REFRESH_JWT);
        }catch (Exception e){
            log.info("유효하지 않은 JWT 입니다.\n"+ "로그인을 다시 해주세요.");
            throw new BaseException(INVAILD_REFRESH_JWT);
        }

        Long userId = (long) claims.getBody().get("userId",Integer.class);
        Integer role = claims.getBody().get("role", Integer.class);

        String accessToken = createAccessToken(userId,role);

        // 3. userInfo 추출
        return PostLoginRes.builder()
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
