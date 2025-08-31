/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

// Table script
document.addEventListener("DOMContentLoaded", function () {
    const dt = new simpleDatatables.DataTable(".user-list");

    let roleFilter = "all";
    let dateFilter = "";

    function updateRowNumbers() {
        const rows = document.querySelectorAll(".user-list tbody tr");
        let count = 1;
        rows.forEach(row => {
            const firstCell = row.cells[0];
            if (firstCell && firstCell.hasAttribute("colspan")) {
                return;
            }
            if (row.offsetParent !== null) {
                row.cells[0].textContent = count;
                count++;
            }
        });
    }

    function applyFilters() {
        const queries = [];

        if (roleFilter !== "all") {
            queries.push({
                terms: [roleFilter],
                columns: [5]
            });
        }
        if (dateFilter !== "") {
            queries.push({
                terms: [dateFilter],
                columns: [2]
            });
        }

        dt.multiSearch(queries);
        updateRowNumbers();

        if (roleFilter !== "all" && dateFilter !== "") {
            const rows = document.querySelectorAll(".user-list tbody tr");
            let validCount = 0;
            rows.forEach(row => {
                if (row.offsetParent !== null) {
                    const rowRole = row.cells[5].textContent.trim();
                    const rowDate = row.cells[2].textContent.trim();
                    if (rowRole === roleFilter && rowDate === dateFilter) {
                        validCount++;
                    }
                }
            });
            if (validCount === 0) {
                dt.search("nonexistentvalue");
                updateRowNumbers();
            }
        }
    }

    const roleFilters = document.querySelectorAll(".nav-contacts li");
    roleFilters.forEach(li => {
        li.addEventListener("click", function () {
            roleFilter = this.getAttribute("data-filter").toLowerCase();
            roleFilters.forEach(item => item.classList.remove("active"));
            this.classList.add("active");
            applyFilters();
        });
    });

    const dateDropdown = document.getElementById("filterCreatedDate");
    if (dateDropdown) {
        dateDropdown.addEventListener("change", function () {
            dateFilter = this.value.trim();
            applyFilters();
        });
    }

    applyFilters();
});

function updateStatusColors() {
    var statusLabels = document.querySelectorAll('.user-list .label');
    statusLabels.forEach(function (label) {
        var statusText = label.textContent.trim();
        if (statusText === 'Active') {
            label.classList.remove('label-default');
            label.classList.add('label-success');
        } else if (statusText === 'Inactive') {
            label.classList.remove('label-default');
            label.classList.add('label-danger');
        }
    });
}
document.addEventListener('datatable:updated', updateStatusColors);

function formatFullName(inputId) {
    var inputElement = document.getElementById(inputId);
    
    var fullName = inputElement.value.trim();
    
    fullName = fullName.toLowerCase().replace(/\b\w/g, function(char) {
        return char.toUpperCase();
    });

    inputElement.value = fullName;
}

//Modal Edit User Script
document.addEventListener("DOMContentLoaded", function () {
    var modalButtons = document.querySelectorAll('.btn-edit, .btn-view');
    modalButtons.forEach(function (btn) {
        btn.addEventListener('click', function () {
            var editBanButton = document.getElementById("editBanButton");
            var editSaveButton = document.getElementById("editSaveButton");
            var modalTitle = document.getElementById("modalTitle");

            document.getElementById('editFullName').readOnly = false;
            document.getElementById('editPhone').readOnly = false;
            document.getElementById('editAddress').readOnly = false;
            editBanButton.style.display = "inline-block";
            editSaveButton.style.display = "inline-block";

            var accountId = btn.getAttribute('data-accountId');
            var fullName = btn.getAttribute('data-fullname');
            var email = btn.getAttribute('data-email');
            var phone = btn.getAttribute('data-phone');
            var address = btn.getAttribute('data-address');
            var roleId = btn.getAttribute('data-role-id');
            var roleName = btn.getAttribute('data-role-name');
            var createdDate = btn.getAttribute('data-createdDate');
            var status = btn.getAttribute('data-status');

            status = (status && status.trim().toLowerCase() === "active") ? "Active" : "Inactive";

            document.getElementById('editAccountId').value = accountId;
            document.getElementById('editFullName').value = fullName;
            document.getElementById('editEmail').value = email;
            document.getElementById('editPhone').value = phone;
            document.getElementById('editAddress').value = address;
            document.getElementById('editRoleId').value = roleId;
            document.getElementById('editRoleName').value = roleName;
            document.getElementById('editCreatedDate').value = createdDate;
            document.getElementById('editStatus').value = status;

            var statusSpan = document.getElementById('editStatusDisplay');
            if (status === "Active") {
                statusSpan.className = "status status-active";
            } else {
                statusSpan.className = "status status-inactive";
            }
            statusSpan.innerHTML = status + " <span class='status-dot'></span>";

            if (btn.getAttribute('data-readonly') === "true") {
                document.getElementById('editFullName').readOnly = true;
                document.getElementById('editPhone').readOnly = true;
                document.getElementById('editAddress').readOnly = true;
                editBanButton.style.display = "none";
                editSaveButton.style.display = "none";
                if (modalTitle) {
                    modalTitle.textContent = "View User";
                }
            } else {
                editBanButton.style.display = "inline-block";
                editSaveButton.style.display = "inline-block";
                if (status === "Active") {
                    editBanButton.textContent = "Ban";
                    editBanButton.className = "btn btn-danger btn-ban-edit";
                } else {
                    editBanButton.textContent = "Activate";
                    editBanButton.className = "btn btn-success btn-ban-edit";
                }
                if (modalTitle) {
                    modalTitle.textContent = "Edit User";
                }
            }
            document.querySelector("#modal-edit-user input[name='action']").value =
                (btn.getAttribute('data-readonly') === "true") ? "view" : "edit";
        });
    });

    var editBanButton = document.getElementById("editBanButton");
    if (editBanButton) {
        editBanButton.addEventListener("click", function() {
            var currentStatus = document.getElementById("editStatus").value;
            var newStatus = (currentStatus === "Active") ? "Inactive" : "Active";
            document.getElementById("editStatus").value = newStatus;
            var statusSpan = document.getElementById("editStatusDisplay");
            if (newStatus === "Active") {
                statusSpan.className = "status status-active";
                this.textContent = "Ban";
                this.className = "btn btn-danger btn-ban-edit";
            } else {
                statusSpan.className = "status status-inactive";
                this.textContent = "Activate";
                this.className = "btn btn-success btn-ban-edit";
            }
            statusSpan.innerHTML = newStatus + " <span class='status-dot'></span>";
        });
    }
});

