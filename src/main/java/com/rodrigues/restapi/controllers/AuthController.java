package com.rodrigues.restapi.controllers;

import com.rodrigues.restapi.data.vo.v1.security.AccountCredentialsVO;
import com.rodrigues.restapi.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private boolean checkParams(AccountCredentialsVO data) {
        return data == null || data.getUsername() == null ||
                data.getUsername().isBlank() || data.getPassword() == null || data.getPassword().isBlank();
    }

    private static boolean checkParams(String username, String refreshToken) {
        return username == null || username.isBlank() || refreshToken == null || refreshToken.isBlank();
    }

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Authenticates an user and return a token")
    @PostMapping("/signing")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
        if (checkParams(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        var token = authService.sigin(data);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        return token;
    }

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Refresh token for authenticated user and return a new token")
    @PutMapping("/refresh/{username}")
    public ResponseEntity refreshToken(
            @PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {

        if (checkParams(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        var token = authService.refreshToken(username, refreshToken);

        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        return token;
    }
}
