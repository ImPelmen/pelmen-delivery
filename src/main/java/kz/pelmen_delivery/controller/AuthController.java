package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.request.AuthRequest;
import kz.pelmen_delivery.model.request.RegisterUserRequest;
import kz.pelmen_delivery.model.response.AuthResponse;
import kz.pelmen_delivery.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody @Valid RegisterUserRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.auth(request));
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(AUTHORIZATION_HEADER) String authHeader) {
        authService.logout(authHeader);
        return ResponseEntity.ok().build();
    }
}
