package org.vedruna.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewSubcontractingRelationshipDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String additionalInfo;
}
