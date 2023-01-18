package entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TenantHasRentalId implements Serializable {
    private static final long serialVersionUID = 7216110660305857979L;
    @NotNull
    @Column(name = "Tenant_idTenant", nullable = false)
    private Integer tenantIdtenant;

    @NotNull
    @Column(name = "Rental_idRental", nullable = false)
    private Integer rentalIdrental;

    public Integer getTenantIdtenant() {
        return tenantIdtenant;
    }

    public void setTenantIdtenant(Integer tenantIdtenant) {
        this.tenantIdtenant = tenantIdtenant;
    }

    public Integer getRentalIdrental() {
        return rentalIdrental;
    }

    public void setRentalIdrental(Integer rentalIdrental) {
        this.rentalIdrental = rentalIdrental;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantHasRentalId entity = (TenantHasRentalId) o;
        return Objects.equals(this.tenantIdtenant, entity.tenantIdtenant) &&
                Objects.equals(this.rentalIdrental, entity.rentalIdrental);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantIdtenant, rentalIdrental);
    }

}