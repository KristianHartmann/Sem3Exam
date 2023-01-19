package entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tenant_has_rental")
public class TenantHasRental {
    @EmbeddedId
    private TenantHasRentalId id;

    public TenantHasRentalId getId() {
        return id;
    }

    public void setId(TenantHasRentalId id) {
        this.id = id;
    }

    //TODO [JPA Buddy] generate columns from DB
}