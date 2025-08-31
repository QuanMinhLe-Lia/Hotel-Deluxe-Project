/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Announcement;

/**
 *
 * @author Admin
 */
public class AnnouncementDAO extends DBContext{
    // Phương thức phân trang
    public ArrayList<Announcement> pagingAnnouncements(int page, int pageSize) throws SQLException {
        ArrayList<Announcement> postList = new ArrayList<>();
        String query = "SELECT * FROM Announcement ORDER BY AnnouncementId DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try{
             PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Announcement post = new Announcement();
                    post.setAnnouncementID(rs.getInt("AnnouncementId"));
                    post.setAnnouncementName(rs.getString("AnnouncementName"));
                    post.setDescription(rs.getString("description"));
                    post.setDateCreated(rs.getDate("dateCreated"));
                    postList.add(post);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return postList;
    }

    // Phương thức đếm tổng số bài đăng
    public int getAllAnnouncementCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM Announcement";
        try {
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    
    // Lấy danh sách tất cả thông báo
    public ArrayList<Announcement> getTop5Announcement() {
        ArrayList<Announcement> list = new ArrayList<>();
        String sql = "SELECT TOP (5) [AnnouncementId]\n" +
"      ,[AnnouncementName]\n" +
"      ,[Description]\n" +
"      ,[DateCreated] FROM Announcement ORDER BY AnnouncementId DESC";
        try {
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery(); 
            while (rs.next()) {
                Announcement info = new Announcement();
                info.setAnnouncementID(rs.getInt("AnnouncementId"));
                info.setAnnouncementName(rs.getString("AnnouncementName"));
                info.setDescription(rs.getString("Description"));
                info.setDateCreated(rs.getDate("DateCreated"));
                list.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy danh sách tất cả thông báo
    public ArrayList<Announcement> getAllAnnouncement() {
        ArrayList<Announcement> list = new ArrayList<>();
        String sql = "SELECT [AnnouncementId]\n" +
"      ,[AnnouncementName]\n" +
"      ,[Description]\n" +
"      ,[DateCreated] FROM Announcement ORDER BY DateCreated DESC";
        try {
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery(); 
            while (rs.next()) {
                Announcement info = new Announcement();
                info.setAnnouncementID(rs.getInt("AnnouncementId"));
                info.setAnnouncementName(rs.getString("AnnouncementName"));
                info.setDescription(rs.getString("Description"));
                info.setDateCreated(rs.getDate("DateCreated"));
                list.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm thông báo mới
    public void addAnnouncement(Announcement info) {
        String sql = "INSERT INTO Announcement (AnnouncementName, Description, DateCreated) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, info.getAnnouncementName());
            ps.setString(2, info.getDescription());
            ps.setDate(3, new java.sql.Date(info.getDateCreated().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật thông báo
    public void updateAnnouncement(Announcement info) {
        String sql = "UPDATE Announcement SET AnnouncementName = ?, Description = ? WHERE AnnouncementID = ?";
        try {
             PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, info.getAnnouncementName());
            ps.setString(2, info.getDescription());
            ps.setInt(3, info.getAnnouncementID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa thông báo
    public void deleteAnnouncement(int informationID) {
        String sql = "DELETE FROM Announcement WHERE AnnouncementId = ?";
        try {
             PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, informationID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
