package org.vedruna.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vedruna.model.help.SubcontractingRelationshipId;

@Entity
@Table(name = "subcontracting_relationship")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubcontractingRelationship {

    @EmbeddedId
    private SubcontractingRelationshipId id;

    @ManyToOne
    @MapsId("contractorId")
    @JoinColumn(name = "contractor_id")
    private Company contractor;

    @ManyToOne
    @MapsId("subcontractId")
    @JoinColumn(name = "subcontract_id")
    private Company subcontract;

    @Column(nullable = false)
    private LocalDate start_date;

    @Column
    private LocalDate end_date;

    @Column(length = 512)
    private String additional_info;

    public SubcontractingRelationship(Company contractor, Company subcontract, LocalDate start_date, LocalDate end_date, String additional_info) {
        this.contractor = contractor;
        this.subcontract = subcontract;
        this.start_date = start_date;
        this.end_date = end_date;
        this.additional_info = additional_info;
    }
}
