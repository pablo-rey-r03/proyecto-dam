package org.vedruna.model.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Respuesta del servidor basada en un mensaje estándar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String message;
}
