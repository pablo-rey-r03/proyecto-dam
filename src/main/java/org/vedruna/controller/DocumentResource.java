package org.vedruna.controller;

import io.quarkus.security.Authenticated;
import io.smallrye.common.constraint.NotNull;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.vedruna.model.dto.NewDocumentDTO;
import org.vedruna.repository.DocumentRepository;

@Path("document")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DocumentResource {

    @Inject
    DocumentRepository docuRepo;

    @GET
    @Authenticated
    public Response getAll() {
        return Response.ok(docuRepo.listAll()).build();
    }

    @GET
    @Path("{id}")
    @Authenticated
    public Response getOne(@NotNull @PathParam("id") Long id) {
        return Response.ok(docuRepo.findByIdOptional(id).orElseThrow(NotFoundException::new)).build();
    }

    @POST
    @Authenticated
    public Response newDoc(@NotNull NewDocumentDTO dto) {
        return Response.ok().build();
    }
}
