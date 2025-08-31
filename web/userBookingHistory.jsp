<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Booking History</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <!-- Include DataTables CSS for table styling and functionality -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap4.min.css">
    
    <!-- Include jQuery and DataTables JS for table interactivity -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap4.min.js"></script>

    <!-- Include Google Fonts for typography -->
    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i" rel="stylesheet">

    <!-- Include various CSS files for styling -->
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
    <!-- Include Font Awesome for star icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

    <!-- Custom CSS for star rating, popup, and feedback input -->
    <style>
      .stars {
        display: flex;
        flex-direction: row;
        font-size: 30px;
        cursor: pointer;
      }
      .stars i {
        color: lightgray;
        transition: color 0.3s;
      }
      .stars i.active {
        color: gold; /* Color for selected stars */
      }
      .stars i.hover {
        color: gold; /* Color for stars on hover */
      }
      .popup {
        display: none;
        position: fixed;
        top: 20px;
        right: 20px;
        background-color: white;
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 15px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        z-index: 1000;
      }
      .popup p {
        margin: 0 0 10px 0;
      }
      .popup button {
        background-color: #28a745;
        color: white;
        border: none;
        padding: 5px 15px;
        border-radius: 15px;
        cursor: pointer;
      }
      .popup button:hover {
        background-color: #218838;
      }
      .feedback-input {
        width: 100%;
        height: 100px;
        margin-top: 10px;
        padding: 5px;
        border: 1px solid #ccc;
        border-radius: 5px;
        resize: vertical;
      }
    </style>
  </head>
  <body>
    <!-- Include the navigation bar -->
    <%@include file="includes/navbar.jsp" %>

    <!-- Hero section with background image -->
    <div class="hero-wrap" style="background-image: url('images/bg_1.jpg');">
      <div class="overlay"></div>
      <div class="container">
        <div class="row no-gutters slider-text d-flex align-itemd-end justify-content-center">
          <div class="col-md-9 ftco-animate text-center d-flex align-items-end justify-content-center">
            <div class="text">
              <p class="breadcrumbs mb-2"><span class="mr-2"><a href="home">Home</a></span> <span>History</span></p>
              <h1 class="mb-4 bread">Booking History</h1>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Main content section for booking history -->
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-10">
          <h2 class="mb-4 text-center">History</h2>
          <!-- Table to display booking history -->
          <table class="table table-bordered table-hover" id="historytable" style="width:150%; margin-left: -25%;">
            <thead class="thead-dark">
              <p>${err}</p> <!-- Display error message if any -->
              <tr>
                <th>Booking ID</th>
                <th>Booking Date</th>
                <th>Room Number</th>
                <th>Room Type</th>
                <th>Check-in Date</th>
                <th>Check-out Date</th>
                <th>Total Price</th>
                <th>Status</th>
                <th>Note</th>
                <th></th> <!-- Column for action buttons -->
              </tr>
            </thead>
            <tbody>
              <!-- Loop through booking history to display each booking -->
              <c:forEach var="booking" items="${bookingHistory}">
                <tr>
                  <td>${booking.bookingId}</td>
                  <td>${booking.bookingDate}</td>
                  <td>${booking.room.roomNumber}</td>
                  <td>${booking.room.category.categoryName}</td>
                  <td>${booking.checkInDate}</td>
                  <td>${booking.checkOutDate}</td>
                  <td>${booking.totalPrice}</td>
                  <td>
                    <!-- Display booking status with different colors -->
                    <c:choose>
                      <c:when test="${booking.bookingStatus eq 'Not Yet'}">
                        <span style="color: black; font-weight: bold;">${booking.bookingStatus}</span>
                      </c:when>
                      <c:when test="${booking.bookingStatus eq 'Done'}">
                        <span style="color: green; font-weight: bold;">${booking.bookingStatus}</span>
                      </c:when>
                      <c:when test="${booking.bookingStatus eq 'Cancel'}">
                        <span style="color: red; font-weight: bold;">${booking.bookingStatus}</span>
                      </c:when>
                      <c:otherwise>
                        <span style="color: yellowgreen; font-weight: bold;">${booking.bookingStatus}</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>${booking.note}</td>
                  <td class="d-flex justify-content-center align-items-center" style="height: 100px;">
                    <!-- Display action buttons based on booking status -->
                    <c:choose>
                      <c:when test="${booking.bookingStatus eq 'On Going'}">
                        <button class="btn btn-primary">Book Service</button>
                      </c:when>
                      <c:when test="${booking.bookingStatus eq 'Cancel'}">
                        <button class="btn btn-danger">Canceled</button>
                      </c:when>
                      <c:when test="${booking.bookingStatus eq 'Done'}">
                        <button class="btn btn-warning feedback-btn" data-booking-id="${booking.bookingId}" data-toggle="modal" data-target="#ratingModal">FeedBack</button>
                      </c:when>
                      <c:when test="${booking.bookingStatus eq 'Not Yet'}">
                        <button class="btn btn-danger cancel-btn" data-booking-id="${booking.bookingId}">Cancel</button>
                      </c:when>
                    </c:choose>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>

      <!-- Modal for rating and feedback -->
      <div class="modal fade" id="ratingModal" tabindex="-1" role="dialog" aria-labelledby="ratingModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="ratingModalLabel">Feedback:</h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- Star rating section -->
              <div class="stars">
                <i class="fa-solid fa-star" data-value="1"></i>
                <i class="fa-solid fa-star" data-value="2"></i>
                <i class="fa-solid fa-star" data-value="3"></i>
                <i class="fa-solid fa-star" data-value="4"></i>
                <i class="fa-solid fa-star" data-value="5"></i>
              </div>
              <p>Selected rating: <span id="selectedRating">0</span> stars</p>
              <!-- Feedback textarea -->
              <div>
                <label for="feedbackText">Enter Your Feedback:</label>
                <textarea class="feedback-input" id="feedbackText" placeholder="Write your feedback here..."></textarea>
              </div>
              <!-- Hidden input to store feedback ID -->
              <input type="hidden" id="feedbackId" value="0">
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary" id="submitRating">Submit</button>
            </div>
          </div>
        </div>
      </div>

      <!-- Popup for confirmation messages -->
      <div class="popup" id="ratingPopup">
        <p id="popupMessage">Thanks for feedback!</p>
        <button id="closePopup">OK</button>
      </div>

      <!-- JavaScript for handling table, modal, and feedback submission -->
      <script>
        $(document).ready(function () {
          // Handle cancel button click
          $(".cancel-btn").click(function () {
            var bookingId = $(this).data("booking-id");
            if (confirm("Are you sure you want to cancel this booking?")) {
              $.ajax({
                type: "POST",
                url: "ChangeStateCancel",
                data: { bookingId: bookingId },
                success: function (response) {
                  alert("Booking cancelled successfully!");
                  location.reload();
                },
                error: function () {
                  alert("Error cancelling booking. Please try again.");
                }
              });
            }
          });

          // Initialize DataTable for the booking history table
          $('#historytable').DataTable({
            "order": [[0, "asc"]], // Sort by Booking ID in ascending order
            "paging": true,
            "searching": true,
            "info": true,
            "responsive": true
          });

          // Variables to store current rating, booking ID, and feedback ID
          let currentRating = 0;
          let currentBookingId = 0;
          let currentFeedbackId = 0;

          // Handle feedback button click to open modal
          $(".feedback-btn").click(function () {
            currentBookingId = $(this).data("booking-id");
            currentRating = 0;
            currentFeedbackId = 0;
            $("#selectedRating").text(currentRating);
            $("#feedbackText").val("");
            $("#feedbackId").val("0");
            $(".stars i").removeClass("active");

            // Check if feedback already exists for this booking
            $.ajax({
              type: "GET",
              url: "CheckFeedback",
              data: { bookingId: currentBookingId },
              success: function (response) {
                if (response.feedbackExists) {
                  // If feedback exists, populate the modal with previous data
                  currentRating = response.rating;
                  currentFeedbackId = response.feedbackId;
                  $("#selectedRating").text(currentRating);
                  $("#feedbackText").val(response.comment);
                  $("#feedbackId").val(currentFeedbackId);
                  $(".stars i").each(function () {
                    $(this).removeClass("active");
                    if ($(this).data("value") <= currentRating) {
                      $(this).addClass("active");
                    }
                  });
                }
              },
              error: function () {
                console.log("Error checking feedback.");
              }
            });
          });

          // Variables for DOM elements
          const stars = $(".stars i");
          const selectedRating = $("#selectedRating");
          const feedbackText = $("#feedbackText");
          const feedbackIdInput = $("#feedbackId");
          const submitButton = $("#submitRating");
          const popup = $("#ratingPopup");
          const popupMessage = $("#popupMessage");
          const closePopupButton = $("#closePopup");

          // Function to update the displayed rating
          function updateRatingDisplay() {
            selectedRating.text(currentRating);
          }

          // Handle star rating interactions
          stars.each(function () {
            // On mouseover, highlight stars up to the hovered star
            $(this).on("mouseover", function () {
              const hoverValue = $(this).data("value");
              stars.each(function () {
                $(this).removeClass("hover");
                if ($(this).data("value") <= hoverValue) {
                  $(this).addClass("hover");
                }
              });
            });

            // On mouseout, remove hover effect
            $(this).on("mouseout", function () {
              stars.each(function () {
                $(this).removeClass("hover");
              });
            });

            // On click, set the rating and highlight selected stars
            $(this).on("click", function () {
              currentRating = parseInt($(this).data("value"));
              updateRatingDisplay();
              stars.each(function () {
                $(this).removeClass("active");
                if ($(this).data("value") <= currentRating) {
                  $(this).addClass("active");
                }
              });
            });
          });

          // Handle submit button click to send feedback
          submitButton.on("click", function () {
            const feedback = feedbackText.val().trim();
            const feedbackId = parseInt(feedbackIdInput.val());
            if (currentRating > 0) {
              // Determine if this is a new feedback (GET) or an update (POST)
              const requestType = feedbackId > 0 ? "POST" : "GET";
              const data = feedbackId > 0 
                ? { feedbackId: feedbackId, rating: currentRating, feedback: feedback }
                : { bookingId: currentBookingId, rating: currentRating, feedback: feedback };

              $.ajax({
                type: requestType,
                url: "FeedbackControl",
                data: data,
                success: function (response) {
                  $("#ratingModal").modal("hide");
                  popupMessage.text("Thanks for feedback!");
                  popup.css("display", "block");
                },
                error: function () {
                  popupMessage.text("Error submitting feedback. Please try again.");
                  popup.css("display", "block");
                }
              });
            } else {
              popupMessage.text("Please select a rating before submitting.");
              popup.css("display", "block");
            }
          });

          // Handle close popup button click
          closePopupButton.on("click", function () {
            popup.css("display", "none");
            location.reload();
          });
        });
      </script>
    </div>

    <!-- Footer section -->
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
            <p>
              Copyright ©<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart color-danger" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
            </p>
          </div>
        </div>
      </div>
    </footer>

    <!-- Include additional JavaScript libraries -->
    <script src="js/jquery-migrate-3.0.1.min.js"></script>
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.easing.1.3.js"></script>
    <script src="js/jquery.waypoints.min.js"></script>
    <script src="js/owl.carousel.min.js"></script>
    <script src="js/jquery.magnific-popup.min.js"></script>
    <script src="js/aos.js"></script>
    <script src="js/jquery.animateNumber.min.js"></script>
    <script src="js/bootstrap-datepicker.js"></script>
    <script src="js/jquery.timepicker.min.js"></script>
    <script src="js/scrollax.min.js"></script>
    <script src="js/main.js"></script>
  </body>
</html>