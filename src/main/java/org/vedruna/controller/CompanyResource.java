package org.vedruna.controller;

import io.smallrye.common.constraint.NotNull;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.vedruna.model.SubcontractingRelationship;
import org.vedruna.model.dto.NewCompanyDTO;
import org.vedruna.model.dto.NewSubcontractingRelationshipDTO;
import org.vedruna.model.dto.message.ResponseDTO;
import org.vedruna.model.dto.message.ResponseEntityDTO;
import org.vedruna.repository.CompanyRepository;
import io.quarkus.security.Authenticated;
import org.vedruna.repository.SubcontractingRelationshipRepository;

import java.util.List;

@Path("/company")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyResource {

    @Inject
    CompanyRepository companyRepository;

    @Inject
    SubcontractingRelationshipRepository subRepo;

    @GET
    @Path("{id}")
    @Authenticated
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(companyRepository.getCompanyById(id).orElseThrow(() -> new NotFoundException("Empresa no encontrada."))).build();
    }

    @GET
    public Response getAll() {
        return Response.ok(companyRepository.listAll()).build();
    }

    @POST
    @Authenticated
    public Response newCompany(@NotNull NewCompanyDTO newCompanyDTO) {
        return Response
                .status(Response.Status.CREATED)
                .entity(
                        new ResponseEntityDTO<>(
                                "Empresa creada correctamente.",
                                companyRepository.create(newCompanyDTO)
                        )
                )
                .build();
    }

    @DELETE
    @Path("{id}")
    @Authenticated
    public Response deleteCompany(@NotNull @PathParam("id") Long id) {
        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(
                        new ResponseEntityDTO<>(
                                "Empresa eliminada correctamente.",
                                companyRepository.delete(id)
                        )
                )
                .build();
    }

    @PUT
    @Path("{id}")
    @Authenticated
    public Response updateCompany(@NotNull @PathParam("id") Long id, NewCompanyDTO companyDTO) {
        return Response
                .status(Response.Status.CREATED)
                .entity(new ResponseEntityDTO<>(
                        "Se ha actualizado correctamente la empresa con id " + id,
                        companyRepository.update(id, companyDTO)
                ))
                .build();
    }

    @GET
    @Path("{id}/hires")
    @Authenticated
    public Response getSubontracts(@NotNull @PathParam("id") Long id) {
        List<SubcontractingRelationship> subcontracts = subRepo.findByContractorId(id);
        if (subcontracts.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity(new ResponseDTO("No hay subcontratas para la empresa dada.")).build();
        }
        return Response.ok(subcontracts).build();
    }

    @GET
    @Path("{id}/ishired")
    @Authenticated
    public Response getContracts(@NotNull @PathParam("id") Long id) {
        List<SubcontractingRelationship> contracts = subRepo.findBySubcontractId(id);
        if (contracts.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity(new ResponseDTO("No hay contratistas para la empresa subcontratada.")).build();
        }
        return Response.ok(contracts).build();
    }

    @GET
    @Path("{contId}/hires/{subId}")
    @Authenticated
    public Response getRelation(
            @NotNull @PathParam("contId") Long contractorId,
            @NotNull @PathParam("subId") Long subcontractId
    ) {
        return Response
                .ok(subRepo.findByIds(contractorId, subcontractId))
                .build();
    }

    @POST
    @Path("{contId}/hires/{subId}")
    @Authenticated
    public Response newRelation(
            @NotNull @PathParam("contId") Long contractorId,
            @NotNull @PathParam("subId") Long subcontractId,
            @NotNull NewSubcontractingRelationshipDTO srDTO
    ) {
        return Response
                .status(Response.Status.CREATED)
                .entity(new ResponseEntityDTO<>(
                        "Se ha creado la relación entre empresas correctamente.",
                        subRepo.create(contractorId, subcontractId, srDTO)
                ))
                .build();
    }

    @PUT
    @Path("{contId}/hires/{subId}")
    @Authenticated
    public Response updateRelation(
            @NotNull @PathParam("contId") Long contractorId,
            @NotNull @PathParam("subId") Long subcontractId,
            @NotNull NewSubcontractingRelationshipDTO srDTO
    ) {
        return Response
                .status(Response.Status.CREATED)
                .entity(new ResponseEntityDTO<>(
                        "Se ha actualizado la relación entre empresas correctamente.",
                        subRepo.update(contractorId, subcontractId, srDTO)
                ))
                .build();
    }

    @DELETE
    @Path("{contId}/fires/{subId}")
    @Authenticated
    public Response deleteRelation(
            @NotNull @PathParam("contId") Long contractorId,
            @NotNull @PathParam("subId") Long subcontractId
    ) {
        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(new ResponseEntityDTO<>(
                        "Se ha eliminado la relación entre empresas correctamente.",
                        subRepo.delete(contractorId, subcontractId)
                ))
                .build();
    }
}
