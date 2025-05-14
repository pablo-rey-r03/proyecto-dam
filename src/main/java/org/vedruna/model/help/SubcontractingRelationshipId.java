package org.vedruna.model.help;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SubcontractingRelationshipId implements Serializable {
    private Long contractorId;
    private Long subcontractId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        SubcontractingRelationshipId that = (SubcontractingRelationshipId) o;
        return Objects.equals(contractorId, that.contractorId) && Objects.equals(subcontractId, that.subcontractId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(contractorId, subcontractId);
    }
}
