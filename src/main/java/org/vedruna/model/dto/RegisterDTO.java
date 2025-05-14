package org.vedruna.model.dto;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vedruna.model.enums.Country;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String email;
    private String password;
    private String nif;
    private String name;
    private String surname;
    private boolean active;
    private Country country;
    private LocalDate start_date;
    private String job;
    private String department;
    private Long company_id;
}
