package entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_has_roles")
public class UserHasRole {
    @EmbeddedId
    private UserHasRoleId id;

    public UserHasRoleId getId() {
        return id;
    }

    public void setId(UserHasRoleId id) {
        this.id = id;
    }

    //TODO [JPA Buddy] generate columns from DB
}