package com.ivandev.gestion_alumnos.controller;

import com.ivandev.gestion_alumnos.dto.LoginDTO;
import com.ivandev.gestion_alumnos.dto.LoginResponseDTO;
import com.ivandev.gestion_alumnos.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}