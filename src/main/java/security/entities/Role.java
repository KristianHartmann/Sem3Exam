package security.entities;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Plaul
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "role_id")
    private int roleId;

    @NotNull
    @Column(name = "role_name")
    private String roleName;
    
    @ManyToMany(mappedBy = "roleList")
    private Set<User> userList = new LinkedHashSet<>();

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getRoleId() == role.getRoleId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleId());
    }
}
