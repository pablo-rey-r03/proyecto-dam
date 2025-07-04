package org.vedruna.model.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Objeto que se devolverá en todos los errores lanzados por la API, de forma que se capturará fácilmente la causa
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private int status;
    private String error;
    private String detail;
    private String stack;
}
