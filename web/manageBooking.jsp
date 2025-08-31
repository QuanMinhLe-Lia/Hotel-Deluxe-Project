<%-- 
    Document   : manageBooking
    Created on : Mar 4, 2025, 4:00:54 PM
    Author     : Overlordil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang='vi'>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/2.2.2/css/dataTables.dataTables.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
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
        <link href="${pageContext.request.contextPath}/css/styleManageBooking.css" rel="stylesheet">

        <title>Manage Booking</title>
        
        <script>
            function showAlert(message) {
                alert(message);
            }
            <% String alert = (String) request.getAttribute("alert");
            if (alert != null && !alert.isEmpty()) { %>
            showAlert('<%= alert %>');
            <% } %>
        </script>
    </head>
    <body>
        <div class="parent">
            <div class="div2">
                <%@ include file="includes/sidebar.jsp" %>
            </div>
            <div class="div3">

                <div class="fake-thead-filter">
                    <div id="activeFilters" style="margin: 10px 0;"></div>
                    <table class="fake-filter-table">
                        <tr class="filter-row"></tr>
                    </table>
                </div>

                <table id="bookingTable" border="1" cellpadding="10" cellspacing="0" style="width:100%; border-collapse: collapse; text-align: left;">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Room Number</th>
                            <th>Room Type</th>
                            <th>Booking Date</th>
                            <th>Customer</th>
                            <th>Status</th>
                            <th>Price Per Night</th>
                            <th>Total Price</th>
                            <th>Detail</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="booking" items="${bookingList}">
                            <tr>
                                <td>${booking.bookingId}</td>
                                <td>${booking.room.roomNumber}</td>
                                <td>${booking.room.category.categoryName}</td>
                                <td>
                                    <span class="date-part">${fn:substring(booking.bookingDate, 0, 10)}</span><br>
                                    <span class="time-part">${fn:substring(booking.bookingDate, 11, 19)}</span>
                                </td>
                                <td>${booking.account.fullName}</td>
                                <td>${booking.bookingStatus}</td>
                                <td>${booking.room.category.pricePerNight}</td>
                                <td>${booking.totalPrice}</td>
                                <td><button type="button" class="btn btn-detail" data-toggle="modal" data-target="#modal-detail-booking"
                                            data-bookingid="${booking.bookingId}"
                                            data-roomnumber="${booking.room.roomNumber}"
                                            data-roomtype="${booking.room.category.categoryName}"
                                            data-bookingdate="${fn:substring(booking.bookingDate, 0, 10)} ${fn:substring(booking.bookingDate, 11, 19)}"
                                            data-checkindate="${fn:substring(booking.checkInDate, 0, 10)} ${fn:substring(booking.checkInDate, 11, 19)}"
                                            data-checkoutdate="${fn:substring(booking.checkOutDate, 0, 10)} ${fn:substring(booking.checkOutDate, 11, 19)}"
                                            data-totalprice="${booking.totalPrice}"
                                            data-bookingstatus="${booking.bookingStatus}"
                                            data-note="${booking.note}"
                                            data-accountfullname="${booking.account.fullName}" 
                                            data-pricePerNight="${booking.room.category.pricePerNight}">Detail</button></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${booking.bookingStatus == 'Not Yet'}">
                                            <form action="manageBooking" method="post" onsubmit="event.preventDefault(); confirmAction(this, event);">
                                                <input type="hidden" name="bookingId" value="${booking.bookingId}" />
                                                <button type="submit" name="submit" value="accept" class="btn btn-accept">Accept</button>
                                                <button type="submit" name="submit" value="cancel" class="btn btn-cancel">Cancel</button>
                                            </form>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!--Modal detail booking-->
            <div class="modal modal-pull-right" data-easein="fadeInRight" data-easeout="fadeOutRight" id="modal-detail-booking" tabindex="-1" role="dialog" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content animated fadeInRight">
                        <div class="modal-body">
                            <div class="row modal-close">
                                <div class="col-md-12 padding-bottom-8 padding-top-8 bg-silver">
                                    <h4 class="pull-left"><b>Booking Detail For Booking: </b><b id="bookingId"></b></h4>
                                    <ul class="list-unstyled list-inline text-right">
                                        <li class="close-right-modal">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="row" style="margin-top: 15px;">
                                <div class="col-md-12">
                                    <div class="booking-detail-content">
                                        <div class="form-horizontal">
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Booking Date</label>
                                                <div class="col-sm-8 tabular-border">
                                                    <p class="form-control-static" id="bookingDate"></p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Check In Time</label>
                                                <div class="col-sm-8 tabular-border">
                                                    <p class="form-control-static" id="checkInDate"></p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Check Out Time</label>
                                                <div class="col-sm-8 tabular-border">
                                                    <p class="form-control-static" id="checkOutDate"></p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Price Per Night</label>
                                                <div class="col-sm-8 tabular-border">
                                                    <p class="form-control-static" id="pricePerNight"></p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Total Price</label>
                                                <div class="col-sm-8 tabular-border">
                                                    <p class="form-control-static" id="totalPrice"></p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Booking Status</label>
                                                <div class="col-sm-8 tabular-border">
                                                    <p class="form-control-static" id="bookingStatus"></p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Note</label>
                                                <div class="col-sm-8 tabular-border">
                                                    <p class="form-control-static" id="note"></p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Room Number</label>
                                                <div class="col-sm-8 tabular-border">
                                                    <p class="form-control-static" id="roomNumber"></p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Room Type</label>
                                                <div class="col-sm-8 tabular-border">
                                                    <p class="form-control-static" id="roomType"></p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Customer's Name</label>
                                                <div class="col-sm-8 tabular-border">
                                                    <p class="form-control-static" id="fullName"></p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-sm-12 text-right">
                                                    <button type="button" class="btn btn-silver btn-flat" data-dismiss="modal" style="background-color: #DC143C; color: white;">Close</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </body>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/2.2.2/js/dataTables.js"></script>
    <script src="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/2.3.1/list.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/manageBookingScript.js"></script>
</html>

