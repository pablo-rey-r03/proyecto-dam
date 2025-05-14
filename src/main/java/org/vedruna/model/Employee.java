package org.vedruna.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vedruna.model.enums.Country;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true)
    private Long id;

    @Column (nullable = false, unique = true)
    private String nif;

    @Column (nullable = false)
    private String name;

    @Column
    private String surname;

    @Column (nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean active;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column (nullable = false)
    private LocalDate start_date;

    @Column
    private LocalDate end_date;

    @Column (nullable = false)
    private String job;

    @Column (nullable = false)
    private String department;

    @Column
    private String additional_info;

    @ManyToOne
    @JoinColumn (referencedColumnName = "id", name = "company_id")
    private Company company;

    public Employee(String nif, String name, String surname, Boolean active, Country country, LocalDate start_date, LocalDate end_date, String job,
            String department, String additional_info, Company company) {
        this.nif = nif;
        this.name = name;
        this.surname = surname;
        this.active = active;
        this.country = country;
        this.start_date = start_date;
        this.end_date = end_date;
        this.job = job;
        this.department = department;
        this.additional_info = additional_info;
        this.company = company;
    }
}
