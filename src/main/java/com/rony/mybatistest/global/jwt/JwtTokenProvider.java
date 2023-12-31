import com.rony.mybatistest.domain.service.UsersService;
import com.rony.mybatistest.domain.users.entitiy.Users;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_VALID_PERIOD =  1000L * 60 * 60 * 24 * 8;
    private final Key jwtSecretKey;
    private final UsersService usersService;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey,
                            final UsersService usersService) {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        jwtSecretKey = Keys.hmacShaKeyFor(keyBytes);

        this.usersService = usersService;
    }

    public TokenResponse generateJWT(final Users userInfo) {
        final Date now = new Date();
        final Date accessTokenExpireIn = new Date(now.getTime() + ACCESS_TOKEN_VALID_PERIOD);

        final String accessToken = Jwts.builder()
                .setSubject("authorization")
                .claim("userUuid", userInfo.getUserUuid())
                .claim("role", userInfo.getUserRole())
                .setExpiration(accessTokenExpireIn)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();

        return TokenResponse.of(accessToken,accessTokenExpireIn.getTime());
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            LogWriteUtils.logInfo(String.format("exception : %s, message : 잘못된 JWT 서명입니다.", e.getClass().getName()));
        } catch (ExpiredJwtException e) {
            LogWriteUtils.logInfo(String.format("exception : %s, message : 만료된 JWT 토큰입니다.", e.getClass().getName()));
        } catch (UnsupportedJwtException e) {
            LogWriteUtils.logInfo(String.format("exception : %s, message : 지원되지 않는 JWT 토큰입니다.", e.getClass().getName()));
        } catch (IllegalArgumentException e) {
            LogWriteUtils.logInfo(String.format("exception : %s, message : JWT 토큰이 잘못되었습니다.", e.getClass().getName()));
        }
        return false;
    }

    public Claims parseClaims(final String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(jwtSecretKey).build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Authentication getAuthentication(final String token) {
        // 토큰 복호화
        Claims claims = parseClaims(token);
        LogWriteUtils.logInfo("token_claims : " + claims.toString());

        if (claims.get("role") == null) {
            throw new BadCredentialsException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        final Collection<? extends GrantedAuthority> authorities = Stream.of(
                        claims.get("role").toString())
                .map(SimpleGrantedAuthority::new)
                .toList();

        final String userUuid = claims.get("userUuid").toString();

        //token 에 담긴 정보에 맵핑되는 User 정보 디비에서 조회
        final Users user = usersService.findByUserUuidForAuthToken(userUuid);

        //Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(user, userUuid, authorities);
    }
}