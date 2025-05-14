package org.vedruna.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vedruna.model.enums.Country;

@Entity
@Table(name = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true)
    private Long id;

    @Column (nullable = false, unique = true, length = 16)
    private String cif;

    @Column (nullable = false, length = 32)
    private String name;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column (nullable = false, unique = true, length = 64)
    private String address;

    public Company(String cif, String name, Country country, String address) {
        this.cif = cif;
        this.name = name;
        this.country = country;
        this.address = address;
    }
}
