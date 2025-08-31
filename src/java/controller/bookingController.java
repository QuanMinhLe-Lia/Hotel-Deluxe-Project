/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BookingDAO;
import dal.CategoryDAO;
import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Booking;
import model.Category;
import model.Room;

/**
 *
 * @author AD
 */
public class bookingController extends HttpServlet {

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
            out.println("<title>Servlet bookingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet bookingController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        CategoryDAO cDao=new CategoryDAO();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String checkinDateStr = request.getParameter("checkinDate");
        String checkoutDateStr = request.getParameter("checkoutDate");
        Category category=cDao.getCategoryById(categoryId);
        String categoryName = category.getCategoryName();
        double pricePerNight = category.getPricePerNight();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int totalNights = 0;
        try {
            Date checkinDate = sdf.parse(checkinDateStr);
            Date checkoutDate = sdf.parse(checkoutDateStr);
            long diff = checkoutDate.getTime() - checkinDate.getTime();
            totalNights = (int) (diff / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double totalPrice = pricePerNight*totalNights;

        request.setAttribute("categoryName", categoryName);
        request.setAttribute("pricePerNight", pricePerNight);
        request.setAttribute("totalNights", totalNights);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("checkinDate", checkinDateStr);
        request.setAttribute("checkoutDate", checkoutDateStr);

        request.getRequestDispatcher("confirmBooking.jsp").forward(request, response);
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
   
}

// Hàm tạo mã QR MoMo

    

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
