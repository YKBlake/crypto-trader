package com.ykb.app.cryptotrader.auth.api;

import com.ykb.app.cryptotrader.auth.dto.JwtDto;
import com.ykb.app.cryptotrader.auth.service.JwtService;
import com.ykb.app.cryptotrader.data.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {

    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthApi(JwtService jwtService, AuthenticationManager authManager) {
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    @PostMapping("/sessions")
    public ResponseEntity<JwtDto> authenticate(@RequestParam String username, @RequestParam String password) {
        Assert.hasText(username, "Given username is blank");
        Assert.hasText(password, "Given password is blank");
        Authentication auth = authManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(username, password));
        return ResponseEntity.ok(jwtService.generateAuthenticationTokens((User) auth.getPrincipal()));
    }

    @GetMapping("/accessTokens")
    public ResponseEntity<String> retrieveAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated())
            throw new RuntimeException("");
        return ResponseEntity.ok(jwtService.generateAccessToken((User) authentication.getPrincipal()));
    }

}
