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
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Service;

/**
 *
 * @author Admin
 */
public class RoomDetailController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
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
        String categoryIdParam = request.getParameter("categoryId");
//            if (categoryIdParam == null || categoryIdParam.isEmpty()) {
//                response.sendRedirect("rooms");
//                return;
//            }
            
            int categoryId = Integer.parseInt(categoryIdParam);
            RoomDAO roomDAO = new RoomDAO();
            ServiceDAO serviceDAO = new ServiceDAO();
            FacilityDAO facilityDAO = new FacilityDAO();
            // Get room category details
            Category category = roomDAO.getRoomCategoryById(categoryId);
 
            ArrayList<Category> similarCategories = roomDAO.getSimilarRoomCategories(categoryId);
            ArrayList<Category> allCategories = roomDAO.ListCategory();
            
            ArrayList<String> facilities = facilityDAO.getFacilitiesById(categoryId);
            
            // Get all services
            List<Service> services = serviceDAO.getAllServices();
            
            // Set attributes for JSP
            request.setAttribute("facilities", facilities);
            request.setAttribute("category", category);
            request.setAttribute("similarCategories", similarCategories);
            request.setAttribute("allCategories", allCategories);
            request.setAttribute("services", services);
            
            // Forward to JSP
            request.getRequestDispatcher("rooms-single.jsp").forward(request, response);
            
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
