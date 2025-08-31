<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Profile - Deluxe Hotel</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700" rel="stylesheet">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css">
        
        <style>
            .profile-container {
                background: #fff;
                border-radius: 10px;
                padding: 30px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                max-width: 600px;
                margin: auto;
            }
            .profile-title {
                font-size: 24px;
                font-weight: 600;
                text-align: center;
                margin-bottom: 20px;
            }
            .form-group label {
                font-weight: 500;
                margin-bottom: 5px;
            }
            .profile-button {
                width: 100%;
                padding: 10px;
                border-radius: 5px;
                font-size: 16px;
                font-weight: bold;
                background-color: #007bff;
                color: white;
                border: none;
                cursor: pointer;
                margin-top: 15px;
            }
            .profile-button:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <%@include file="includes/navbar.jsp" %>

        <div class="hero-wrap" style="background-image: url('images/bg_1.jpg');">
      <div class="overlay"></div>
      <div class="container">
        <div class="row no-gutters slider-text d-flex align-itemd-end justify-content-center">
          <div class="col-md-9 ftco-animate text-center d-flex align-items-end justify-content-center">
          	<div class="text">
	            <p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home</a></span> <span>Contact</span></p>
	            <h1 class="mb-4 bread">User Information</h1>
            </div>
          </div>
        </div>
      </div>
    </div>

        <section class="ftco-section bg-light">
            <div class="container">
                <div class="profile-container">
                    <h2 class="profile-title">Profile Settings</h2>
                    <form action="ProfileControl" method="POST">
                        
                        <div class="form-group">
                            <label>Your Account ID: ${account.accountId}</label>
                        </div>
                        
                        <div class="form-group">
                            <label>Your Role: ${account.role.roleName}</label>
                        </div>
                        
                        <div class="form-group">
                            <label>Full Name:</label>
                            <input type="text" class="form-control" name="fullName" value="${account.fullName}" required>
                        </div>
                        <div class="form-group">
                            <label>Phone Number:</label>
                            <input type="text" class="form-control" name="phone" value="${account.phone}" required>
                        </div>
                        <div class="form-group">
                            <label>Email:</label>
                            <input type="email" class="form-control" name="email" value="${account.email}" readonly>
                        </div>
                        <div class="form-group">
                            <label>Address:</label>
                            <input type="text" class="form-control" name="address" value="${account.address}" required>
                        </div>
                        <div class="form-group">
                            <label name="createdDate">Your Created Date: ${account.createdDate}</label>
                        </div>
                        <button type="submit" class="profile-button">Save Profile</button>
                        <div class="text-center">
    <a href="changePassword.jsp" class="profile-button" style="background-color: #28a745; display: inline-block; width: auto; padding: 10px 20px;">Change Password</a>
</div>
                    </form>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger mt-3">${error}</div>
                    </c:if>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success mt-3">${success}</div>
                    </c:if>
                </div>
            </div>
        </section>

        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
