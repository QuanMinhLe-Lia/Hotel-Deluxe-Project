<%-- 
    Document   : verify
    Created on : Feb 5, 2025, 3:30:40 AM
    Author     : AD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <form action="verify" method="post">
    <label>Nhập mã xác nhận</label>
    <input type="text" name="code" required>
    <input type="hidden" name="email" value="<%= request.getParameter("email") %>">
    <button type="submit">Xác nhận</button>
</form>

</html>
