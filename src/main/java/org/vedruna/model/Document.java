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
import org.vedruna.model.enums.AddresseeType;
import org.vedruna.model.enums.ValidationState;

@Entity
@Table(name = "document")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ValidationState validation_state;

    @ManyToOne
    @JoinColumn(name = "contractor_id", nullable = false)
    private Company contractor;

    @ManyToOne
    @JoinColumn(name = "subcontract_id", nullable = false)
    private Company subcontract;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AddresseeType addressee_type;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private LocalDate expiration_date;

    @Column
    private LocalDate validation_date;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(length = 256)
    private String additional_info;

    @Column(length = 512)
    private String filePath;

    public Document(ValidationState validation_state, Company contractor, Company subcontract, AddresseeType addressee_type, String name, LocalDate date,
            LocalDate expiration_date, LocalDate validation_date, Employee employee, String additional_info, String filePath) {
        this.validation_state = validation_state;
        this.contractor = contractor;
        this.subcontract = subcontract;
        this.addressee_type = addressee_type;
        this.name = name;
        this.date = date;
        this.expiration_date = expiration_date;
        this.validation_date = validation_date;
        this.employee = employee;
        this.additional_info = additional_info;
        this.filePath = filePath;
    }
}
