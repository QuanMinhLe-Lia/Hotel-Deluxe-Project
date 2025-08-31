<%-- 
    Document   : dashboard
    Created on : Feb 13, 2025, 1:58:23 PM
    Author     : Overlordil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Dashboard</title>

        <link href="https://netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/styleDashboard.css" rel="stylesheet">

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2"></script>
        <%!
            public String generateStars(int rating) {
                StringBuilder stars = new StringBuilder();
                for (int i = 0; i < rating; i++) {
                    stars.append("<span class='glyphicon glyphicon-star'></span>");
                }
                return stars.toString();
            }
        %>
    </head>
    <body>
        <%@include file="includes/sidebar.jsp" %>
        <div class="dashboard-content">
            <div class="container mt-4">
                <div class="row charts-row">
                    <!--Account Chart-->
                    <div class="col-md-4 account-chart-container">
                        <h2 style="font-family: sans-serif">Account Chart</h2>
                        <canvas id="accountChart" 
                                data-activeCustomer="${customer[1]}" 
                                data-inactiveCustomer="${customer[2]}" 
                                data-activeStaff="${staff[1]}" 
                                data-inactiveStaff="${staff[2]}" 
                                data-activeOwner="${owner[1]}" 
                                data-inactiveOwner="${owner[2]}"></canvas>
                        <div class="account-grid">
                            <!-- Col Total -->
                            <div class="column total">
                                <div>Total Accounts: ${totalAccount[0]}</div>
                                <div>Customers: ${customer[0]}</div>
                                <div>Staffs: ${staff[0]}</div>
                                <div>Owners: ${owner[0]}</div>
                            </div>
                            <!-- Col Active -->
                            <div class="column active">
                                <div>Active: ${totalAccount[1]}</div>
                                <div>Active: ${customer[1]}</div>
                                <div>Active: ${staff[1]}</div>
                                <div>Active: ${owner[1]}</div>
                            </div>
                            <!-- Col Inactive -->
                            <div class="column inactive">
                                <div>Inactive: ${totalAccount[2]}</div>
                                <div>Inactive: ${customer[2]}</div>
                                <div>Inactive: ${staff[2]}</div>
                                <div>Inactive: ${owner[2]}</div>
                            </div>
                        </div>
                    </div>

                    <!--Room Chart-->
                    <div class="col-md-4 room-chart-container">
                        <h2 style="font-family: sans-serif">Room Chart</h2>
                        <canvas id="roomChart" 
                                data-availableRoom="${rooms[1]}" 
                                data-unavailableRoom="${rooms[2]}" 
                                data-maintenanceRoom="${rooms[3]}"></canvas>
                        <div style="margin-top: 10px; margin-bottom: -6px; margin-left: 10px; white-space: nowrap">Total Rooms: ${rooms[0]}</div>
                        <div class="account-grid">
                            <div class="column total">
                                <div style="color: rgba(54, 198, 0, 1)">Available: ${rooms[1]}</div>
                                <div style="color: rgba(190, 0, 0, 1)">In Use: ${rooms[2]}</div>
                                <div style="color: rgba(200, 130, 70, 1)">Maintenance: ${rooms[3]}</div>
                            </div>
                        </div>
                    </div>

                    <!--Feedback Chart-->
                    <div class="col-md-4 feedback-chart-container">
                        <h2 style="font-family: sans-serif">Feedback Chart</h2>
                        <canvas id="feedbackChart" 
                                data-one="${feedbacks[1]}" 
                                data-two="${feedbacks[2]}" 
                                data-three="${feedbacks[3]}" 
                                data-four="${feedbacks[4]}" 
                                data-five="${feedbacks[5]}"></canvas>
                        <div style="margin-top: 10px; margin-bottom: -6px; margin-left: 10px; white-space: nowrap">Total Feedbacks: ${feedbacks[0]}</div>
                        <div class="account-grid">
                            <div class="column total">
                                <div><%= generateStars(1)%>: ${feedbacks[1]}</div>
                                <div><%= generateStars(2)%>: ${feedbacks[2]}</div>
                                <div><%= generateStars(3)%>: ${feedbacks[3]}</div>
                                <div><%= generateStars(4)%>: ${feedbacks[4]}</div>
                                <div><%= generateStars(5)%>: ${feedbacks[5]}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/dashboardScript.js"></script>
    </body>
</html>