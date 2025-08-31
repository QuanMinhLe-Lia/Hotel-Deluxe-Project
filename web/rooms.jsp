<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Deluxe - Free Bootstrap 4 Template by Colorlib</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

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
    </head>
    <body>
        <%@include file="includes/navbar.jsp" %>

        <div class="hero-wrap" style="background-image: url('images/bg_1.jpg');">
            <div class="overlay"></div>
            <div class="container">
                <div class="row no-gutters slider-text d-flex align-itemd-end justify-content-center">
                    <div class="col-md-9 ftco-animate text-center d-flex align-items-end justify-content-center">
                        <div class="text">
                            <p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.jsp">Home</a></span> <span>About</span></p>
                            <h1 class="mb-4 bread">Rooms</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <section class="ftco-section bg-light">
            <div class="container">
                <div class="row">
                    <div class="col-lg-9">
                        <div class="row">
                            <c:forEach var="c" items="${list}">
                                <div class="col-sm col-md-6 col-lg-4 ftco-animate">
                                    <div class="room">
                                        <!-- Thay ??i href ?? truy?n categoryId -->
                                        <a href="room-detail?categoryId=${c.getCategoryId()}&checkin=${param.checkin}&checkout=${param.checkout} " class="img d-flex justify-content-center align-items-center" style="background-image: url(${c.getImage()});">
                                            <div class="icon d-flex justify-content-center align-items-center">
                                                <span class="icon-search2"></span>
                                            </div>
                                        </a>
                                        <div class="text p-3 text-center">
                                            <!-- Thay ??i href ?? truy?n categoryId -->
                                            <h3 class="mb-3"><a href="room-detail?categoryId=${c.getCategoryId()}&checkin=${param.checkin}&checkout=${param.checkout} ">${c.getCategoryName()}</a></h3>

                                            <p><span class="price mr-2"><fmt:formatNumber value="${c.getPricePerNight()}" type="number" maxFractionDigits="0"/>VND</span> <span class="per">1 night</span></p>
                                            <ul class="list">
                                                <li><span>Max:</span> ${c.getCapacity()}</li>
                                                <li><span>Size:</span> ${c.getSize()} m2</li>
                                                <li><span>Bed:</span> ${c.getBed()}</li>
                                            </ul>
                                            <hr>
                                            <!-- Thay ??i href ?? truy?n categoryId -->
                                            <p class="pt-1"><a href="room-detail?categoryId=${c.getCategoryId()}&checkin=${param.checkin}&checkout=${param.checkout} " class="btn-custom">View Room Details <span class="icon-long-arrow-right"></span></a></p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="col-lg-3 sidebar">
                        <div class="sidebar-wrap bg-light ftco-animate">
                            <h3 class="heading mb-4">Advanced Search</h3>
                            <form action="filter">
                                <div class="fields">
                                    <!-- Check In Date -->
                                    <div class="form-group">
                                        <input type="date" name="checkin" id="checkin_date" class="form-control" 
                                               placeholder="Check In Date" value="<%= request.getParameter("checkin") != null ? request.getParameter("checkin") : "" %>">
                                    </div>

                                    <!-- Check Out Date -->
                                    <div class="form-group">
                                        <input type="date" name="checkout" id="checkout_date" class="form-control" 
                                               placeholder="Check Out Date" value="<%= request.getParameter("checkout") != null ? request.getParameter("checkout") : "" %>">
                                    </div>

                                    <% if (request.getAttribute("errorMessage") != null) { %>
                                    <div style="color: red;">
                                        <%= request.getAttribute("errorMessage") %>
                                    </div>
                                    <% } %>

                                    <!-- Room Type Dropdown -->
                                    <div class="form-group">
                                        <select name="roomType" id="" class="form-control">
                                            <option value="">Room Type</option>
                                            <c:if test="${not empty list}">
                                                <c:forEach var="c" items="${list}">
                                                    <option value="${c.getCategoryName()}" ${param.roomType == c.getCategoryName() ? "selected" : ""}>
                                                        ${c.getCategoryName()}
                                                    </option>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </div>

                                    <!-- Capacity Dropdown -->
                                    <div class="form-group">
                                        <select name="capacity" id="" class="form-control">
                                            <option value="">Capacity</option>
                                            <c:forEach var="c" items="${capacities}">
                                                <option value="${c.getCapacity()}" ${param.capacity == c.getCapacity() ? "selected" : ""}>
                                                    ${c.getCapacity()}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Bed Dropdown -->
                                    <div class="form-group">
                                        <select name="bed" id="" class="form-control">
                                            <option value="">Bed</option>
                                            <c:forEach var="b" items="${beds}">
                                                <option value="${b.getBed()}" ${param.bed == (b.getBed() + '') ? "selected" : ""}>
                                                    ${b.getBed()}
                                                </option>
                                            </c:forEach>
                                            <c:if test="${empty beds}">
                                                <option value="">No beds available</option>
                                            </c:if>
                                        </select>
                                    </div>

                                    <!-- Filter by Price Checkboxes -->
                                    <div class="form-group">
                                        <div class="filter-price">
                                            <h3>Filter by Price</h3>
                                            <div class="form-check">
                                                <input type="radio" class="form-check-input" name="priceRange" value="0-100000" id="price1"
                                                       <%= "0-100000".equals(request.getParameter("priceRange")) ? "checked" : "" %>>
                                                <label class="form-check-label" for="price1">0 - 100,000</label>
                                            </div>
                                            <div class="form-check">
                                                <input type="radio" class="form-check-input" name="priceRange" value="100000-200000" id="price2"
                                                       <%= "100000-200000".equals(request.getParameter("priceRange")) ? "checked" : "" %>>
                                                <label class="form-check-label" for="price2">100,000 - 200,000</label>
                                            </div>
                                            <div class="form-check">
                                                <input type="radio" class="form-check-input" name="priceRange" value="200000-300000" id="price3"
                                                       <%= "200000-300000".equals(request.getParameter("priceRange")) ? "checked" : "" %>>
                                                <label class="form-check-label" for="price3">200,000 - 300,000</label>
                                            </div>
                                            <div class="form-check">
                                                <input type="radio" class="form-check-input" name="priceRange" value="300000-400000" id="price4"
                                                       <%= "300000-400000".equals(request.getParameter("priceRange")) ? "checked" : "" %>>
                                                <label class="form-check-label" for="price4">300,000 - 400,000</label>
                                            </div>
                                            <div class="form-check">
                                                <input type="radio" class="form-check-input" name="priceRange" value="400000-500000" id="price5"
                                                       <%= "400000-500000".equals(request.getParameter("priceRange")) ? "checked" : "" %>>
                                                <label class="form-check-label" for="price5">400,000 - 500,000</label>
                                            </div>
                                            <div class="form-check">
                                                <input type="radio" class="form-check-input" name="priceRange" value="500000-4000000" id="price6"
                                                       <%= "500000-4000000".equals(request.getParameter("priceRange")) ? "checked" : "" %>>
                                                <label class="form-check-label" for="price6">500,000 - 4,000,000</label>
                                            </div>


                                        </div>
                                    </div>
                                </div>


                                <div class="form-group">
                                    <input type="submit" value="Search" class="btn btn-primary py-3 px-5">
                                </div>

                                <div class="form-group">
                                    <input type="button" class="btn btn-secondary py-3 px-5" value="Reset" onclick="resetFilters()">
                                </div>

                                <script>
                                    function resetFilters() {
                                        window.location.href = 'http://localhost:9999/hotel_Deluxe_cloneIfBroken1/rooms'; // ???ng d?n c?n reset
                                    }
                                </script>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <footer class="ftco-footer ftco-bg-dark ftco-section">
            <div class="container">
                <div class="row mb-5">
                    <div class="col-md">
                        <div class="ftco-footer-widget mb-4">
                            <h2 class="ftco-heading-2">Deluxe Hotel</h2>
                            <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                            <ul class="ftco-footer-social list-unstyled float-md-left float-lft mt-5">
                                <li class="ftco-animate"><a href="#"><span class="icon-twitter"></span></a></li>
                                <li class="ftco-animate"><a href="#"><span class="icon-facebook"></span></a></li>
                                <li class="ftco-animate"><a href="#"><span class="icon-instagram"></span></a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md">
                        <div class="ftco-footer-widget mb-4 ml-md-5">
                            <h2 class="ftco-heading-2">Useful Links</h2>
                            <ul class="list-unstyled">
                                <li><a href="#" class="py-2 d-block">Blog</a></li>
                                <li><a href="#" class="py-2 d-block">Rooms</a></li>
                                <li><a href="#" class="py-2 d-block">Amenities</a></li>
                                <li><a href="#" class="py-2 d-block">Gift Card</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md">
                        <div class="ftco-footer-widget mb-4">
                            <h2 class="ftco-heading-2">Privacy</h2>
                            <ul class="list-unstyled">
                                <li><a href="#" class="py-2 d-block">Career</a></li>
                                <li><a href="#" class="py-2 d-block">About Us</a></li>
                                <li><a href="#" class="py-2 d-block">Contact Us</a></li>
                                <li><a href="#" class="py-2 d-block">Services</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md">
                        <div class="ftco-footer-widget mb-4">
                            <h2 class="ftco-heading-2">Have a Questions?</h2>
                            <div class="block-23 mb-3">
                                <ul>
                                    <li><span class="icon icon-map-marker"></span><span class="text">203 Fake St. Mountain View, San Francisco, California, USA</span></li>
                                    <li><a href="#"><span class="icon icon-phone"></span><span class="text">+2 392 3929 210</span></a></li>
                                    <li><a href="#"><span class="icon icon-envelope"></span><span class="text">info@yourdomain.com</span></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 text-center">

                        <p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                            Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart color-danger" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                            <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
                    </div>
                </div>
            </div>
        </footer>



        <!-- loader -->
        <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>


        <script src="js/jquery.min.js"></script>
        <script src="js/jquery-migrate-3.0.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.easing.1.3.js"></script>
        <script src="js/jquery.waypoints.min.js"></script>
        <script src="js/jquery.stellar.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/jquery.magnific-popup.min.js"></script>
        <script src="js/aos.js"></script>
        <script src="js/jquery.animateNumber.min.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
        <script src="js/jquery.timepicker.min.js"></script>
        <script src="js/scrollax.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
        <script src="js/google-map.js"></script>
        <script src="js/main.js"></script>

    </body>
</html>