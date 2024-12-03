package io.agileintelligence.ppmtool.security;

import io.agileintelligence.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.*;

import static io.agileintelligence.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static io.agileintelligence.ppmtool.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String userId = Long.toString(user.getId());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());
        claims.put("user", user);

        return Jwts.builder()
                .setSubject(userId)
//                claims - info about user
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact(); // generate a token when username & password are valid
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT Token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT Token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }

        return false;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
        Object id = claims.get("id");
        return new Long((String) id); // Long.parseLong(id);
    }

    public User getUserFromJWT(String token) {
        LinkedHashMap<String, Object> claimsUserData = (LinkedHashMap<String, Object>) Jwts.parser().setSigningKey(SECRET).build().parseClaimsJws(token).getBody().get("user");

        User user = new User();
        user.setId(Long.valueOf(claimsUserData.get("id").toString()));
        user.setUsername((String) claimsUserData.get("username"));
        user.setPassword((String) claimsUserData.get("password"));
        user.setFullName((String) claimsUserData.get("fullName"));
//        user.setCreate_At((Date) claimsUserData.get("create_At"));
//        user.setUpdate_At((Date) claimsUserData.get("update_At"));
        return user;
    }
}
