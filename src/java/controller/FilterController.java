/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.RoomDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Category;

/**
 *
 * @author Admin
 */
public class FilterController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
HttpSession session = request.getSession();

        String categoryName = request.getParameter("roomType");
        String capacityParam = request.getParameter("capacity");
        String bedParam = request.getParameter("bed");
        String checkinDate = request.getParameter("checkin");
        String checkoutDate = request.getParameter("checkout");
        String[] priceRanges = request.getParameterValues("priceRange");

        int capacity = 0;
        if (capacityParam != null && !capacityParam.isEmpty()) {
            capacity = Integer.parseInt(capacityParam);
        }
        int bed = 0;

        if (bedParam != null && !bedParam.isEmpty()) {
            bed = Integer.parseInt(bedParam);
        }
        RoomDAO r = new RoomDAO();

        ArrayList<Category> filteredCategories = new ArrayList<>();
        
        // Kiểm tra điều kiện ngày check-in và check-out
    if (!isValidDateRange(checkinDate, checkoutDate)) {
    // Kiểm tra xem ngày check-in có phải là quá khứ hay không
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date checkin = sdf.parse(checkinDate);
        Date today = new Date();

        if (checkin.before(today)) {
            // Nếu ngày check-in là quá khứ, đặt thông báo lỗi
            request.setAttribute("errorMessage", "Check-in date cannot be in the past. Please select today or a future date.");
        } else {
            // Nếu ngày check-in lớn hơn hoặc bằng ngày check-out, đặt thông báo lỗi
            request.setAttribute("errorMessage", "Check-in date must be before check-out date.");
        }
    } catch (ParseException e) {
        // Nếu có lỗi parse ngày, đặt thông báo lỗi
        request.setAttribute("errorMessage", "Invalid date format. Please use the format YYYY-MM-DD.");
    }

    
    // Chuyển tiếp đến trang rooms.jsp
    request.getRequestDispatcher("rooms.jsp").forward(request, response);
    return;
}
        // Handle price ranges if selected
        if (priceRanges != null && priceRanges.length > 0) {
            // Process each selected price range
            for (String range : priceRanges) {
                // Split the range into min and max prices
                String[] prices = range.split("-");
                double minPrice = Double.parseDouble(prices[0]);
                double maxPrice = Double.parseDouble(prices[1]);

                // Use the filterRoomCategory method with appropriate parameters
                ArrayList<Category> categories = r.filterRoomCategory(
                        minPrice,
                        maxPrice,
                        categoryName,
                        capacity,
                        bed,
                        checkoutDate,
                        checkinDate
                );

                // Add unique categories to the filtered list
                for (Category category : categories) {
                    if (!containsCategory(filteredCategories, category)) {
                        filteredCategories.add(category);
                    }
                }
            }
        } else {
            filteredCategories = r.filterRoomCategory(
                    null, // min price (will use default 0)
                    null, // max price (will use max from database)
                    categoryName,
                    capacity > 0 ? capacity : 0, // Use capacity if provided
                    bed > 0 ? bed : 0,
                    checkoutDate,
                    checkinDate
            );
        }

        // Get all capacities for the dropdown
        ArrayList<Category> capacities = r.getAllCapacities();
        ArrayList<Category> beds = r.getAllBeds();
        // Get all categories for the room type dropdown
        ArrayList<Category> allCategories = r.ListCategory();

        // Set attributes for the JSP
        request.setAttribute("list", filteredCategories);
        request.setAttribute("capacities", capacities);
        request.setAttribute("allCategories", allCategories);
        request.setAttribute("beds", beds);
        // Add selected filter parameters to maintain state
        session.setAttribute("selectedCategory", categoryName);
        session.setAttribute("selectedCapacity", capacity);
        session.setAttribute("selectedCheckin", checkinDate);
        session.setAttribute("selectedCheckout", checkoutDate);
        session.setAttribute("selectedPriceRanges", priceRanges);

        // Forward to rooms.jsp
        request.getRequestDispatcher("rooms.jsp").forward(request, response);
    }
    
    private boolean isValidDateRange(String checkinDate, String checkoutDate) {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        
        // Lấy ngày hiện tại
        Date today = new Date();
        String todayStr = sdf.format(today);
        
        // Parse ngày check-in và check-out
        Date checkin = sdf.parse(checkinDate);
        Date checkout = sdf.parse(checkoutDate);
        
         // Kiểm tra điều kiện: ngày check-in phải lớn hơn hoặc bằng ngày hiện tại
        if (checkin.before(sdf.parse(todayStr))) {
            // Nếu ngày check-in là quá khứ, trả về false
            return false;
        }
        
        // Kiểm tra điều kiện: ngày check-in phải nhỏ hơn ngày check-out
        return checkin.before(checkout);
    } catch (ParseException e) {
        // Nếu có lỗi parse ngày, trả về false
        return false;
    }
}
    
    private boolean containsCategory(ArrayList<Category> categories, Category category) {
        for (Category c : categories) {
            if (c.getCategoryId() == category.getCategoryId()) {
                return true;
            }
        }
        return false;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
