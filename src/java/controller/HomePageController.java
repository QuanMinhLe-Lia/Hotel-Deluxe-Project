/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.AccountDAO;
import dal.AnnouncementDAO;
import dal.RoomDAO;
import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Announcement;
import model.Category;
import model.Service;

/**
 *
 * @author Admin
 */
public class HomePageController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomePageController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomePageController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
         AccountDAO a = new AccountDAO();
        RoomDAO r = new RoomDAO();
        AnnouncementDAO i = new AnnouncementDAO();
        ServiceDAO s = new ServiceDAO();
        ArrayList<Announcement> announcements = i.getTop5Announcement();
        ArrayList<Category> capacities = r.getAllCapacities();
        ArrayList<Category> list = r.ListCategory();
        ArrayList<Service> top4Services = s.getTop4ServicesBooked();
        // Make sure roomList is not null
        if (list == null) {
            list = new ArrayList<>(); // Provide an empty list instead of null
        }
        request.setAttribute("top4Services", top4Services);
        request.setAttribute("informations", announcements);
        request.setAttribute("capacities", capacities);
        request.setAttribute("list", list);
        request.setAttribute("totalCustomers", a.getTotalCustumers());
        request.setAttribute("totalStaffs", a.getTotalStaffs());
        request.setAttribute("topRooms", r.getTop3Category());
        request.setAttribute("totalRooms", r.getTotalRoom());
        request.getRequestDispatcher("index.jsp").forward(request, response);
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
