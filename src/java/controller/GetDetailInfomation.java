/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import model.Booking;
import java.util.*;
import dal.BookingDAO;
import model.Account;
import dal.AccountDAO;
import dal.RoomDAO;
import model.Room;
import model.Service;
import dal.ServiceDAO;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import model.Category;
import model.Role;


/**
 *
 * @author DELL
 */
@WebServlet(name = "DetailInformationOutssss", urlPatterns = {"/DetailInformationOutssss"})
public class GetDetailInformation extends HttpServlet {

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
            out.println("<title>Servlet DetailInformationOut</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailInformationOut at " + request.getContextPath() + "</h1>");
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
        String bookingId = request.getParameter("bookingId");
        
        
        BookingDAO bkd = new BookingDAO();
        Booking bk = bkd.getBookingById(Integer.parseInt(bookingId));
        AccountDAO ac = new AccountDAO();
        
        Account acc = bk.getAccount();
        
        
       ServiceDAO sd = new  ServiceDAO();
       List<Service> listSV = sd.getServiceUsageByBookingId(Integer.parseInt(bookingId));long total=0;
        for (Service service : listSV) {
            total+=service.getPrice()*service.getQuantity();
        }
        
        Account account = ac.getAccountInfoById(String.valueOf(acc.getAccountId()));
        String email = account.getEmail();
        System.out.println("Hello");
      
        Room room = bk.getRoom();
        
        String roomnumber = room.getRoomNumber();
        Role roles = account.getRole();
        
        int roleid=roles.getRoleId();
        String role;
        int temp =roleid;
        if(temp==1){
            role="Owner";
        }
        else if(temp==2){
            role="Staff";
        }else{
            role="Customer";
        }
        Date createDate = account.getCreatedDate();
        String address = account.getAddress();
        String phone = account.getPhone();
        

        
        LocalDateTime checkin = bk.getCheckInDate();
        LocalDateTime checkout = bk.getCheckOutDate();
        long diffInMillies = Math.abs(
    Timestamp.valueOf(checkout).getTime() - Timestamp.valueOf(checkin).getTime()
);
    long daysBetween = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

    int discont = bkd.countBookingByAccountID(String.valueOf(acc.getAccountId()));
    if(role.equals("Owner")){
        discont=20;
    }else if(role.equals("Staff")){
        discont=10;
    }
    double temap=discont*5;
    double discountrate = (100-temap)/100;     
        
        Double price;
        Category cate = bk.getRoom().getCategory();
        price = cate.getPricePerNight()*daysBetween;
        double totalPrice = price*discountrate;
        
        request.setAttribute("roomnum", roomnumber);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);
        request.setAttribute("createdate", createDate);
        request.setAttribute("role", role);
        request.setAttribute("bookingId", bookingId);
        request.setAttribute("email", email);
        request.setAttribute("roomType", cate.getRoomType());
        request.setAttribute("bookingDate", bk.getBookingDate());
        request.setAttribute("checkInDate", bk.getCheckInDate());
        request.setAttribute("checkOutDate", bk.getCheckOutDate());
        request.setAttribute("note", bk.getNote());
        request.setAttribute("customerName", acc.getFullName());
        request.setAttribute("accoutID",acc.getAccountId());
        request.setAttribute("status", bk.getBookingStatus());
        request.setAttribute("pernight", cate.getPricePerNight());
        request.setAttribute("nightstay", daysBetween);
        request.setAttribute("dc", discont*5);
        request.setAttribute("price", totalPrice);
        request.setAttribute("serviceList", listSV);
        request.setAttribute("totalservice", total);
        
        request.getRequestDispatcher("bookingdetailcheckout.jsp").forward(request, response);
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
