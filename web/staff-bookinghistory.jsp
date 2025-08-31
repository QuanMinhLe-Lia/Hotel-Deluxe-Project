<%-- 
    Document   : staff
    Created on : Mar 4, 2025, 4:00:54 PM
    Author     : DELL
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Booking"%>

<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap4.min.css">
    
    <!-- Include jQuery and DataTables JS for table interactivity -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap4.min.js"></script>
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
        <title>Staff Page</title>
        <style>
            .parent {
                display: grid;
                grid-template-columns: 200px 1fr 1fr 1fr 1fr;
                grid-template-rows: auto 1fr 1fr 1fr 1fr;
                grid-column-gap: 0px;
                grid-row-gap: 0px;
                height: 100vh;
            }

            .div2 {
                grid-area: 2 / 1 / 6 / 2;
                background-color: #222;
                color: white;
                padding: 20px;
                display: flex;
                flex-direction: column;
                gap: 10px;
                background-color: #d3d3d3;
            }

            .div2 h2 {
                text-align: center;
                width: 100%;
            }

            .div3 {
                grid-area: 2 / 2 / 6 / 6;
                background-color: #f0f0f0;
                padding: 20px;
            }

            .div2 a:hover {
                color: #555;
            }

            .div3 table thead th {
                background-color: #dbeeff; /* Light Blue */
                color: #444;
                padding: 10px;
            }

            .status-done {
                color: #8BC34A;
            }
            .status-on-going {
                color: #FF9800;
            }
            .status-cancel {
                color: #FF5722;
            }


        </style>
    </head>
    <body>
        <%@include file="includes/navbar.jsp" %>
        <div class="parent">
            <div class="div2">
                <h2>Menu</h2>
                <a href="index.jsp"><i class="icon-home"></i> Home</a>
                <a href="#"><i class="icon-person"></i> Information</a>

                <a href="CheckInLoader"><i class="ion-arrow-right-b"></i> Check In</a>
     
                <a href="CheckOutLoader"><i class="ion-arrow-left-b"></i> Check Out</a>
                <a href="LoadAllBookingInfo"><i class="ion-ios-paper"></i> Booking History</a>
   
            </div>
            <div class="div3">
                <h2>Booking History</h2>

                <table border="1" cellpadding="10" id="table" cellspacing="0" style="width:100%; border-collapse: collapse; text-align: left;">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Booking Id</th>
                            <th>Room Type</th>
                            <th>Booking Date</th>
                            <th>Check In Date</th>
                            <th>Check Out Date</th>
                            <th>Note</th>
                            <th>Name</th>
                            <th>ID</th>
                            <th>Status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${booking}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${item.bookingId}</td>
                             
                                <td>${item.room.category.categoryName}</td>
                                <td>${fn:substring(item.bookingDate, 8, 10)}/${fn:substring(item.bookingDate, 5, 7)}/${fn:substring(item.bookingDate, 0, 4)}</td>
<td>${fn:substring(item.checkInDate, 8, 10)}/${fn:substring(item.checkInDate, 5, 7)}/${fn:substring(item.checkInDate, 0, 4)}</td>
<td>${fn:substring(item.checkOutDate, 8, 10)}/${fn:substring(item.checkOutDate, 5, 7)}/${fn:substring(item.checkOutDate, 0, 4)}</td>
                                <td>${item.note}</td>
                                <td>${item.account.fullName}</td>
                                <td>${item.account.accountId}</td>
                                <td><c:choose>
                                        <c:when test="${item.bookingStatus == 'Done'}">
                                            <span class="status-done">${item.bookingStatus}</span>
                                        </c:when>
                                        <c:when test="${item.bookingStatus == 'On Going'}">
                                            <span class="status-on-going">${item.bookingStatus}</span>
                                        </c:when>
                                        <c:when test="${item.bookingStatus == 'Cancel'}">
                                            <span class="status-cancel">${item.bookingStatus}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-not-yet">${item.bookingStatus}</span>
                                        </c:otherwise>
                                    </c:choose></td>
                                <td><a href="DetailInformationOut?bookingId=${item.bookingId}" class="btn btn-primary btn-sm"><i class="fas fa-search"></i></a></td>
        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <script>$('#table').DataTable({
            "order": [[0, "asc"]], // Sort by Booking ID in ascending order
            "paging": true,
            "searching": true,
            "info": true,
            "responsive": true
          });</script>
    </body>
</html>