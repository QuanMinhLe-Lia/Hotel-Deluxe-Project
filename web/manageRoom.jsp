<%-- 
    Document   : manageRoom
    Created on : Feb 18, 2025, 9:57:58 PM
    Author     : Overlordil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Manage Room</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css">
        <link href="${pageContext.request.contextPath}/css/styleManageRoom.css" rel="stylesheet">
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
        <div class="container-fluid bootstrap snippets bootdey">
            <div class="row">

                <div class="col-md-2">
                    <%@ include file="includes/sidebar.jsp" %>
                </div>

                <div class="col-lg-7">
                    <div class="main-box no-header clearfix">
                        <div class="main-box-body clearfix">
                            <div class="table-responsive">
                                <table class="table room-list">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th><span>Room Number</span></th>
                                            <th><span>Room Type</span></th>
                                            <th><span>Status</span></th>
                                            <th>
                                                <button type="button" class="btn btn-warning btn-add-room" data-toggle="modal" data-target="#modal-add-room">
                                                    <span>Add New Room</span>
                                                </button>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="room" items="${roomList}" varStatus="status">
                                            <tr>
                                                <td>${status.index + 1}</td>
                                                <td>${room.roomNumber}</td>
                                                <td>${room.category.categoryName}</td>
                                                <td>${room.status}</td>
                                                <td>
                                                    <button type="button" class="btn-edit" data-toggle="modal" data-target="#modal-edit-room"
                                                            data-roomId="${room.id}" 
                                                            data-roomNumber="${room.roomNumber}" 
                                                            data-roomType="${room.category.categoryId}" 
                                                            data-status="${room.status}">
                                                        <i class="fas fa-edit"></i> Edit
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <!-- Lọc Room Status -->
                    <div class="form-group filter-group">
                        <label for="filterCreatedDate">Choose Status</label>
                        <ul class="nav nav-pills nav-stacked nav-contacts role">
                            <li class="active" data-filter="all"><a>All</a></li>
                            <li data-filter="available"><a>Available</a></li>
                            <li data-filter="unavailable"><a>Unavailable</a></li>
                            <li data-filter="maintenance"><a>Maintenance</a></li>
                        </ul>
                    </div>
                    <!-- Lọc Room Type -->
                    <div class="form-group" style="margin-top: 20px;">
                        <label for="filterRoomType">Choose Room Type</label>
                        <select id="filterRoomType" class="form-control">
                            <option value="">All</option>
                            <c:forEach var="type" items="${roomType}">
                                <option value="${type.categoryName}">${type.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <!--Modal Add Room-->
                <div class="modal modal-pull-right" data-easein="fadeInRight" data-easeout="fadeOutRight" id="modal-add-room" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
                    <div class="modal-dialog">
                        <div class="modal-content animated fadeInRight">
                            <div class="modal-body">
                                <div class="row modal-close">
                                    <div class="col-md-12 padding-bottom-8 padding-top-8 bg-silver">
                                        <h4 class="pull-left"><b>Create New Room</b></h4>
                                        <ul class="list-unstyled list-inline text-right">
                                            <li class="close-right-modal"><span class="fa fa-times fa-2x" data-dismiss="modal"></span></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="contact-add-content">
                                            <form class="form-horizontal tabular-form margin-top-10" action="manageRoom" method="post">
                                                <div class="form-group form-group-inline">
                                                    <label for="roomNumber" class="col-sm-2 control-label ">Room Number</label>
                                                    <div class="col-sm-10 tabular-border">
                                                        <input type="text" class="form-control" id="roomNumber" name="roomNumber" placeholder="Room Number">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="categoryName" class="col-sm-2 control-label">Room Type</label>
                                                    <div class="col-sm-10 tabular-border">
                                                        <select class="form-control" id="categoryName" name="categoryName">
                                                            <c:forEach var="type" items="${roomType}">
                                                                <option value="${type.categoryId}">${type.categoryName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="status" class="col-sm-2 control-label">Status</label>
                                                    <div class="col-sm-10 tabular-border">
                                                        <select class="form-control" id="status" name="status">
                                                                <option value="Available">Available</option>
                                                                <option value="Unavailable">Unavailable</option>
                                                                <option value="Maintenance">Maintenance</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-actions">
                                                    <button class="btn btn-silver btn-flat" data-dismiss="modal" style="background-color: #DC143C; color: white">Cancel</button> 
                                                    <button type="submit" class="btn btn-green btn-flat" value="add" name="submit" style="background-color: blue; color: white">Create Room</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!--Modal Edit Room-->
                <div class="modal modal-pull-right" data-easein="fadeInRight" data-easeout="fadeOutRight" id="modal-edit-room" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
                    <div class="modal-dialog">
                        <div class="modal-content animated fadeInRight">
                            <div class="modal-body">
                                <div class="row modal-close">
                                    <div class="col-md-12 padding-bottom-8 padding-top-8 bg-silver">
                                        <h4 class="pull-left"><b>Edit Room </b> <b id="displayRoomNumber"></b></h4>
                                        <ul class="list-unstyled list-inline text-right">
                                            <li class="close-right-modal"><span class="fa fa-times fa-2x" data-dismiss="modal"></span></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="contact-add-content">
                                            <form class="form-horizontal tabular-form margin-top-10" action="manageRoom" method="post">
                                                <div class="form-group form-group-inline">
                                                    <label for="roomNumber" class="col-sm-2 control-label ">Room Number</label>
                                                    <div class="col-sm-10 tabular-border">
                                                        <input type="text" class="form-control" id="editRoomNumber" name="roomNumber" readonly>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="roomType" class="col-sm-2 control-label">Room Type</label>
                                                    <div class="col-sm-10 tabular-border">
                                                        <select class="form-control" id="editRoomType" name="categoryName">
                                                            <c:forEach var="type" items="${roomType}">
                                                                <option value="${type.categoryId}">${type.categoryName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="status" class="col-sm-2 control-label">Status</label>
                                                    <div class="col-sm-10 tabular-border">
                                                        <select class="form-control" id="editStatus" name="status">
                                                                <option value="Available">Available</option>
                                                                <option value="Unavailable">Unavailable</option>
                                                                <option value="Maintenance">Maintenance</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-actions">
                                                    <button class="btn btn-silver btn-flat" data-dismiss="modal" style="background-color: #DC143C; color: white">Cancel</button> 
                                                    <button type="submit" class="btn btn-green btn-flat" value="update" name="submit" style="background-color: blue; color: white">Save Changes</button>
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
    </body>
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" type="text/javascript"></script>
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/2.3.1/list.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/manageRoomScript.js"></script>
</html>
