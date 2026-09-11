package com.huellavet.reservas.security;

import com.huellavet.reservas.model.AdministradorModel;
import com.huellavet.reservas.model.Rol;
import com.huellavet.reservas.model.UsuarioModel;
import com.huellavet.reservas.model.VeterinarioModel;
import com.huellavet.reservas.repository.AdministradorRepository;
import com.huellavet.reservas.repository.UsuarioRepository;
import com.huellavet.reservas.repository.VeterinarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserService {

    private final UsuarioRepository usuarioRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final AdministradorRepository administradorRepository;

    public AuthenticatedUserService(UsuarioRepository usuarioRepository,
                                    VeterinarioRepository veterinarioRepository,
                                    AdministradorRepository administradorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.administradorRepository = administradorRepository;
    }
    public String obtenerCorreoActual() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("No hay un usuario autenticado en esta request");
        }
        return authentication.getName();
    }
    public Rol obtenerRolActual() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String authority = authentication.getAuthorities().iterator().next().getAuthority();
        String rolTexto = authority.replace("ROLE_", "");
        return Rol.valueOf(rolTexto);
    }
    public Long obtenerIdUsuarioActual() {
        if (obtenerRolActual() != Rol.USUARIO) {
            throw new IllegalStateException("El rol actual no es USUARIO");
        }
        UsuarioModel usuario = usuarioRepository.findByEmailIgnoreCase(obtenerCorreoActual())
                .orElseThrow(() -> new IllegalStateException("Usuario del token no encontrado en la BD"));
        return usuario.getId();
    }

    public Long obtenerIdVeterinarioActual() {
        if (obtenerRolActual() != Rol.VETERINARIO) {
            throw new IllegalStateException("El rol actual no es VETERINARIO");
        }
        VeterinarioModel veterinario = veterinarioRepository.findByCorreoIgnoreCase(obtenerCorreoActual())
                .orElseThrow(() -> new IllegalStateException("Veterinario del token no encontrado en la BD"));
        return veterinario.getId();
    }

    public boolean esAdministrador() {
        return obtenerRolActual() == Rol.ADMINISTRADOR;
    }
}