/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener("DOMContentLoaded", function () {
    const dt = new simpleDatatables.DataTable(".room-list");

    let statusFilter = "all";
    let typeFilter = "";

    function updateRowNumbers() {
        const rows = document.querySelectorAll(".room-list tbody tr");
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

    function applyRoomFilters() {
        const noRow = document.querySelector(".room-list tbody tr td[colspan]");
        if (noRow)
            noRow.parentElement.remove();

        const rows = document.querySelectorAll(".room-list tbody tr");
        let validFound = false;
        rows.forEach(row => {
            const firstCell = row.cells[0];
            if (firstCell && firstCell.hasAttribute("colspan")) {
                row.style.display = "none";
                return;
            }
            const rowType = row.cells[2].textContent.trim().toLowerCase();
            const rowStatus = row.cells[3].textContent.trim().toLowerCase();

            const statusOk = (statusFilter === "all" || rowStatus === statusFilter);
            const typeOk = (typeFilter === "" || rowType === typeFilter.toLowerCase());

            if (statusOk && typeOk) {
                row.style.display = "";
                validFound = true;
            } else {
                row.style.display = "none";
            }
        });
        if (!validFound) {
            const tbody = document.querySelector(".room-list tbody");
            const noResultRow = document.createElement("tr");
            const td = document.createElement("td");
            td.setAttribute("colspan", "7");
            td.style.textAlign = "center";
            td.textContent = "No matching records found.";
            noResultRow.appendChild(td);
            tbody.appendChild(noResultRow);
        }
        updateRowNumbers();
    }

    const statusFilters = document.querySelectorAll(".nav-contacts li");
    statusFilters.forEach(li => {
        li.addEventListener("click", function () {
            statusFilter = this.getAttribute("data-filter").toLowerCase();
            statusFilters.forEach(item => item.classList.remove("active"));
            this.classList.add("active");
            applyRoomFilters();
        });
    });

    const typeDropdown = document.getElementById("filterRoomType");
    if (typeDropdown) {
        typeDropdown.addEventListener("change", function () {
            typeFilter = this.value.trim();
            applyRoomFilters();
        });
    }

    applyRoomFilters();

    var detailButtons = document.querySelectorAll('.btn-edit');
    detailButtons.forEach(function (btn) {
        btn.addEventListener('click', function () {
            var roomId = btn.getAttribute('data-roomId');
            var roomNumber = btn.getAttribute('data-roomNumber');
            var roomType = btn.getAttribute('data-roomType');
            var status = btn.getAttribute('data-status');

            document.getElementById('displayRoomNumber').textContent = roomNumber;
            document.getElementById('editRoomNumber').value = roomNumber;
            document.getElementById('editRoomType').value = roomType;
            document.getElementById('editStatus').value = status;
        });
    });
});

