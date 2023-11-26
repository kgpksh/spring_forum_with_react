package learning.practice.spring_forum_with_react.base.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtProvider {
    private static final long TOKEN_DURATION  = 1000L * 60 * 60 * 3;
    private static final String USERNAME_KEY = "username";

    public static String createJwt(String username, String secretKey) {
        return Jwts.builder()
                .claim(USERNAME_KEY, username)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static boolean isExprired(String token, String secretKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (ExpiredJwtException exception) {
            return true;
        }
    }

    public static String getUsername(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(USERNAME_KEY, String.class);
    }
}
