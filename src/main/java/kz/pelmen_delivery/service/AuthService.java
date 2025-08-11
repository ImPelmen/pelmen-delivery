package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.request.AuthRequest;
import kz.pelmen_delivery.model.request.RegisterUserRequest;
import kz.pelmen_delivery.model.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterUserRequest request);

    AuthResponse auth(AuthRequest request);

    void logout(String authHeader);
}
