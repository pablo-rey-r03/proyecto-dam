package org.vedruna.controller;

import io.quarkus.security.Authenticated;
import io.smallrye.common.constraint.NotNull;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.vedruna.model.Employee;
import org.vedruna.model.dto.NewEmployeeDTO;
import org.vedruna.model.dto.message.ResponseDTO;
import org.vedruna.model.dto.message.ResponseEntityDTO;
import org.vedruna.repository.EmployeeRepository;

import java.util.List;

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
        return Response.ok(employeeRepo.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Empleado no encontrado"))).build();
    }

    @GET
    @Path("company/{id}")
    @Authenticated
    public Response getByCompany(@NotNull @PathParam("id") Long id) {
        List<Employee> employees = employeeRepo.getByCompany(id);

        if (employees.isEmpty()) return Response
                .status(Response.Status.NO_CONTENT)
                .entity(new ResponseDTO(
                        "No hay empleados en dicha empresa"
                ))
                .build();

        return Response.ok(employees).build();
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

    @PUT
    @Path("{id}")
    @Authenticated
    public Response updateEmployee (@NotNull @PathParam("id") Long id, @NotNull NewEmployeeDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(new ResponseEntityDTO<>(
                        "Se ha actualizado el empleado correctamente",
                        employeeRepo.update(dto, id)
                ))
                .build();
    }

    @DELETE
    @Path("{id}")
    @Authenticated
    public Response deleteEmployee(@NotNull @PathParam("id") Long id) {
        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(new ResponseEntityDTO<>(
                        "Se ha eliminado el empleado correctamente",
                        employeeRepo.delete(id)
                ))
                .build();
    }
}
