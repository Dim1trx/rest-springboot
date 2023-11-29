package com.rodrigues.restapi.services;

import com.rodrigues.restapi.data.vo.v1.security.AccountCredentialsVO;
import com.rodrigues.restapi.data.vo.v1.security.TokenVO;
import com.rodrigues.restapi.model.User;
import com.rodrigues.restapi.repositories.UserRepository;
import com.rodrigues.restapi.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private final JwtTokenProvider tokenProvider;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public AuthService(JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }


    @SuppressWarnings("rawtypes")
    public ResponseEntity sigin(AccountCredentialsVO data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = userRepository.findByUsername(username);

            var tokenResponse = new TokenVO();

            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            }

            else {
                throw new UsernameNotFoundException("Username " + username + " not found");
            }


            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {
        var tokenResponse = new TokenVO();

        var user = userRepository.findByUsername(username);

        if (user != null) {
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        }

        else {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        return ResponseEntity.ok(tokenResponse);
    }

}
