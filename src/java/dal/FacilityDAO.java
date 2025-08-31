/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class FacilityDAO extends DBContext {

    public ArrayList<String> getFacilitiesById(int CategoryId) {
        ArrayList<String> facilities = new ArrayList<>();

        String sql = "SELECT c.[CategoryId],\n"
                + "  f.FacilityName\n"
                + "  FROM [HotelManagement].[dbo].[Category] c\n"
                + "  join Categories_Facilities cf on cf.CategoryId = c.CategoryId\n"
                + "  join Facilities f on f.FacilityId = cf.FacilityId\n"
                + "  where c.CategoryId = ?";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,CategoryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {                
                facilities.add(rs.getString("FacilityName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacilityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return facilities;
    }
    
    public static void main(String[] args) {
        FacilityDAO f = new FacilityDAO();
        for (String r : f.getFacilitiesById(1)) {
            System.out.println(r);
        }
    }
}
