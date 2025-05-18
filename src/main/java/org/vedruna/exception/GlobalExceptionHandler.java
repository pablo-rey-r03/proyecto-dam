package org.vedruna.exception;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.RollbackException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.SQLGrammarException;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.vedruna.model.dto.message.ErrorMessage;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;

@Provider
@ApplicationScoped
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        ErrorMessage error;
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

        while (ex.getCause() != null) {
            ex = ex.getCause();
        }

        if (ex instanceof NotFoundException) {
            status = Response.Status.NOT_FOUND;
            error = new ErrorMessage(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    "Recurso no encontrado.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof IllegalArgumentException) {
            status = Response.Status.BAD_REQUEST;
            error = new ErrorMessage(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "El argumento proporcionado no es válido.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof SQLIntegrityConstraintViolationException) {
            status = Response.Status.FORBIDDEN;
            error = new ErrorMessage(
                    Response.Status.FORBIDDEN.getStatusCode(),
                    "Se ha violado una restricción del modelo de la base de datos.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof RollbackException) {
            status = Response.Status.FORBIDDEN;
            error = new ErrorMessage(
                    Response.Status.FORBIDDEN.getStatusCode(),
                    "Se ha cancelado la operación por violar las restricciones.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof DataException) {
            status = Response.Status.BAD_REQUEST;
            error = new ErrorMessage(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "Los datos no son válidos para el esquema de la base de datos.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof PropertyValueException) {
            status = Response.Status.BAD_REQUEST;
            error = new ErrorMessage(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "Error relacionado con el valor de una propiedad.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof ConstraintViolationException) {
            status = Response.Status.FORBIDDEN;
            error = new ErrorMessage(
                    Response.Status.FORBIDDEN.getStatusCode(),
                    "Se ha violado una restricción del modelo.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof NullPointerException) {
            status = Response.Status.NOT_FOUND;
            error = new ErrorMessage(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    "Se ha accedido a una entidad nula.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof SQLGrammarException) {
            status = Response.Status.BAD_REQUEST;
            error = new ErrorMessage(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "Se ha producido un error de SQL en la base de datos.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof BadRequestException) {
            status = Response.Status.BAD_REQUEST;
            error = new ErrorMessage(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "La solicitud no presenta el formato requerido.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof GenericJDBCException) {
            status = Response.Status.BAD_REQUEST;
            error = new ErrorMessage(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "Un evento de la base de datos ha impedido la ejecución del comando.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof IOException) {
            error = new ErrorMessage(
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Error en el procesado de flujos de archivos.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else if (ex instanceof NotSupportedException) {
            error = new ErrorMessage(
                    Response.Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode(),
                    "Formato de archivo multimedia no soportado.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        } else {
            ex.printStackTrace();
            error = new ErrorMessage(
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Se ha producido un error.",
                    ex.getMessage(),
                    Arrays.toString(ex.getStackTrace())
            );
        }

        return Response
                .status(status)
                .entity(error)
                .build();
    }
}
