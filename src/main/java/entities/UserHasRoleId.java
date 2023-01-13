package entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserHasRoleId implements Serializable {
    private static final long serialVersionUID = 5365676438303381560L;
    @NotNull
    @Column(name = "FK_role_id", nullable = false)
    private Integer fkRoleId;

    @NotNull
    @Column(name = "FK_user_id", nullable = false)
    private Integer fkUserId;

    public Integer getFkRoleId() {
        return fkRoleId;
    }

    public void setFkRoleId(Integer fkRoleId) {
        this.fkRoleId = fkRoleId;
    }

    public Integer getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHasRoleId entity = (UserHasRoleId) o;
        return Objects.equals(this.fkRoleId, entity.fkRoleId) &&
                Objects.equals(this.fkUserId, entity.fkUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fkRoleId, fkUserId);
    }

}