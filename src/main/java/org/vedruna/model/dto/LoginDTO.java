//
// Copyright - Metadata S.L. - Todos los Derechos Reservados - Documento Confidencial [CON]
//

package org.vedruna.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String email;
    private String password;
}
