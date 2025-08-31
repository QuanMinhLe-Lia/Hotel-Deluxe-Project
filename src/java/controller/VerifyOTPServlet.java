package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

@WebServlet(name = "VerifyOTPServlet", urlPatterns = {"/VerifyOTPServlet"})

public class VerifyOTPServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String enteredOTP = request.getParameter("otp");
        String generatedOTP = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("email");

        if (enteredOTP != null && generatedOTP != null && enteredOTP.equals(generatedOTP)) {
            // Lấy thông tin từ session
            String fullName = (String) session.getAttribute("fullName");
            String phone = (String) session.getAttribute("phone");
            String password = (String) session.getAttribute("password");

            // Lưu tài khoản vào database
            AccountDAO dao = new AccountDAO();
            Account account = new Account(fullName, email, password, phone);
            dao.insert(account);

            // Xóa session để tránh nhập lại OTP
            session.invalidate();

            // Chuyển hướng đến trang đăng nhập
            response.sendRedirect("login.jsp?success=registered");
        } else {
            request.setAttribute("message", "Invalid OTP! Please try again.");
            request.getRequestDispatcher("verifyOTP.jsp").forward(request, response);
        }
    }
}
