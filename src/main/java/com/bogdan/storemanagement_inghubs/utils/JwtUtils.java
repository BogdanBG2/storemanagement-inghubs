package com.bogdan.storemanagement_inghubs.utils;

import com.bogdan.storemanagement_inghubs.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils implements Serializable {
  @Value("${jwt.secret:secret}")
  private static String secret;

  public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60L;

  public static String generateToken(User user) {
    long currentTimeMillis = System.currentTimeMillis();
    Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    return Jwts.builder()
        .setSubject(user.getUsername())
        .claim("role", user.getRole().name())
        .setIssuedAt(new Date(currentTimeMillis))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000L))
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();
  }

  public static <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(getAllClaims(token));
  }

  private static Claims getAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public static boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = getClaim(token, Claims::getSubject);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private static boolean isTokenExpired(String token) {
    final Date expiration = getClaim(token, Claims::getExpiration);
    return expiration.before(new Date());
  }

  private static Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
