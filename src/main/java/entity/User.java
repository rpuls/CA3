package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import security.IUser;
import security.PasswordStorage;

@Entity(name = "SEED_USER")
public class User implements IUser, Serializable {

    @Id
    @Column(length = 15)
    private String userName;
    private String passwordHash;

    @ManyToMany
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private transient List<Shop> shops;

    public User() {
    }

    public User(String userName, String password) throws PasswordStorage.CannotPerformOperationException {
        this.userName = userName;
        this.passwordHash = PasswordStorage.createHash(password);
    }

//  public User(String userName, String passwordHash,List<String> roles) {
//    this.userName = userName;
//    this.passwordHash = passwordHash;
//    //this.roles = roles;
//  }
    public void addRole(Role role) {
        if (roles == null) {
            roles = new ArrayList();
        }
        roles.add(role);
        role.addUser(this);
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public List<String> getRolesAsStrings() {
        if (roles.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList();
        for (Role role : roles) {
            rolesAsStrings.add(role.getRoleName());
        }
        return rolesAsStrings;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String password) throws PasswordStorage.CannotPerformOperationException {
        this.passwordHash = PasswordStorage.createHash(password);
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void addShop(Shop shop) {
        if (shops == null) {
            shops = new ArrayList();
        }
        shops.add(shop);
        shop.setUser(this);
    }

    public void setUserName(String username) {
        this.userName = username;
    }

}
