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
public class NewCompanyDTO {
    private String cif;
    private String name;
    private String country;
    private String address;
}
