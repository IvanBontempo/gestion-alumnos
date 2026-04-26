package com.ivandev.gestion_alumnos.service;

import com.ivandev.gestion_alumnos.dto.LoginDTO;
import com.ivandev.gestion_alumnos.dto.LoginResponseDTO;
import com.ivandev.gestion_alumnos.exception.ResourceNotFoundException;
import com.ivandev.gestion_alumnos.repository.UsuarioRepository;
import com.ivandev.gestion_alumnos.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public LoginResponseDTO login(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());

        String token = jwtService.generateToken(userDetails);

        var usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return LoginResponseDTO.builder()
                .token(token)
                .nombre(usuario.getNombre())
                .rol(usuario.getRol().name())
                .build();
    }
}