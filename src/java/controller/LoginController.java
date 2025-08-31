package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import dal.AccountDAO;
import java.io.IOException;

@WebServlet(name ="LoginController", urlPatterns ={"/LoginController"})
public class LoginController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.login(email, password);
        if (account != null) {
            String role = account.getRole().getRoleName();
            HttpSession session = request.getSession();
            session.setAttribute("role", role);
            session.setAttribute("status", account.getStatus());
            session.setAttribute("account", account);
            response.sendRedirect("index.jsp");  // Chuyển hướng về trang chính
        } else {
            request.setAttribute("errorMessage", "Invalid email or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }

}
