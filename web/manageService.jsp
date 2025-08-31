<%-- 
    Document   : manageService
    Created on : Mar 9, 2025, 11:18:38 AM
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Manage Service</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/styleManageService.css" rel="stylesheet">
        <script>
            function showAlert(message) {
                alert(message);
            }

            <% String alert = (String) session.getAttribute("alert");
       if (alert != null && !alert.isEmpty()) { %>
            showAlert('<%= alert %>');
            <% session.removeAttribute("alert"); // Clear the alert after displaying it %>
            <% } %>
        </script>

    </head>
    <body>
        <%@include file="includes/sidebar.jsp" %>

        <div class="container">
            <div class="col-md-12">
                <div class="page-people-directory">
                    <div class="row">

                        <div class="col-md-9">
                            <div class="well">
                                <div class="row">
                                    <div class="col-md-9">
                                        <form action="manageService" method="get">
                                            <div class="input-group">
                                                <input style="font-size: 20px; width: 362.775px; transform: translate(38.4px, 0px);" type="text" placeholder="Search service" class="form-control" name="keyword" value="${keyword}">
                                                <select class="form-control" name="status" style="width: 163.987px; transform: translate(37.2px, 0px);">
                                                    <option value="">All</option>
                                                    <option value="Available" ${statusFilter eq 'Available' ? 'selected' : ''}>Available</option>
                                                    <option value="Unavailable" ${statusFilter eq 'Unavailable' ? 'selected' : ''}>Unavailable</option>
                                                </select>
                                                <span class="input-group-btn">
                                                    <button class="btn btn-default" type="submit" style="font-size: 20px; width: 158.025px; height: 52.4625px; transform: translate(37.2px, 0px);">Search</button>
                                                </span>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-md-6">
                                    <h3 id="tittle">All Services</h3>
                                </div>
                                <div class="col-md-6">
                                    <button style="font-size: 20px; width: 231.244px; transform: translate(153.6px, 0px);" type="button" class="btn btn-green btn-raised btn-add-new-contact">
                                        <span class="icon-plus" data-toggle="modal"
                                              data-target="#modalAddNewService"> Add New Service</span></button>
                                </div>
                            </div>

                            <div class="list-group">
                                <c:forEach var="service" items="${serviceList}">
                                    <form action="manageService" method="post">
                                        <div class="list-group-item" data-status="${service.status.toLowerCase()}">
                                            <input type="hidden" name="serviceId" value="${service.serviceId}">
                                            <h5 style="Strong">${service.serviceName}</h5>
                                            <p><b>Description:</b> ${service.description}</p>
                                            <p><b>Price:</b> ${service.price}</p>
                                            <p><b>Status:</b> ${service.status}</p>
                                            <p><b>total Services Booked:</b> ${service.quantity}</p>
                                            <p><b>Revenue:</b> ${service.totalRevenue}</p>
                                            <button style=" font-size: 20px ;width: 93.6375px; height: 50.475px; transform: translate(-7.19995px, -1.2px);"
                                                    type="button" 
                                                    data-serviceId="${service.serviceId}"
                                                    data-serviceName="${service.serviceName}"
                                                    data-description="${service.description}"
                                                    data-price="${service.price}"
                                                    data-status="${service.status}"
                                                    data-toggle="modal" 
                                                    data-target="#modalUpdateService" 
                                                    class="btn btn-primary btn-sm btn-edit">Edit</button>
                                            <button style="font-size: 20px; width: 93.6375px; height: 50.475px; transform: translate(-7.19995px, -1.2px);"
                                                    type="submit" 
                                                    name="submit" 
                                                    value="delete" 
                                                    class="btn btn-danger btn-sm"
                                                    onclick="return confirmRemove()">Remove</button>

                                        </div>
                                    </form>
                                </c:forEach>
                            </div>

                            <div class="pull-right">
                                <ul class="pagination pagination-split pagination-sm pagination-contact">
                                    <ul class="pagination pagination-split pagination-sm pagination-contact">
                                        <c:if test="${currentPage > 1}">
                                            <li>
                                                <a href="?page=${currentPage - 1}&keyword=${keyword}&status=${statusFilter}">
                                                    <i class="fa fa-angle-left"></i>
                                                </a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <li class="${i == currentPage ? 'active' : ''}">
                                                <a href="?page=${i}&keyword=${keyword}&status=${statusFilter}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <c:if test="${currentPage < totalPages}">
                                            <li>
                                                <a href="?page=${currentPage + 1}&keyword=${keyword}&status=${statusFilter}">
                                                    <i class="fa fa-angle-right"></i>
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </ul>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <h3>Top 4 Services</h3>
                            <ul class="list-group">
                                <c:forEach var="service" items="${top4Services}">
                                    <li class="list-group-item">
                                        <h5>${service.serviceName}</h5>
                                        <p>Total Quantity: ${service.quantity}</p>
                                        <p>Total Revenue: ${service.totalRevenue}</p>
                                        <p>Status: ${service.status}</p>
                                    </li>
                                </c:forEach>
                            </ul>
                            <br>

                        </div>
                    </div>

                    <!-- addnewService modal -->
                    <div class="modal modal-pull-right" data-easein="fadeInRight" data-easeout="fadeOutRight" id="modalAddNewService" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content animated fadeInRight">
                                <div class="modal-body">
                                    <div class="row modal-close">
                                        <div class="col-md-12 padding-bottom-8 padding-top-8 bg-silver">
                                            <h4 class="pull-left"><b>Create New Service</b></h4>
                                            <ul class="list-unstyled list-inline text-right">
                                                <li class="close-right-modal"><span class="fa fa-times fa-2x" data-dismiss="modal"></span></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="contact-add-content">
                                                <form class="form-horizontal tabular-form margin-top-10" action="manageService" method="post">
                                                    <div class="form-group">
                                                        <label for="serviceName" class="col-sm-2 control-label">Name</label>
                                                        <div class="col-sm-10 tabular-border">
                                                            <input type="text" class="form-control" id="serviceName" name="serviceName" placeholder="Service Name">
                                                            <label id="serviceName_error" for="serviceName" style="color: red"></label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label  for="description" class="col-sm-2 control-label" style="width: 90.6px; transform: translate(-6px, 0px);">Description</label>
                                                        <div class="col-sm-10 tabular-border">
                                                            <textarea class="form-control" id="description" name="description" placeholder="Description" ></textarea>
                                                            <label id="description_error" for="description" style="color: red"></label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="price" class="col-sm-2 control-label">Price</label>
                                                        <div class="col-sm-10 tabular-border">
                                                            <input type="number" step="0.01" class="form-control" id="price" name="price" placeholder="Price">
                                                            <label id="price_error" for="price" style="color: red"></label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="status" class="col-sm-2 control-label">Status</label>
                                                        <div class="col-sm-10 tabular-border">
                                                            <select class="form-control" id="status" name="status" >
                                                                <option value="Available">Available</option>
                                                                <option value="Unavailable">Unavailable</option>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-actions">
                                                        <button style="width: 118.463px; height: 64.4812px; font-size: 15px" class="btn btn-silver btn-flat" data-dismiss="modal">Cancel</button> 
                                                        <button style="width: 118.463px; height: 64.4812px; font-size: 15px" type="submit" class="btn btn-green btn-flat" value="add" name="submit">Add Service</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--End of createService-->
                    <div class="modal modal-pull-right" data-easein="fadeInRight" data-easeout="fadeOutRight" id="modalUpdateService" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content animated fadeInRight">
                                <div class="modal-body">
                                    <div class="row modal-close">
                                        <div class="col-md-12 padding-bottom-8 padding-top-8 bg-silver">
                                            <h4 class="pull-left"><b>Update Service</b></h4>
                                            <ul class="list-unstyled list-inline text-right">
                                                <li class="close-right-modal"><span class="fa fa-times fa-2x" data-dismiss="modal"></span></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="contact-add-content">
                                                <form class="form-horizontal tabular-form margin-top-10" action="manageService" method="post">
                                                    <input type="hidden" id="editServiceId" name="serviceId"/>
                                                    <div class="form-group">
                                                        <label for="editServiceName" class="col-sm-2 control-label">Name</label>
                                                        <div class="col-sm-10 tabular-border">
                                                            <input type="text" class="form-control" id="editServiceName" name="serviceName" placeholder="Service Name">
                                                            <label id="serviceName_error" for="serviceName" style="color: red"></label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="editServiceDescription" class="col-sm-2 control-label" style="width: 90.6px; transform: translate(-6px, 0px);">Description</label>
                                                        <div class="col-sm-10 tabular-border">
                                                            <textarea class="form-control" id="editServiceDescription" name="description" placeholder="Description"></textarea>
                                                            <label id="description_error" for="description" style="color: red"></label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="editServicePrice" class="col-sm-2 control-label">Price</label>
                                                        <div class="col-sm-10 tabular-border">
                                                            <input type="number" step="0.01" class="form-control" id="editServicePrice" name="price" placeholder="Price" readonly>
                                                            <a>locked</a>
                                                            <label id="price_error" for="price" style="color: red"></label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="editStatus" class="col-sm-2 control-label">Status</label>
                                                        <div class="col-sm-10 tabular-border">
                                                            <select class="form-control" id="editStatus" name="status">
                                                                <option value="Available">Available</option>
                                                                <option value="Unavailable">Unavailable</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-actions">
                                                        <button style="width: 118.463px; height: 64.4812px; font-size: 12px"  class="btn btn-silver btn-flat" data-dismiss="modal">Cancel</button> 
                                                        <button style="width: 118.463px; height: 64.4812px; font-size: 12px"  type="submit" class="btn btn-green btn-flat" value="update-service" name="submit">Update Service</button>
                                                    </div>
                                                </form>
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
        <script src="${pageContext.request.contextPath}/js/manageServiceScript.js">
        </script>

        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script>
                                                        function confirmRemove() {
                                                            return confirm("Are you sure you want to remove this service?");
                                                        }
        </script>



    </body>
</html>
