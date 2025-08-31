<%@ page import="model.Account" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    Account account = (Account) session.getAttribute("account");
    String role = (String) session.getAttribute("role");
%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">Deluxe</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ml-auto">
                
                <% if (account != null && ("Staff".equals(role))) { %>
                <li class="nav-item"><a href="CheckInLoader" class="nav-link">Staff Page</a></li>
                    <% } %>
                    <% if (account != null && ("Admin".equals(role) || "Owner".equals(role))) { %>
                <li class="nav-item"><a href="dashboard" class="nav-link">Dashboard</a></li>
                    <% } %>
                <li class="nav-item"><a href="home" class="nav-link">Home</a></li>
                <li class="nav-item"><a href="rooms" class="nav-link">Rooms</a></li>
                <li class="nav-item"><a href="contact.jsp" class="nav-link">Contact</a></li>

                <% if (account != null) { %>
                <li class="nav-item"><a href="userHistoryLoader" class="nav-link">Booking History</a></li>
                <li class="nav-item"><a href="ProfileControl" class="nav-link">Welcome, <%= account.getFullName() %></a></li>
                <li class="nav-item"><a href="LogoutController" class="nav-link">Logout</a></li>
                    <% } else { %>
                <li class="nav-item"><a href="login.jsp" name="login" class="nav-link">Login</a></li>
                    <% } %>
            </ul>
        </div>
    </div>
</nav>
