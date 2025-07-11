package ccpetrov01.userapplication.Security;

import ccpetrov01.userapplication.Users.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(UserEntity user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        String role = user.getRole().name();
        String fullRole = "ROLE_" + role;

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", fullRole)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserEntity userDetails) {
        final String email = extractEmail(token);
        return email.equals(userDetails.getEmail()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
