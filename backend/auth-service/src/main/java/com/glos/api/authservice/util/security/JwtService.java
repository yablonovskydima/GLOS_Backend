package com.glos.api.authservice.util.security;

import com.glos.api.authservice.entities.SecureCode;
import com.glos.api.authservice.exception.AccessDeniedException;
import com.glos.api.authservice.exception.NoSuchClaimException;
import com.glos.api.authservice.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtProperties jwtProps;
    private final UserDetailsService userDetailsService;
    private SecretKey key;

    public JwtService(
            JwtProperties jwtProps,
            UserDetailsService userDetailsService
    ) {
        this.jwtProps = jwtProps;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProps.getSecret().getBytes());
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(userDetails, new HashMap<>());
    }

    public String generateAccessToken(
            UserDetails userDetails,
            Map<String, Object> extraClaims
    ) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProps.getAccess());

        Set<String> roles = convertToStrRoles(userDetails.getAuthorities());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProps.getRefresh());

        Set<String> roles = convertToStrRoles(userDetails.getAuthorities());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) {
        if(isTokenExpired(refreshToken)) {
            throw new AccessDeniedException("Refresh token is expired");
        }

        final String username = extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        JwtResponse response = new JwtResponse();
        response.setAccessToken(generateAccessToken(userDetails));
        response.setAccessToken(generateRefreshToken(userDetails));

        return response;
    }

    private Set<String> convertToStrRoles(Collection<? extends GrantedAuthority> authorities) {
        Set<? extends GrantedAuthority> authoritiesSet = (Set<? extends GrantedAuthority>)authorities;
        return authoritiesSet.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        List<String> rolesList = (List<String>)extractClaim(token, claims -> claims.get("roles"));
        Set<String> roles = new HashSet<>(rolesList);

        Set<String> userDetailsRoles = convertToStrRoles(userDetails.getAuthorities());

        return userDetails.getUsername().equals(username) &&
                userDetailsRoles.equals(roles) &&
                !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        final Date now = new Date();
        return extractExpiration(token).before(now);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token) {
        Claims claims = extractAllClaims(token);
        return true;
    }

    public String generateShared(SecureCode secureCode) {
        return Jwts.builder()
                .claim(Constants.CLAIM_ROOT_FULL_NAME, secureCode.getResourcePath())
                .claim(Constants.CLAIM_CODE, secureCode.getCode())
                .issuedAt(convertLocalDateTimeToDate(secureCode.getCreationDate()))
                .expiration(convertLocalDateTimeToDate(secureCode.getExpirationDate()))
                .signWith(key)
                .compact();
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    public boolean validateSharedToken(String sharedToken) {
        Claims claims = extractAllClaims(sharedToken);
        final String rootFullName = claims.get(Constants.CLAIM_ROOT_FULL_NAME, String.class);
        final String code = claims.get(Constants.CLAIM_CODE, String.class);
        if (rootFullName == null || code == null) {
            throw new NoSuchClaimException("invalid token claims");
        }
        return true;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
