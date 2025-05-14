package org.vedruna.controller;

import io.quarkus.security.Authenticated;
import io.smallrye.common.constraint.NotNull;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.vedruna.model.dto.NewEmployeeDTO;
import org.vedruna.model.dto.message.ResponseEntityDTO;
import org.vedruna.repository.EmployeeRepository;

@Path("employee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    EmployeeRepository employeeRepo;

    @GET
    @Authenticated
    public Response getAll() {
        return Response.ok(employeeRepo.listAll()).build();
    }

    @GET
    @Path("{id}")
    @Authenticated
    public Response getOne(@NotNull @PathParam("id") Long id) {
        return Response.ok(employeeRepo.findByIdOptional(id).orElseThrow(NotFoundException::new)).build();
    }

    @POST
    @Authenticated
    public Response newEmployee (@NotNull NewEmployeeDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(new ResponseEntityDTO<>(
                        "Se ha insertado un nuevo empleado correctamente",
                        employeeRepo.create(dto)
                ))
                .build();
    }
}
