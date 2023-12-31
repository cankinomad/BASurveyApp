package com.bilgeadam.basurveyapp.configuration.jwt;

import com.bilgeadam.basurveyapp.exceptions.custom.UndefinedTokenException;
import com.bilgeadam.basurveyapp.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Eralp Nitelik
 */
@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private static final long timeToExpire = 432000000L; // 5days

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + timeToExpire)) // 5days
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateMailToken(String email, Long surveyOid) {
        return "emailToken";
    }
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return isTokenNotExpired(jwtToken) && username.equals(userDetails.getUsername());
    }

    private boolean isTokenNotExpired(String jwtToken) {
        return extractExpiration(jwtToken).after(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*
        Email Related Token Methods!
     */
    public String generateSurveyEmailToken(Long userId,Long surveyOid, Long studentTagOid, String userEmail, Integer day) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("surveyOid", surveyOid);
        claims.put("studentTagOid",studentTagOid);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + calculateDayMiliseconds(day)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isSurveyEmailTokenValid(String jwtToken) {
        final String email = extractEmail(jwtToken);
        return !isTokenNotExpired(jwtToken) || userRepository.findActiveUserByEmail(email).isEmpty();
    }

    public String extractEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public Long extractSurveyOid(String jwtToken) {
        final Claims claims = extractAllClaims(jwtToken);
        return Long.valueOf(claims.get("surveyOid").toString());
    }
    public Long extractStudentTagOid(String jwtToken) {
        final Claims claims = extractAllClaims(jwtToken);
        return Long.valueOf(claims.get("studentTagOid").toString());
    }

    private Long calculateDayMiliseconds(Integer day) {
        return day * 86_400_000L;
    }

    public Optional<Long> getSurveyIdFromToken(String jwtToken) {
        try {
            final Claims claims = extractAllClaims(jwtToken);
            return Optional.of(Long.valueOf(claims.get("surveyOid").toString()));
        }catch(Exception e){
            throw new UndefinedTokenException("Invalid token.");
        }
    }

    public Optional<Long> getUserIdFromToken(String jwtToken) {
        try {
            final Claims claims = extractAllClaims(jwtToken);
            return Optional.of(Long.valueOf(claims.get("userId").toString()));
        }catch(Exception e){
            throw new UndefinedTokenException("Invalid token.");
        }
    }

    public Optional<Long> getStudentTagOidFromToken(String jwtToken) {
        try {
            final Claims claims = extractAllClaims(jwtToken);
            return Optional.of(Long.valueOf(claims.get("studentTagOid").toString()));
        }catch(Exception e){
            throw new UndefinedTokenException("Invalid token.");
        }
    }

}
