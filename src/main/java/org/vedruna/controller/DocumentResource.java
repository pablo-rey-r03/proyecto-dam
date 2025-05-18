package org.vedruna.controller;

import io.quarkus.security.Authenticated;
import io.smallrye.common.constraint.NotNull;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.vedruna.model.Document;
import org.vedruna.model.dto.DocumentUploadForm;
import org.vedruna.model.dto.NewDocumentDTO;
import org.vedruna.model.dto.message.ResponseEntityDTO;
import org.vedruna.repository.DocumentRepository;

import java.util.List;

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
        return Response.ok(docuRepo.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Documento no encontrado."))).build();
    }

    @POST
    @Authenticated
    public Response newDoc(@NotNull NewDocumentDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(new ResponseEntityDTO<>("Se ha subido un documento", docuRepo.create(dto)))
                .build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{id}")
    @Authenticated
    public Response addFile(@NotNull @PathParam("id") Long id, @NotNull @MultipartForm DocumentUploadForm form) {
        return Response
                .status(Response.Status.CREATED)
                .entity(new ResponseEntityDTO<>("Se ha subido un documento", docuRepo.addFile(id, form.file)))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Authenticated
    @Path("{id}/file")
    public Response downloadFile(@NotNull @PathParam("id") Long id) {
        return docuRepo.downloadFileFromDocument(id);
    }

    @GET
    @Path("/emp/{id}")
    @Authenticated
    public Response getByEmp(@NotNull @PathParam("id") Long id) {
        List<Document> docs = docuRepo.getByEmp(id);

        if (docs.isEmpty()) {
            return Response
                    .noContent()
                    .build();
        }

        return Response
                .ok(docs)
                .build();
    }

    @GET
    @Path("/sub/{id}")
    @Authenticated
    public Response getBySub(@NotNull @PathParam("id") Long id) {
        List<Document> docs = docuRepo.getBySub(id);

        if (docs.isEmpty()) {
            return Response
                    .noContent()
                    .build();
        }

        return Response
                .ok(docs)
                .build();
    }

    @PUT
    @Path("{id}")
    @Authenticated
    public Response updateDoc(@NotNull @PathParam("id") Long id, @NotNull NewDocumentDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(
                        new ResponseEntityDTO<>(
                                "Se ha actualizado el documento",
                                docuRepo.update(id, dto)
                        )
                )
                .build();
    }

    @DELETE
    @Authenticated
    @Path("{id}")
    public Response deleteDoc(@NotNull @PathParam("id") Long id) {
        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(new ResponseEntityDTO<>("Se ha eliminado correctamente el documento", docuRepo.delete(id)))
                .build();
    }
}
