package controller;

import dal.AccountDAO;
import emailSender.EmailUtil;
import java.io.IOException;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})

public class RegisterController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if(fullName.length()<6){
            request.setAttribute("message", "Username must have atleast 6 digits!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra email đã tồn tại chưa (giả sử AccountDAO có phương thức này)
        AccountDAO dao = new AccountDAO();
        if (dao.emailExists(email)) {
            request.setAttribute("message", "Email is already registered!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        try{
            int phoneint = Integer.parseInt(phone);
        }catch(NumberFormatException e){
            request.setAttribute("message", "Phone must be number!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        if(phone.length()!=10){
            request.setAttribute("message", "Phone must have 10 numbers!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        if(password.length()<6){
            request.setAttribute("message", "Password must have atleast 6 digits!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra mật khẩu có khớp không
        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Passwords do not match!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        

        // Tạo OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        // Lưu OTP vào session để xác nhận sau
        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("email", email);
        session.setAttribute("fullName", fullName);
        session.setAttribute("phone", phone);
        session.setAttribute("password", password);

        // Gửi OTP qua email
        EmailUtil.sendVerificationEmail(email, otp);

        // Chuyển hướng đến trang nhập OTP
        response.sendRedirect("verifyOTP.jsp?email=" + email);
    }
}
