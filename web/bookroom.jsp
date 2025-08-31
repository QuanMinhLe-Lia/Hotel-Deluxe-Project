<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Book Room</title>
        <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i" rel="stylesheet">

        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link rel="stylesheet" href="css/animate.css">

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
        <script>
            function updateTotalPrice() {
                let roomTotal = parseFloat(document.getElementById("roomTotal").value);
                let serviceCheckboxes = document.querySelectorAll("input[name='selectedServices']:checked");
                let totalPrice = roomTotal;

                serviceCheckboxes.forEach(checkbox => {
                    totalPrice += parseFloat(checkbox.getAttribute("data-price"));
                });

                document.getElementById("totalPrice").textContent = totalPrice.toFixed(2);
            }
        </script>
    </head>
    <body>
            <%@ include file="includes/navbar.jsp" %>

        <h1>Book Room</h1>
        <p>Category Name: ${categoryName}</p>
        <p>Check-in Date: ${checkinDate}</p>
        <p>Check-out Date: ${checkoutDate}</p>
        <p>Day Stay: ${day}</p>
        <p>CategoryID: ${categoryId}</p>
        <p>Price Per Night: ${pricepernight}</p>
        <p>Room Total Price: ${roomtotal}</p>

        <form action="ProcessBookingServlet" method="post">
            <input type="hidden" name="categoryName" value="${categoryName}">
            <input type="hidden" name="checkinDate" value="${checkinDate}">
            <input type="hidden" name="checkoutDate" value="${checkoutDate}">
            <input type="hidden" id="roomTotal" value="${roomtotal}">

            <table border="1" cellpadding="10" cellspacing="0" style="width:100%; border-collapse: collapse; text-align: left;">
                <thead>
                    <tr>
                        <th>Service Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Select</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listservice}">
                        <tr>
                            <td>${item.serviceName}</td>
                            <td>${item.description}</td>
                            <td>${item.price}</td>
                            <td>
                                <input type="checkbox" name="selectedServices" value="${item.serviceName}" data-price="${item.price}" onchange="updateTotalPrice()">
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <label for="note">Note:</label><br>
            <textarea id="note" name="note" rows="4" cols="50"></textarea><br><br>
            <p>Total Price: <span id="totalPrice">${roomtotal}</span></p>
            <input type="submit" value="Confirm Booking">
        </form>
    </body>
</html>
