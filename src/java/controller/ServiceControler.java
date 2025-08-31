/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.ServiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import model.Category;
import model.Service;



/**
 *
 * @author DELL
 */
@WebServlet(name = "ServiceControler", urlPatterns = {"/ServiceControler"})
public class ServiceControler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet ServiceControler</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceControler at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       ServiceDAO svd = new ServiceDAO();
        List<Service> sv = svd.getAvailableServices();

        request.setAttribute("listservice", sv);

        String categoryName = request.getParameter("categoryName");
        String checkinDateStr = request.getParameter("checkinDate");
        String checkoutDateStr = request.getParameter("checkoutDate");
        String categoryId = request.getParameter("categoryId");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkinDate = LocalDate.parse(checkinDateStr, formatter);
        LocalDate checkoutDate = LocalDate.parse(checkoutDateStr, formatter);
        
        long numberOfDays = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        
        CategoryDAO CD = new CategoryDAO();
        Category a = CD.getCategoryById(Integer.parseInt(categoryId));
        double pricepernight = a.getPricePerNight();
        double roomprice = pricepernight*numberOfDays;
        request.setAttribute("pricepernight", pricepernight);
        request.setAttribute("day", numberOfDays);
        request.setAttribute("roomtotal", roomprice);
        request.setAttribute("categoryName", categoryName);
        request.setAttribute("checkinDate", checkinDateStr);
        request.setAttribute("checkoutDate", checkoutDateStr);
        request.setAttribute("categoryId", categoryId);
        request.getRequestDispatcher("bookroom.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
