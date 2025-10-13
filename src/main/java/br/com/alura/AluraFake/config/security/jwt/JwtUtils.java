package br.com.alura.AluraFake.config.security.jwt;

import br.com.alura.AluraFake.user.model.User;
import br.com.alura.AluraFake.user.service.helper.UserServiceHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final UserServiceHelper userServiceHelper;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiritationTime;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String password) {
        User user = userServiceHelper.findValidUserByEmail(email, password);

        return Jwts.builder()
                .setSubject(email)
                .claim("role", user.getRole().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiritationTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    protected boolean isValidToken(String token){
        String email = this.extractEmail(token);
        userServiceHelper.findValidUserByEmail(email, null);

        return true;
    }

    protected String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    protected String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
