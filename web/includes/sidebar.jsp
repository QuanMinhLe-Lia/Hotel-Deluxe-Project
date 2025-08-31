<%-- 
    Document   : sidebar
    Created on : Feb 13, 2025, 2:46:22 PM
    Author     : Overlordil
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i" rel="stylesheet">
        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link rel="stylesheet" href="css/animate.css">
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/owl.theme.default.min.css">
        <link rel="stylesheet" href="css/magnific-popup.css">
        <link rel="stylesheet" href="css/aos.css">
        <link rel="stylesheet" href="css/ionicons.min.css">
        <link rel="stylesheet" href="css/bootstrap-datepicker.css">
        <link rel="stylesheet" href="css/jquery.timepicker.css">
        <link rel="stylesheet" href="css/flaticon.css">
        <link rel="stylesheet" href="css/icomoon.css">
        <link rel="stylesheet" href="css/style.css">
        <meta charset="UTF-8">
        <%
            String currentUri = request.getRequestURI();
            String activePage = currentUri.substring(currentUri.lastIndexOf("/") + 1);
            String role = (String) session.getAttribute("role");
        %>
        <link href="${pageContext.request.contextPath}/css/StyleSidebar.css" rel="stylesheet">
    </head>
    <body>
        <div class="sidebar">
            <h2>Menu</h2>
            <a href="home" class="<%= "index.jsp".equals(activePage) ? "active" : ""%>">
                <i class="icon-home"></i> Home
            </a>
            <% if (role != null && role.equals("Owner")) {%>
            <a href="dashboard" class="<%= "dashboard.jsp".equals(activePage) ? "active" : ""%>">
                <i class="icon-dashboard"></i> Dashboard
            </a>
            <% }%>
            <a href="information.jsp" class="<%= "information.jsp".equals(activePage) ? "active" : ""%>">
                <i class="icon-person"></i> Profile
            </a>
            <% if (role != null && role.equals("Owner")) {%>
            <a href="manageBooking" class="<%= "manageBooking.jsp".equals(activePage) ? "active" : ""%>">
                <i class="ion-ios-bookmarks"></i> Booking Request
            </a>
            <a href="manageUser" class="<%= "manageUser.jsp".equals(activePage) ? "active" : ""%>">
                <i class="icon-list-ul"></i> Manage User
            </a>
            <a href="manageRoom" class="<%= "manageRoom.jsp".equals(activePage) ? "active" : ""%>">
                <i class="icon-room"></i> Manage Room
            </a>
            <a href="manageService" class="<%= "manageService.jsp".equals(activePage) ? "active" : ""%>">
                <i class="icon-local_laundry_service"></i> Manage Service
            </a>
            <% }%>
            <% if (role != null && role.equals("Staff")) {%>
            <a href="staff-checkin.jsp" class="<%= "staff-checkin.jsp".equals(activePage) ? "active" : ""%>">
                <i class="ion-arrow-right-b"></i> Check In
            </a>
            <a href="ongoing.jsp" class="<%= "ongoing.jsp".equals(activePage) ? "active" : ""%>">
                <i class="icon-timer"></i> On Going
            </a>
            <a href="staff-checkout.jsp" class="<%= "staff-checkout.jsp".equals(activePage) ? "active" : ""%>">
                <i class="ion-arrow-left-b"></i> Check Out
            </a>
            <% }%>
        </div>
    </body>
</html>
