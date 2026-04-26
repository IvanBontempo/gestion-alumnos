package com.ivandev.gestion_alumnos.security;

import com.ivandev.gestion_alumnos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> new org.springframework.security.core.userdetails.User(
                        usuario.getEmail(),
                        usuario.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }
}