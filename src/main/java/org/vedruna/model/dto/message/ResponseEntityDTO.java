package org.vedruna.model.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Respuesta del servidor asociada a una entidad declarada como tipo genérico
 * @param <E> Entidad devuelta
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntityDTO<E> {
    private String message;
    private E entity;
}
