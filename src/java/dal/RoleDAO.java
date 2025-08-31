/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.Role;

/**
 *
 * @author Overlordil
 */
public class RoleDAO extends DatabaseOperations {

    public ArrayList<Role> getAllRole() {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "SELECT [RoleId],[RoleName]\n"
                   + "FROM [dbo].[Role]";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                Role r = new Role();
                r.setRoleId(rs.getInt("RoleId"));
                r.setRoleName(rs.getString("RoleName"));
                
                roles.add(r);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return roles;
    }
}
