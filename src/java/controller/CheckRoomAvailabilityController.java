/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.FacilityDAO;
import dal.RoomDAO;
import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Service;

/**
 *
 * @author Admin
 */
public class CheckRoomAvailabilityController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    try {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String checkInDate = request.getParameter("checkinDate");
        String checkOutDate = request.getParameter("checkoutDate");
        String numberOfRoomsStr = request.getParameter("numberOfRooms");
        int numberOfRooms = (numberOfRoomsStr == null || numberOfRoomsStr.isEmpty()) ? 1 : Integer.parseInt(numberOfRoomsStr);

        // Kiểm tra số lượng phòng hợp lệ
        if (numberOfRooms <= 0) {
            throw new IllegalArgumentException("Number of rooms must be greater than 0.");
        }

        // Tiếp tục xử lý logic kiểm tra tình trạng phòng
        RoomDAO r = new RoomDAO();
        int availableRooms = r.RoomCountByCategoryId(categoryId, checkOutDate, checkInDate);
        ServiceDAO serviceDAO = new ServiceDAO();
        FacilityDAO facilityDAO = new FacilityDAO();
        Category category = r.getRoomCategoryById(categoryId);

        ArrayList<Category> similarCategories = r.getSimilarRoomCategories(categoryId);
        ArrayList<Category> allCategories = r.ListCategory();

        ArrayList<String> facilities = facilityDAO.getFacilitiesById(categoryId);

        // Get all services
        List<Service> services = serviceDAO.getAllServices();
        request.setAttribute("availableRooms", availableRooms);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("checkinDate", checkInDate);
        request.setAttribute("checkoutDate", checkOutDate);
        request.setAttribute("numberOfRooms", numberOfRooms);
        request.setAttribute("facilities", facilities);
        request.setAttribute("category", category);
        request.setAttribute("similarCategories", similarCategories);
        request.setAttribute("allCategories", allCategories);
        request.setAttribute("services", services);
        // Forward to JSP
        request.getRequestDispatcher("rooms-single.jsp").forward(request, response);
    } catch (IllegalArgumentException e) {
        // Gửi thông báo lỗi chi tiết đến client
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }
}

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     * Handles the HTTP <code>POST</code> method.
     *
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
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
