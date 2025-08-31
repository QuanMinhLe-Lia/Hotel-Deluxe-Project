<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OTP Verification</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>Verify Your Email</h2>
        <p>We've sent a verification code to your email. Please enter it below.</p>
        <form action="VerifyOTPServlet" method="post">
            <input type="hidden" name="email" value="<%= request.getParameter("email") %>">
            <div class="mb-3">
                <label for="otp" class="form-label">Enter OTP</label>
                <input type="text" class="form-control" name="otp" required>
            </div>
            <% String message = (String) request.getAttribute("message"); %>
            <% if (message != null) { %>
            <div class="alert alert-danger"><%= message %></div>
            <% } %>
            <button type="submit" class="btn btn-primary">Verify OTP</button>
        </form>
    </div>
</body>
</html>
