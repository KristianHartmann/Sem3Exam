package security.entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "user")
@NamedQuery(name = "User.getHighestAI", query = "select max(u.userId) from User u")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  @NotNull
  @Column(name = "user_id")
  private int userId;

  @NotNull
  @Column(name = "user_name")
  private String userName;
  @NotNull
  @Column(name = "user_pass")
  private String userPass;

  @JoinTable(name = "user_has_roles", joinColumns = {
    @JoinColumn(name = "FK_user_id", referencedColumnName = "user_id")}, inverseJoinColumns = {
    @JoinColumn(name = "FK_role_id", referencedColumnName = "role_id")})
  @ManyToMany(cascade = CascadeType.PERSIST)
  private Set<Role> roleList = new LinkedHashSet<>();

  public List<String> getRolesAsStrings() {
    if (roleList.isEmpty()) {
      return null;
    }
    List<String> rolesAsStrings = new ArrayList<>();
    roleList.forEach((role) -> {
        rolesAsStrings.add(role.getRoleName());
      });
    return rolesAsStrings;
  }

  public User() {}

  //TODO Change when password is hashed
   public boolean verifyPassword(String pw){
        return BCrypt.checkpw(pw, userPass);
    }

  public User(String userName, String userPass) {
    this.userName = userName;
    this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt(10));
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPass() {
    return userPass;
  }

  public void setUserPass(String userPass) {
    this.userPass = userPass;
  }

  public Set<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(Set<Role> roleList) {
    this.roleList = roleList;
  }

  public void addRole(Role userRole) {
    userRole.getUserList().add(this);
    this.roleList.add(userRole);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return getUserId() == user.getUserId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUserId());
  }

}
