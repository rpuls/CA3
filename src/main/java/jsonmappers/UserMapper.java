/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonmappers;

import entity.Role;
import entity.Shop;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;

/**
 *
 * @author TimmosQuadros
 */
public class UserMapper {

    private String userName;
    private String passwordHash;
    private List<RoleMapper> roles;
    private List<Shop> shops;

    public UserMapper(User user) {
        if (user != null) {
            if (user.getUserName() != null) {
                userName = user.getUserName();
            }
            if (user.getPassword() != null) {
                passwordHash = user.getPassword();
            }
            roles = new ArrayList<>();
            if (user.getRoles() != null) {
                List<Role> rolesTmp = user.getRoles();
                for (Role role : rolesTmp) {
                    roles.add(new RoleMapper(role));
                }
            }
            shops=user.getShops();
        }

        //passwordHash=user.getPassword();
//        
        
    }

}
