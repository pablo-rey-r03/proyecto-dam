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
public class NewEmployeeDTO {
    private String nif;
    private String name;
    private String surname;
    private Boolean active;
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    private String job;
    private String department;
    private String additionalInfo;
    private Long companyId;
}
