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

//  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//  @JoinColumn(name = "FK_faceituser_id", referencedColumnName = "faceituser_id")
//  private FaceitUser faceitUser;

  @JoinTable(name = "user_has_roles", joinColumns = {
    @JoinColumn(name = "FK_user_id", referencedColumnName = "user_id")}, inverseJoinColumns = {
    @JoinColumn(name = "FK_role_id", referencedColumnName = "role_id")})
  @ManyToMany(cascade = CascadeType.PERSIST)
  private Set<Role> roleList = new LinkedHashSet<>();

//  @JoinTable(name = "user_has_community", joinColumns = {
//          @JoinColumn(name = "FK_user_id", referencedColumnName = "user_id")}, inverseJoinColumns = {
//          @JoinColumn(name = "FK_community_id", referencedColumnName = "community_id")})
//  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//  private Set<Community> communityList = new LinkedHashSet<>();

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

//  public List<String> getCommunitiesAsStrings() {
//    if (communityList.isEmpty()) {
//      return null;
//    }
//    List<String> communitiesAsStrings = new ArrayList<>();
//    communityList.forEach((community) -> {
//      communitiesAsStrings.add(community.getCommunityName());
//    });
//    return communitiesAsStrings;
//  }

//  public Set<Community> getCommunityList() {
//    return communityList;
//  }

//  public void setCommunityList(Set<Community> communityList) {
//    this.communityList = communityList;
//  }

  public User() {}

  //TODO Change when password is hashed
   public boolean verifyPassword(String pw){
        return BCrypt.checkpw(pw, userPass);
    }

//  public User(String userName, String userPass, FaceitUser faceitUser) {
//    this.userName = userName;
//    this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt(10));
//    this.faceitUser = faceitUser;
//    faceitUser.setUser(this);
//  }

  public User(String userName, String userPass) {
    this.userName = userName;
    this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt(10));
  }

//  public FaceitUser getFaceitUser() {
//    return faceitUser;
//  }

//  public void setFaceitUser(FaceitUser faceitUser) {
//    this.faceitUser = faceitUser;
//  }

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

//  public void addFaceituser(FaceitUser faceitUser) {
//    faceitUser.setUser(this);
//    setFaceitUser(faceitUser);
//  }

//  public void addCommunity(Community community) {
//    community.getUserList().add(this);
//    this.communityList.add(community);
//  }

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

//  @Override
//  public String toString() {
//    return "User{" +
//            "userId=" + userId +
//            ", userName='" + userName + '\'' +
//            ", userPass='" + userPass + '\'' +
//            ", faceitUser=" + faceitUser +
//            ", roleList=" + roleList +
//            ", communityList=" + communityList +
//            '}';
//  }
}
