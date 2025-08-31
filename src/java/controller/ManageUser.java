/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.RoleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Overlordil
 */
public class ManageUser extends HttpServlet {

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
            out.println("<title>Servlet ManageUser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageUser at " + request.getContextPath() + "</h1>");
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
        AccountDAO user = new AccountDAO();
        RoleDAO role = new RoleDAO();
        
        request.setAttribute("roleList", role.getAllRole());
        request.setAttribute("createdList", user.getCreatedDate());
        request.setAttribute("userList", user.getAllAccount());
        request.getRequestDispatcher("manageUser.jsp").forward(request, response);
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
        try {
            String submit = request.getParameter("submit");
            AccountDAO user = new AccountDAO();
            
            switch (submit) {
                case "add": {
                    String fullName = request.getParameter("fullName").trim();
                    String email = request.getParameter("email").trim();
                    String password = request.getParameter("password").trim();
                    String phone = request.getParameter("phone").trim();
                    String address = request.getParameter("address").trim();
                    int roleid = Integer.parseInt(request.getParameter("roleid"));
                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                    Date createdDate = date.parse(request.getParameter("createdDate"));
                    
                    if (user.emailExists(email)) {
                        request.setAttribute("alert", "Email already exists!");
                    } else {
                        String status = "Active";
                        user.createAccount(roleid, fullName, email, password, phone, address, createdDate, status);
                        request.setAttribute("alert", "Add successfully!");
                    }
                    break;
                }
                case "update": {
                    int accountId = Integer.parseInt(request.getParameter("accountId"));
                    String fullName = request.getParameter("fullName").trim();
                    String phone = request.getParameter("phone").trim();
                    String address = request.getParameter("address").trim();
                    String statusUser = request.getParameter("status");
                    
                    user.updateUser(fullName, phone, address, statusUser, accountId);
                    request.setAttribute("alert", "Update successfully!");
                    break;
                }
                case "ban": {
                    int accountId = Integer.parseInt(request.getParameter("accountId"));
                    String statusUser = request.getParameter("status");
                    
                    user.updateStatusUser(accountId, statusUser);
                    request.setAttribute("alert", "Update status successfully!");
                    break;
                }
                default:
                    throw new AssertionError("Action submit không hợp lệ: " + submit);
            }
            
            doGet(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
