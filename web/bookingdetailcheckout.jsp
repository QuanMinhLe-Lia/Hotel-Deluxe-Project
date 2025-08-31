<%-- 
    Document   : staff
    Created on : Mar 4, 2025, 4:00:54 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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

            .bookingorder {
                display: grid;
                grid-template-columns: repeat(6, 1fr);
                grid-template-rows: repeat(5, 1fr);
                grid-column-gap: 0px;
                grid-row-gap: 0px;
            }
            .b1 {
                grid-area: 1 / 1 / 6 / 4;
            }
            .b2 {
                grid-area: 1 / 4 / 6 / 7;
            }

            .bookingorder {
                display: grid;
                grid-template-columns: 1fr 1fr; /* 2 cột */
                grid-template-rows: auto auto; /* 2 hàng */
                gap: 20px;
                padding: 20px;
                background: #f8f9fa;
                border-radius: 10px;
                box-shadow: 2px 2px 15px rgba(0, 0, 0, 0.1);
            }

            /* Booking Detail bên trái */
            .b1 {
                grid-column: 1 / 2;
                grid-row: 1 / 3; /* Chiếm cả 2 hàng */
            }

            /* Service bên phải (trên) */
            .b2 {
                grid-column: 2 / 3;
                grid-row: 1 / 2;
            }

            /* Payment bên phải (dưới) */
            .payment {
                grid-column: 2 / 3;
                grid-row: 2 / 3;
            }

            /* Nút Confirm nằm dưới Payment */
            .buttons {
                grid-column: 2 / 3;
                grid-row: 3 / 4;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            
            /* Thiết lập chung cho bảng */
table {
    width: 100%;
    border-collapse: collapse;
    background: #fff;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    overflow: hidden;
}

/* Header của bảng */
table thead {
    background: #007bff; /* Màu xanh dương */
    color: white;
}

table thead th {
    padding: 12px;
    text-align: left;
    font-weight: bold;
}

/* Dòng dữ liệu */
table tbody tr {
    border-bottom: 1px solid #ddd;
    transition: background 0.3s ease-in-out;
}

/* Hover hiệu ứng */
table tbody tr:hover {
    background: #f1f1f1;
}

/* Ô dữ liệu */
table td {
    padding: 10px;
    color: #333;
}

/* Căn giữa nội dung số */
table td:nth-child(2),
table td:nth-child(3),
table td:nth-child(4) {
    text-align: center;
}

/* Bo tròn góc bảng */
table thead th:first-child {
    border-top-left-radius: 8px;
}

table thead th:last-child {
    border-top-right-radius: 8px;
}

table tbody tr:last-child td:first-child {
    border-bottom-left-radius: 8px;
}

table tbody tr:last-child td:last-child {
    border-bottom-right-radius: 8px;
}

/* Style cho nút Confirm */
.confirm {
    background: #28a745; /* Màu xanh lá cây */
    color: white;
    font-size: 16px;
    font-weight: bold;
    padding: 12px 20px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease-in-out;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
}

/* Hover hiệu ứng */
.confirm:hover {
    background: #218838;
    box-shadow: 4px 4px 15px rgba(0, 0, 0, 0.3);
    transform: translateY(-2px);
}

/* Khi bấm vào */
.confirm:active {
    background: #1e7e34;
    transform: scale(0.98);
}

.cancel {
    background: #dc3545; /* Màu đỏ */
    color: white;
    font-size: 16px;
    font-weight: bold;
    padding: 12px 20px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease-in-out;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
}

/* Hover hiệu ứng */
.cancel:hover {
    background: #c82333;
    box-shadow: 4px 4px 15px rgba(0, 0, 0, 0.3);
    transform: translateY(-2px);
}

/* Khi bấm vào */
.cancel:active {
    background: #bd2130;
    transform: scale(0.98);
}
.confirm, .cancel {
    margin: 0 10px; /* Thêm khoảng cách ngang giữa các nút */
}
        </style>
    </head>
    <body>
        <%@include file="includes/navbar.jsp" %>
        <div class="parent">

            <div class="div2">
                 <h2>
        Menu
    </h2>
                <a href="index.jsp"><i class="icon-home"></i> Home</a>
                <a href="#"><i class="icon-person"></i> Information</a>
              
                <a href="CheckInLoader"><i class="ion-arrow-right-b"></i> Check In</a>
       
                <a href="CheckOutLoader"><i class="ion-arrow-left-b"></i> Check Out</a>
                <a href="LoadAllBookingInfo"><i class="ion-ios-paper"></i> Booking History</a>
        
            </div>
            <div class="div3">
                <h2><c:choose>
            <c:when test="${status == 'Not Yet'}">Check In</c:when>
            <c:when test="${status == 'On Going'}">Check Out</c:when>
            <c:when test="${status == 'Done' || status == 'Cancel'}">View History</c:when>
        </c:choose></h2>
                <a href="<c:choose>
            <c:when test='${status == "Not Yet"}'>CheckInLoader</c:when>
            <c:when test='${status == "On Going"}'>CheckOutLoader</c:when>
            <c:when test='${status == "Done" || status == "Cancel"}'>LoadAllBookingInfo</c:when>
         </c:choose>">
    <i class="ion-ios-arrow-back"></i> Go Back
</a>
                <div class="bookingorder">
                    <!-- Booking Detail (BÊN TRÁI) -->
                    <div class="b1">
                        <h4>Booking Detail</h4>
                        <div class="booking-detail-grid">
                            <p><strong>Booking ID:</strong> ${bookingId}</p>
                            <p><strong>Account ID:</strong> ${accoutID}</p>
                            <p><strong>Name:</strong> ${customerName}</p>
                            <p><strong>Email:</strong> ${email}</p>
                            <p><strong>Role:</strong> ${role}</p>
                            <p><strong>Address:</strong> ${address}</p>
                            <p><strong>Phone:</strong> ${phone}</p>
                            <p><strong>Create Date:</strong> ${createdate}</p>
                            <h4>Room:</h4>
                            <p><strong>Room Type:</strong> ${roomType}</p>
                            <p><strong>Room Number:</strong> ${roomnum}</p>
                            <p><strong>Booking Date:</strong> ${bookingDate}</p>
                            <p><strong>Check-in Date:</strong> ${checkInDate}</p>
                            <p><strong>Check-out Date:</strong> ${checkOutDate}</p>
                            <p><strong>Status:</strong> ${status}</p>
                            <p><strong>Note:</strong> ${note}</p>
                        </div>
                    </div>

                    <!-- Service (BÊN PHẢI, TRÊN) -->
                    <div class="b2">
                        <h4>Service</h4>
                        <table border="1" cellpadding="10" cellspacing="0" style="width:100%; border-collapse: collapse; text-align: left;">
                            <thead>
                                <tr>
                                    <th>Service</th>
                                    <th>Price</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${serviceList}">
                                    <tr>
                                        <td>${item.serviceName}</td>
                                        <td>${item.price}</td>
                                        
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <p><strong>Total: </strong>${totalservice}$</p>
                    </div>

                    <!-- Payment (BÊN PHẢI, DƯỚI Service) -->
                    <div class="payment">
                        <h4>Payment</h4>
                        <div class="booking-detail-grid">
                            <p><strong>Price per night:</strong> ${pernight}$</p>
                            <p><strong>Night Stay:</strong> ${nightstay}</p>
                            <p><strong>Discount:</strong> ${dc}%</p>
                            <p><strong>Total Price:</strong> ${price}$</p>
                        </div>
                    </div>

                    <!-- Nút Confirm (BÊN DƯỚI Payment) -->
                    <div class="buttons">
    <c:choose>
        <c:when test="${status == 'Not Yet'}">
            <!-- Nếu trạng thái là "Not Yet", hiển thị nút Check In và Cancel -->
            <form action="ChangeStateOnGoing" method="get" style="display:inline;">
                <input type="hidden" name="bookingId" value="${bookingId}">
                <button type="submit" class="confirm">Check In</button>
            </form>
            <form action="CancelBooking" method="get" style="display:inline;">
                <input type="hidden" name="bookingId" value="${bookingId}">
                <button type="submit" class="cancel">Cancel</button>
            </form>
        </c:when>
        <c:when test="${status == 'On Going'}">
            <!-- Nếu trạng thái là "On Going", hiển thị nút Check Out -->
            <form action="ChangeStateDone" method="get">
                <input type="hidden" name="bookingId" value="${bookingId}">
                <input type="hidden" name="price" value="${price}">
                <button type="submit" class="confirm">Check Out</button>
            </form>
        </c:when>
        <c:when test="${status == 'Done' || status == 'Cancel'}">
            <!-- Nếu trạng thái là "Done", không hiển thị nút nào -->
        </c:when>
    </c:choose>
</div>
                </div>


            </div>
        </div>
                        
                        
                        
    </body>
</html>
