package org.vedruna.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewDocumentDTO {
    private String validationState;
    private Long contractorId;
    private Long subcontractId;
    private String addresseeType;
    private String name;
    private LocalDate date;
    private LocalDate expirationDate;
    private LocalDate validationDate;
    private Long employeeId;
    private String additionalInfo;
    private String filePath;
}
