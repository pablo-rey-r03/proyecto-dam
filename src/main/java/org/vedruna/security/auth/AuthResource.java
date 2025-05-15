package org.vedruna.security.auth;

import java.util.Optional;

import io.smallrye.common.constraint.NotNull;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.vedruna.model.User;
import org.vedruna.model.dto.LoginDTO;
import org.vedruna.model.dto.RegisterDTO;
import org.vedruna.model.dto.message.TokenResponse;
import org.vedruna.model.dto.message.ResponseDTO;
import org.vedruna.model.dto.message.ResponseEntityDTO;
import org.vedruna.security.JWTUtil;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    public Response register(@NotNull RegisterDTO registerDTO) {
        if (registerDTO == null) throw new IllegalArgumentException("Debe proporcionar la información del usuario para ser registrado.");
        return Response.status(Response.Status.CREATED).entity(new ResponseEntityDTO<>("Se ha registrado correctamente como empleado.", authService.register(registerDTO))).build();
    }

    @POST
    @Path("/login")
    public Response login(@NotNull LoginDTO loginDTO) {
        if (loginDTO == null) throw new IllegalArgumentException("Las credenciales son obligatorias.");

        Optional<User> userOptional = authService.findByEmail(loginDTO.getEmail());
        if (userOptional.isPresent() && authService.verifyPassword(loginDTO.getPassword(), userOptional.get().getPassword())) {
            String token = JWTUtil.generateToken(userOptional.get());
            return Response.ok(new TokenResponse(token)).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity(new ResponseDTO("Las credenciales son incorrectas.")).build();
    }
}
