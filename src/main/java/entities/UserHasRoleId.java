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

    public Integer getFkRoleId() {
        return fkRoleId;
    }

    public void setFkRoleId(Integer fkRoleId) {
        this.fkRoleId = fkRoleId;
    }


    @NotNull
    @Column(name = "FK_user_name", nullable = false)
    private String FK_user_name;

    public String getFkUsername() {
        return FK_user_name;
    }

    public void setFkUsername(String fkUsername) {
        this.FK_user_name = fkUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHasRoleId entity = (UserHasRoleId) o;
        return Objects.equals(this.fkRoleId, entity.fkRoleId) &&
                Objects.equals(this.FK_user_name, entity.FK_user_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fkRoleId, FK_user_name);
    }

}