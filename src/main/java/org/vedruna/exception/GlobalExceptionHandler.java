package org.vedruna.exception;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.vedruna.model.dto.message.ErrorMessage;

import java.sql.SQLException;

@ApplicationScoped
public class GlobalExceptionHandler {

    @ServerExceptionMapper(NotFoundException.class)
    public Response handleNotFound(NotFoundException ex) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Recurso no encontrado.",
                        ex.getMessage()
                ))
                .build();
    }

    @ServerExceptionMapper(IllegalArgumentException.class)
    public Response handleIllegalArgument(IllegalArgumentException ex) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(
                        Response.Status.BAD_REQUEST.getStatusCode(),
                        "El argumento proporcionado no es válido.",
                        ex.getMessage()
                ))
                .build();
    }

    @ServerExceptionMapper(DataException.class)
    public Response handleData(DataException ex) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(
                        Response.Status.BAD_REQUEST.getStatusCode(),
                        "Los datos no son válidos para el esquema de la base de datos.",
                        ex.getMessage()
                ))
                .build();
    }

    @ServerExceptionMapper(PropertyValueException.class)
    public Response handlePropertyValue(PropertyValueException ex) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(
                        Response.Status.BAD_REQUEST.getStatusCode(),
                        "Error relacionado con el valor de una propiedad.",
                        ex.getMessage()
                ))
                .build();
    }

    @ServerExceptionMapper(ConstraintViolationException.class)
    public Response handleConstraintViolation(ConstraintViolationException ex) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(
                        Response.Status.BAD_REQUEST.getStatusCode(),
                        "Se ha violado una restricción del modelo.",
                        ex.getMessage()
                ))
                .build();
    }

//    @ServerExceptionMapper(Exception.class)
//    public Response handleGeneral(Exception ex) {
//        return Response
//                .status(Response.Status.INTERNAL_SERVER_ERROR)
//                .entity(
//                        new ErrorMessage(
//                                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
//                                "Se ha producido un error",
//                                ex.getMessage()
//                        )
//                )
//                .build();
//    }
}
