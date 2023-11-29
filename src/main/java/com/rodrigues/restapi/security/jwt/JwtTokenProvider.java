package com.rodrigues.restapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rodrigues.restapi.data.vo.v1.security.TokenVO;
import com.rodrigues.restapi.exceptions.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.Base64;
import java.util.Date;
import java.util.List;


@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1hr

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    /*
    Esse metodo e chamado apos a contextualizacao do Spring,
    depois de iniciar a aplicacao e injetar as dependencias
    e antes de executar qualquer metodo solicatado pelo usuario
     */
    @PostConstruct
    protected void init() {
        //Pega o valor da chave secreta e codifica em Base64
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        //Cria o algoritmo de assinatura
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenVO createAccessToken(String username, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        var accessToken = getAccessToken(username, roles, now, validity);
        var refreshToken = getRefreshToken(username, roles, now);

        return new TokenVO(username, true, now, validity, accessToken, refreshToken);
    }


    public TokenVO refreshToken(String refreshToken) {
        if(refreshToken.contains("Bearer "))
            refreshToken = refreshToken.substring("Bearer ".length());

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(refreshToken);

        String username = decoded.getSubject();
        List<String> roles = decoded.getClaim("roles").asList(String.class);

        return createAccessToken(username, roles);
    }

    private String getRefreshToken(String username, List<String> roles, Date now) {
        //vale por 3 hrs
        Date validity = new Date(now.getTime() + validityInMilliseconds * 3);

        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(username)
                .sign(algorithm)
                .strip();
    }

    private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
        //pega a URL do servidor da aplicacao
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm)
                .strip();
    }


    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());


        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());

        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        //como vem o bearerToken:
        //"Bearer 12174127421847182974893657uiasyhdaskjdghsujkagdias (representacao token)"
        //portanto eh necessario usar o substring para pegar somente o token


        if (bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring("Bearer ".length());

        return null;
    }

    public boolean validateToken(String token) {
        DecodedJWT decodedJWT = decodedToken(token);

        try {
            if (decodedJWT.getExpiresAt().before(new Date())) return false;

            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid token");
        }
    }
}


