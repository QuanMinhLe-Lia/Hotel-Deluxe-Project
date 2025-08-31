/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Service;

/**
 *
 * @author Admin
 */
public class OwnerServiceController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServiceDAO s = new ServiceDAO();
            ArrayList<Service> services = s.getAllServices();
            request.setAttribute("services", services);
           request.getRequestDispatcher("manageservice.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        ServiceDAO service = new ServiceDAO();
        try {
            if ("add".equals(action)) {
                String serviceName = request.getParameter("serviceName");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                String status = request.getParameter("status");

                Service s = new Service();
                s.setServiceName(serviceName);
                s.setDescription(description);
                s.setPrice(price);
                s.setStatus(status);
                service.addService(s);
            } else if ("delete".equals(action)) {
                int serviceId = Integer.parseInt(request.getParameter("serviceId"));
                service.deleteService(serviceId);
            }

            response.sendRedirect("service");
        } catch (Exception e) {
            throw new ServletException(e);
        }
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
