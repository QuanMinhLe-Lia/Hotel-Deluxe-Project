<%-- 
    Document   : manageUser
    Created on : Feb 16, 2025, 1:00:12 AM
    Author     : Overlordil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Manage User</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css">
        <link href="${pageContext.request.contextPath}/css/styleManageUser.css" rel="stylesheet">
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
        <hr>
        <div class="container-fluid bootstrap snippets bootdey">
            <div class="row">

                <div class="col-md-2">
                    <%@ include file="includes/sidebar.jsp" %>
                </div>

                <div class="col-lg-7">
                    <div class="main-box no-header clearfix">
                        <div class="main-box-body clearfix">
                            <div class="table-responsive">
                                <table class="table user-list">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th><span>User</span></th>
                                            <th><span>Created</span></th>
                                            <th class="text-center"><span>Status</span></th>
                                            <th><span>Email</span></th>
                                            <th>Role</th>
                                            <th>
                                                <button type="button" class="btn btn-warning btn-add-user" data-toggle="modal" data-target="#modal-add-user">
                                                    <span>Add New User</span>
                                                </button>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="user" items="${userList}" varStatus="status">
                                            <tr data-role="${fn:toLowerCase(user.role.roleName)}">
                                                <td>${status.index + 1}</td>
                                                <td><div class="user-info">
                                                        <img src="${empty user.image ? 'images/unknown.jpg' : user.image}" alt="Avatar">
                                                        <div class="user-details">
                                                            <a href="#" class="user-link">${user.fullName}</a>
                                                            <span class="user-subhead">${user.role.roleName}</span>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>${user.createdDate}</td>
                                                <td class="text-center">
                                                    <c:choose>
                                                        <c:when test="${user.status.trim() == 'Active'}">
                                                            <span class="label label-success">${user.status.trim()}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-danger">${user.status.trim()}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <a href="#"><span class="__cf_email__">${user.email}</span></a>
                                                </td>
                                                <td>${user.role.roleName}</td>
                                                <td style="width: 20%;">
                                                    <c:choose>
                                                        <c:when test="${user.role.roleName eq 'Owner'}">
                                                            <button type="button" class="table-link text-info btn-view" data-toggle="modal" data-target="#modal-edit-user" data-tooltip="View"
                                                                    data-accountId="${user.accountId}"
                                                                    data-fullname="${user.fullName}" 
                                                                    data-email="${user.email}"
                                                                    data-phone="${user.phone}" 
                                                                    data-address="${user.address}" 
                                                                    data-createdDate="${user.createdDate}"
                                                                    data-status="${user.status}"
                                                                    data-role-id="${user.role.roleId}"
                                                                    data-role-name="${user.role.roleName}"
                                                                    data-readonly="true">
                                                                <span class="fa-stack">
                                                                    <i class="fas fa-eye"></i>
                                                                </span>
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="button" class="table-link text-info btn-edit" data-toggle="modal" data-target="#modal-edit-user" data-tooltip="Edit"
                                                                    data-accountId="${user.accountId}"
                                                                    data-fullname="${user.fullName}" 
                                                                    data-email="${user.email}"
                                                                    data-phone="${user.phone}" 
                                                                    data-address="${user.address}" 
                                                                    data-createdDate="${user.createdDate}"
                                                                    data-status="${user.status}"
                                                                    data-role-id="${user.role.roleId}"
                                                                    data-role-name="${user.role.roleName}">
                                                                <span class="fa-stack">
                                                                    <i class="fas fa-pencil-alt"></i>
                                                                </span>
                                                            </button>

                                                            <c:choose>
                                                                <c:when test="${user.status.trim() == 'Active'}">
                                                                    <form action="manageUser" method="post" style="display:inline;">
                                                                        <input type="hidden" name="accountId" value="${user.accountId}" />
                                                                        <input type="hidden" name="status" value="Inactive" />
                                                                        <button type="submit" class="table-link btn btn-danger" data-tooltip="Ban" name="submit" value="ban" onclick="return confirm('Are you sure you want to ban this user?');">
                                                                            <span class="fa-stack">
                                                                                <i class="fas fa-trash-alt"></i>
                                                                            </span>
                                                                        </button>
                                                                    </form>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <form action="manageUser" method="post" style="display:inline;">
                                                                        <input type="hidden" name="accountId" value="${user.accountId}" />
                                                                        <input type="hidden" name="status" value="Active" />
                                                                        <button type="submit" class="table-link btn btn-success" data-tooltip="Active" name="submit" value="ban" onclick="return confirm('Are you sure you want to activate this user?');">
                                                                            <span class="fa-stack">
                                                                                <i class="fas fa-check-circle"></i>
                                                                            </span>
                                                                        </button>
                                                                    </form>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:otherwise>
                                                    </c:choose>
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
                    <!--Lọc role-->
                    <h5 class="page-title"><b>Choose Role</b></h5>
                    <ul class="nav nav-pills nav-stacked nav-contacts role">
                        <li class="active" data-filter="all"><a>All</a></li>
                        <li data-filter="owner"><a>Owner</a></li>
                        <li data-filter="staff"><a>Staff</a></li>
                        <li data-filter="customer"><a>Customer</a></li>
                    </ul>
                    <!-- Lọc Created Date -->
                    <div class="form-group" style="margin-top: 20px;">
                        <label for="filterCreatedDate">Created Date</label>
                        <select id="filterCreatedDate" class="form-control">
                            <option value="">All</option>
                            <c:forEach var="created" items="${createdList}">
                                <option value="${created}">${created}</option>
                            </c:forEach>
                        </select>
                    </div>

                </div>
            </div>
        </div>

        <!--Modal Edit User-->
        <div class="modal modal-pull-right" data-easein="fadeInRight" data-easeout="fadeOutRight" id="modal-edit-user" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
            <div class="modal-dialog">
                <div class="modal-content animated fadeInRight">
                    <div class="modal-body">
                        <div class="row modal-close">
                            <div class="col-md-12 padding-bottom-8 padding-top-8 bg-silver">
                                <h4 class="pull-left" id="modalTitle"><b>Edit User</b></h4>
                                <ul class="list-unstyled list-inline text-right">
                                    <li class="close-right-modal"><span class="fa fa-times fa-2x" data-dismiss="modal"></span></li>
                                </ul>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="contact-add-content">
                                    <form class="form-horizontal tabular-form margin-top-10" action="manageUser" method="post">
                                        <input type="hidden" name="action" value="edit">
                                        <div class="form-group">
                                            <label for="editFullName" class="col-sm-2 control-label">Name</label>
                                            <div class="col-sm-10 tabular-border">
                                                <div style="display: flex; align-items: center;">
                                                    <input type="text" class="form-control" id="editFullName" name="fullName" placeholder="Full Name" style="flex: 1;">
                                                    <small id="editStatusDisplay" class="status" style="margin-left: 10px;"></small>
                                                </div>
                                                <label id="editFullName_error" for="editFullName" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="editEmail" class="col-sm-2 control-label">Email</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="text" class="form-control" id="editEmail" name="email" placeholder="Email" readonly>
                                                <label for="editEmail" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="editPhone" class="col-sm-2 control-label">Phone</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="text" class="form-control" id="editPhone" name="phone" placeholder="Phone">
                                                <label id="editPhone_error" for="editPhone" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="editAddress" class="col-sm-2 control-label">Address</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="text" class="form-control" id="editAddress" name="address" placeholder="Address">
                                                <label id="editAddress_error" for="editAddress" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="editRoleName" class="col-sm-2 control-label">Role</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="text" class="form-control" id="editRoleName" readonly>
                                                <input type="hidden" id="editRoleId" name="roleid">
                                            </div>
                                            <label for="editRoleName" style="color: red"></label>
                                        </div>
                                        <div class="form-group">
                                            <label for="editCreatedDate" class="col-sm-2 control-label">CreatedDate</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="date" class="form-control" id="editCreatedDate" name="createdDate" readonly>
                                            </div>
                                        </div>
                                        <div class="form-actions" style="display: flex; align-items: center;">
                                            <div style="display: flex; gap: 10px;">
                                                <button type="button" class="btn btn-ban-edit" id="editBanButton"></button>
                                                <button type="submit" class="btn btn-green btn-flat" id="editSaveButton" style="background-color: blue; color: white;" name="submit" value="update">Save Changes</button>
                                            </div>
                                            <div style="flex: 1;"></div>
                                            <div>
                                                <button type="button" class="btn btn-silver btn-flat" data-dismiss="modal" style="background-color: #DC143C; color: white;">Cancel</button>
                                            </div>
                                        </div>
                                        <input type="hidden" id="editStatus" name="status">
                                        <input type="hidden" id="editAccountId" name="accountId">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--Modal Add User-->
        <div class="modal modal-pull-right" data-easein="fadeInRight" data-easeout="fadeOutRight" id="modal-add-user" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
            <div class="modal-dialog">
                <div class="modal-content animated fadeInRight">
                    <div class="modal-body">
                        <div class="row modal-close">
                            <div class="col-md-12 padding-bottom-8 padding-top-8 bg-silver">
                                <h4 class="pull-left"><b>Create New User</b></h4>
                                <ul class="list-unstyled list-inline text-right">
                                    <li class="close-right-modal"><span class="fa fa-times fa-2x" data-dismiss="modal"></span></li>
                                </ul>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="contact-add-content">
                                    <form class="form-horizontal tabular-form margin-top-10" action="manageUser" method="post">
                                        <div class="form-group">
                                            <label for="fullName" class="col-sm-2 control-label">Name</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="text" class="form-control" id="fullName" name="fullName" placeholder="Full Name">
                                                <label id="fullName_error" for="fullName" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="email" class="col-sm-2 control-label">Email</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="text" class="form-control" id="email" name="email" placeholder="Email">
                                                <label id="email_error" for="email" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="password" class="col-sm-2 control-label">Password</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                                                <label id="password_error" for="password" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="confirmPassword" class="col-sm-2 control-label">Confirm</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password">
                                                <label id="confirmPassword_error" for="confirmPassword" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="phone" class="col-sm-2 control-label">Phone</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="text" class="form-control" id="phone" name="phone" placeholder="PhoneNumber">
                                                <label id="phone_error" for="phone" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="address" class="col-sm-2 control-label">Address</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="text" class="form-control" id="address" name="address" placeholder="Address">
                                                <label id="address_error" for="address" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="role" class="col-sm-2 control-label">Role</label>
                                            <div class="col-sm-10 tabular-border">
                                                <select class="form-control" id="role" name="roleid">
                                                    <c:forEach var="role" items="${roleList}">
                                                        <option value="${role.roleId}">${role.roleName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="createdDate" class="col-sm-2 control-label">Created Date</label>
                                            <div class="col-sm-10 tabular-border">
                                                <input type="date" class="form-control" id="createdDate" name="createdDate" readonly>
                                                <label id="createdDate_error" for="createdDate" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <button class="btn btn-silver btn-flat" data-dismiss="modal" style="background-color: #DC143C; color: white">Cancel</button> 
                                            <button type="submit" class="btn btn-green btn-flat" value="add" name="submit" style="background-color: blue; color: white">Add User</button>
                                        </div>
                                    </form>
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
    <script src="${pageContext.request.contextPath}/js/manageUserScript.js"></script>
</html>