document.getElementById("editFullName").addEventListener("input", function() {
    formatFullName("editFullName");
});

function validateEditPhoneNumber() {
    var phoneNumber = document.getElementById("editPhone").value.trim();

    var phoneNumberRegex = /^(0[35789])\d{8}$/;

    if (!phoneNumberRegex.test(phoneNumber)) {
        document.getElementById("editPhone_error").innerText = "Wrong format phone number,\n Example: 0943614388\n Enter again please!";
    } else {
        document.getElementById("editPhone_error").innerText = "";
    }
}

function validateEditAddress() {
    var address = document.getElementById("editAddress").value.trim();

    var addressRegex = /^(?:[A-ZÀ-Ỹ][a-zà-ỹ]*|\d+)(?:\s[A-ZÀ-Ỹ][a-zà-ỹ]*|\s\d+)*$/;

    if (!addressRegex.test(address)) {
        document.getElementById("editAddress_error").innerText = "Capitalize the first letter of each word please!";
    } else {
        document.getElementById("editAddress_error").innerText = "";
    }
}

document.getElementById("editFullName").addEventListener("input", validateEditFullName);
document.getElementById("editPhone").addEventListener("input", validateEditPhoneNumber);
document.getElementById("editAddress").addEventListener("input", validateEditAddress);

$('#modal-edit-user').on('hidden.bs.modal', function () {
    var form = $(this).find('form')[0];
    if (form) {
        form.reset();
    }
    $(this).find('label[style="color: red"]').text('');
    $('#editStatusDisplay').html('');
});

//Modal Add User Script
document.addEventListener("DOMContentLoaded", function () {
    var createdDateInput = document.getElementById("createdDate");
    var today = new Date().toISOString().split("T")[0];
    createdDateInput.value = today;
});

document.getElementById("fullName").addEventListener("input", function() {
    formatFullName("fullName");
});

function validateEmail() {
    var email = document.getElementById("email").value.trim();

    var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (!emailRegex.test(email)) {
        document.getElementById("email_error").innerText = "Wrong format email,\n Example: example@gmail.com\n Enter again please!";
    } else {
        document.getElementById("email_error").innerText = "";
    }
}

function validatePhoneNumber() {
    var phoneNumber = document.getElementById("phone").value.trim();

    var phoneNumberRegex = /^(0[35789])\d{8}$/;

    if (!phoneNumberRegex.test(phoneNumber)) {
        document.getElementById("phone_error").innerText = "Wrong format phone number,\n Example: 0943614388\n Enter again please!";
    } else {
        document.getElementById("phone_error").innerText = "";
    }
}

function validateAddress() {
    var address = document.getElementById("address").value.trim();

    var addressRegex = /^(?:[A-ZÀ-Ỹ][a-zà-ỹ]*|\d+)(?:\s[A-ZÀ-Ỹ][a-zà-ỹ]*|\s\d+)*$/;

    if (!addressRegex.test(address)) {
        document.getElementById("address_error").innerText = "Capitalize the first letter of each word please!";
    } else {
        document.getElementById("address_error").innerText = "";
    }
}

function validatePassword() {
    var password = document.getElementById("password").value.trim();

    var passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,18}$/;

    if (!passwordRegex.test(password)) {
        document.getElementById("password_error").innerText = "Password must be 6-18 characters\nAt least 1 uppercase letter, 1 lowercase letter and 1 special character\nEnter again please!";
    } else {
        document.getElementById("password_error").innerText = "";
    }
}

function validateConfirmPass() {
    var password = document.getElementById("password").value.trim();
    var confirmPass = document.getElementById("confirmPassword").value.trim();

    if (confirmPass !== password) {
        document.getElementById("confirmPassword_error").innerText = "Password and Confirm password are different\n Enter again please!";
    } else {
        document.getElementById("confirmPassword_error").innerText = "";
    }
}

document.getElementById("fullName").addEventListener("input", validateFullName);
document.getElementById("email").addEventListener("input", validateEmail);
document.getElementById("phone").addEventListener("input", validatePhoneNumber);
document.getElementById("address").addEventListener("input", validateAddress);
document.getElementById("password").addEventListener("input", validatePassword);
document.getElementById("confirmPassword").addEventListener("input", validateConfirmPass);

$('#modal-add-user').on('hidden.bs.modal', function () {
    var form = $(this).find('form')[0];
    if (form) {
        form.reset();
    }
    $(this).find('label[style="color: red"]').text('');
});