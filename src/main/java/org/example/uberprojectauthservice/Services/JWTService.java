package org.example.uberprojectauthservice.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    //This method creates a brand-new JWT token for us based on a payload
    private String createToken(Map<String,Object>payload, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+expiry*1000L);

        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(username)
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private Claims extractAllPayloads(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims=extractAllPayloads(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token){
       return extractClaim(token, Claims::getExpiration);
    }

    private String extractEmail(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private boolean isTokenExpired(String token){
         return extractExpiration(token).before(new Date());
    }

    private String extractPhoneNumber(String token){
        Claims claims=extractAllPayloads(token);
        String number=(String) claims.get("phoneNumber");
        return number;
    }

    private String extractEmailFromClaim(String token){
        Claims claims=extractAllPayloads(token);
        String email=(String) claims.get("email");
        return email;
    }

    private Object extractPayload(String token, String payloadKey){
        Claims claims=extractAllPayloads(token);
        return (Object) claims.get(payloadKey);
    }

    private boolean validateToken(String token, String email){
          final String userEmailFetchedFromToken= extractEmail(token);
          return (userEmailFetchedFromToken.equals(email)) && !isTokenExpired(token);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> mp=new Hashtable<>();
        mp.put("email","a@b.com");
        mp.put("phoneNumber","999999999");
        String result=createToken(mp,"Nandini@123");
        System.out.println("Generated token is: "+result);
        System.out.println("Phone number is: " + extractPhoneNumber(result));
        System.out.println(extractEmailFromClaim(result));

        System.out.println("Email is: " + extractPayload(result,"email").toString());
        System.out.println("username fetched from token: "+extractEmail(result));
        System.out.println("Token validation: "+validateToken(result,"Nandini@123"));
    }
}
