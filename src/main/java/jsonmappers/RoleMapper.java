/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonmappers;

import entity.Role;
import entity.User;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author TimmosQuadros
 */
public class RoleMapper {
    
  private String roleName;

    public RoleMapper(Role role) {
        roleName=role.getRoleName();
    }
  
    
}
