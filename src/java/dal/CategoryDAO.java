/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import model.Account;
import model.Category;

/**
 *
 * @author AD
 */
public class CategoryDAO extends DBContext{
     

    

    public CategoryDAO() {
    }

    public Category getCategoryById(int id) {
        Category category = null;
        String sql = "SELECT * FROM Category WHERE categoryId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setCapacity(rs.getInt("capacity"));
                category.setPricePerNight(rs.getDouble("pricePerNight"));
                category.setDescription(rs.getString("description"));
                category.setImage(rs.getString("image"));
                category.setNumberOfRooms(rs.getInt("numberOfRoom"));
                category.setSize(rs.getDouble("size"));
                category.setBed(rs.getInt("bed"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return category;
    }
     public static void main(String[] args) {
       CategoryDAO c=new CategoryDAO();
       Category test=c.getCategoryById(1);
        System.out.println(test.getCategoryName());
    }
}
