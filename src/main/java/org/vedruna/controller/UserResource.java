//
// Copyright - Metadata S.L. - Todos los Derechos Reservados - Documento Confidencial [CON]
//

package org.vedruna.controller;

import java.util.ArrayList;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.vedruna.model.User;
import org.vedruna.model.dto.UserResponseDTO;
import org.vedruna.repository.UserRepository;
import io.quarkus.security.Authenticated;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository userRepo;

    @GET
    @Authenticated
    public Response getAll() {
        List<User> users = userRepo.listAll();
        List<UserResponseDTO> usersDTO = new ArrayList<>();

        if (users.isEmpty()) return Response.noContent().build();

        for (User u : users) {
            usersDTO.add(new UserResponseDTO(u.getId(), u.getEmail(), u.getEmployee().getId()));
        }

        return Response.ok(usersDTO).build();
    }
}
