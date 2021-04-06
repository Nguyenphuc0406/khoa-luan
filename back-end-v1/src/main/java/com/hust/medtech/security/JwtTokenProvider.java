package com.hust.medtech.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Slf4j
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "lodaaaaaa";

    // Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 100000L;

    // Tạo ra jwt từ thông tin user
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

//        customer.setToken(UUID.randomUUID().toString());
//        customer.setExpiresDate(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)));
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(String.valueOf(userDetails.getAccount().getUsername()))
                .setIssuedAt(now)
                .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();

    }

    // Lấy thông tin user từ jwt
    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

        return String.valueOf(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {

        } catch (ExpiredJwtException ex) {

        } catch (UnsupportedJwtException ex) {

        } catch (IllegalArgumentException ex) {

        }
        return false;
    }
}
