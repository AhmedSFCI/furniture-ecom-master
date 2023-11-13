/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._helpers;

import com.furniture.ecom._model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.websocket.Decoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author HP
 */
@Component
public class JwtToken {
    
    private static final String SECRET_STRING = "jhfewkrj3454ht7eworihhs9icvhsfiewrhrwjkrhnfoker9er";
    private static final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_STRING));
    private LocalDate localDate = LocalDate.now().plusDays(4);
    public String createToken(Long usrNo, String usrCode, String roleTyp, String usrName, String usrPicture, String email) {
        String jwt = Jwts.builder()
                .setSubject("FurnitureEComApp")
                .claim("usrNo", usrNo + "")
                .claim("usrCode", usrCode)
                .claim("roleTyp", roleTyp)
                .claim("usrNm", usrName)
                .claim("usrPicture", usrPicture)
                .claim("email", email)
                .signWith(key, SignatureAlgorithm.HS256)
 //               .setExpiration(localDate)
                .compact();
        return jwt;
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            assert Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject().equals("FurnitureEComApp");
            System.out.println("Valid Token ");
            return true;
        } catch (MissingClaimException ex) {
            return false;
        } catch (IncorrectClaimException ex) {
            return false;
        } catch (Exception ex) {
            System.out.println("in Valid Token " + ex.getMessage());
            return false;
        }
    }
    
    public LoginUser decodeToken(String token) {
        LoginUser loginUsr;
        Jws<Claims> jws;
        try {
            jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            loginUsr = new LoginUser();
            loginUsr.setEmail((String) jws.getBody().get("email"));
            loginUsr.setUsrNo((long) (Integer.parseInt((String) jws.getBody().get("usrNo"))));
            loginUsr.setUsrNm((String) jws.getBody().get("usrNm"));
            if (jws.getBody().get("roleTyp") != null) {
                loginUsr.setRoleTyp((String) jws.getBody().get("roleTyp"));
            } else {
                loginUsr.setRoleTyp(null);
            }
            if (jws.getBody().get("usrCode") != null) {
                loginUsr.setUsrCode((String) jws.getBody().get("usrCode"));
            } else {
                loginUsr.setUsrCode(null);
            }
            loginUsr.setUsrPicture((String) jws.getBody().get("usrPicture"));
            return loginUsr;
        } catch (JwtException ex) {
            System.out.println("JWT Decode Exception");
            return null;
        }
    }
    
    public String getUserFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
    
    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
