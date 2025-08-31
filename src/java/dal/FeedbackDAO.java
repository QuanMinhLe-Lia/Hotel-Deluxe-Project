/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Account;
import model.Feedback;
import model.Role;


/**
 *
 * @author DELL
 */
public class FeedbackDAO extends DBContext{
    
    public List<Feedback> getAllFeedback() {
    List<Feedback> feedbackList = new ArrayList<>();
    String sql = "SELECT * FROM Feedback";
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Feedback feedback = new Feedback();
            feedback.setFeedbackId(rs.getInt("FeedbackID"));
            feedback.setBookingId(rs.getInt("BookingID"));
            feedback.setRating(rs.getInt("Rating"));
            feedback.setComment(rs.getString("Comment"));
            feedback.setFeedbackDate(rs.getDate("FeedbackDate"));
            feedbackList.add(feedback);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return feedbackList;
}
    public void submitFeedback(Feedback fb) {
    String sql = "INSERT INTO Feedback (BookingID, Rating, Comment) VALUES (?, ?, ?)";
    
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, fb.getBookingId());
        ps.setInt(2, fb.getRating());
        ps.setString(3, fb.getComment());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public void updateFeedback(Feedback fb) {
    String sql = "UPDATE Feedback SET Rating = ?, Comment = ? WHERE FeedbackID = ?";

    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, fb.getRating());
        ps.setString(2, fb.getComment());
        ps.setInt(3, fb.getFeedbackId());
        
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Feedback updated successfully!");
        } else {
            System.out.println("Feedback ID not found!");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public boolean feedbackExists(int bookingId) {
    String sql = "SELECT COUNT(*) FROM Feedback WHERE BookingID = ?";
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, bookingId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
    
    public Feedback getFeedbackByBookingId(int bookingId) {
        String sql = "SELECT * FROM Feedback WHERE BookingID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(rs.getInt("FeedbackID"));
                feedback.setBookingId(rs.getInt("BookingID"));
                feedback.setRating(rs.getInt("Rating"));
                feedback.setComment(rs.getString("Comment"));
                feedback.setFeedbackDate(rs.getDate("FeedbackDate"));
                return feedback;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
       
    }
    
}
