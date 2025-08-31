<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>
            * {
                font-family: 'Poppins', sans-serif;
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            body {
                user-select: none;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background: hsl(218deg 50% 91%);
            }
            .screen-1 {
                background: hsl(213deg 85% 97%);
                padding: 2em;
                display: flex;
                flex-direction: column;
                border-radius: 30px;
                box-shadow: 0 0 2em hsl(231deg 62% 94%);
                gap: 2em;
            }
            .logo {
                text-align: center;
                font-size: 2em;
                font-weight: bold;
                color: hsl(233deg 36% 38%);
            }
            .email, .password {
                background: white;
                box-shadow: 0 0 2em hsl(231deg 62% 94%);
                padding: 1em;
                display: flex;
                flex-direction: column;
                gap: 0.5em;
                border-radius: 20px;
                color: hsl(0deg 0% 30%);
            }
            input {
                outline: none;
                border: none;
                font-size: 1em;
                padding: 0.5em;
                width: 100%;
            }
            input::placeholder {
                color: hsl(0deg 0% 50%);
                font-size: 0.9em;
            }
            .login {
                padding: 1em;
                background: hsl(233deg 36% 38%);
                color: white;
                border: none;
                border-radius: 30px;
                font-weight: 600;
                cursor: pointer;
                transition: 0.3s;
            }
            .login:hover {
                background: hsl(233deg 50% 50%);
            }
            .footer {
                display: flex;
                font-size: 0.8em;
                color: hsl(0deg 0% 37%);
                justify-content: space-between;
                padding-top: 1em;
            }
            .footer span {
                cursor: pointer;
                text-decoration: underline;
            }

            /* Thêm style cho thông báo lỗi */
            .error-message {
                color: red;
                font-size: 0.9em;
                text-align: center;
            }
        </style>
        <script>
            function showAlert(message) {
                alert(message);
            }
            <% String alert = (String) request.getAttribute("alert");
                    if (alert != null && !alert.isEmpty()) {%>
            showAlert('<%= alert%>');
            <% } %>
        </script>
    </head>
    <body>
        <div class="screen-1">
            <div class="logo">Deluxe Hotel</div>

            <!-- Hiển thị thông báo lỗi nếu có -->
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
            <div class="error-message"><%= errorMessage%></div>
            <%
                }
            %>

            <form action="LoginController" method="POST">
                <div class="email">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" placeholder="Username@gmail.com">
                </div>

                <div class="password">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="············">
                </div>

                <button class="login" name="button" id="button" type="submit">Login</button>
            </form>

            <div class="footer">
                <a href="register.jsp"><span>Sign Up</span></a>
                <a href="forgetpassword.jsp"><span>Forgot Password?</span></a>
            </div>
        </div>
    </body>
</html>
