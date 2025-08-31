/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
$(document).ready(function () {
    // Tạo hàng filter chứa dropdown
    var table = $('#bookingTable').DataTable({
        lengthMenu: [[5, 10, 25, -1], [5, 10, 25, "All"]],
        initComplete: function () {
            // Tạo dropdown filter trong bảng giả
            var $filterRow = $('.fake-filter-table .filter-row');
            const columnsToFilter = [1, 2, 5];
            var $originalHeader = $('#bookingTable thead tr:first');

            $originalHeader.find('th').each(function (index) {
                const $filterCell = $('<th>').appendTo($filterRow);

                // Chỉ thêm tên và filter cho các cột được chỉ định
                if (columnsToFilter.includes(index)) {
                    const colName = $(this).text().trim();
                    $filterCell.html(`
                <div class="column-name">${colName}</div>
                <div class="filter-container"></div>
            `);
                } else {
                    $filterCell.html('<div class="empty-header"></div>'); // Giữ nguyên layout
                }
            });

            // Khởi tạo dropdown chỉ cho các cột được filter
            this.api().columns(columnsToFilter).every(function () {
                const column = this;
                const colIdx = column.index();
                const $filterContainer = $filterRow.find('th').eq(colIdx).find('.filter-container');

                // Tạo dropdown
                const $select = $('<select>')
                        .addClass('form-control')
                        .append($('<option>').val('').text('All'))
                        .appendTo($filterContainer)
                        .on('change', function () {
                            column.search(this.value).draw();
                        });

                // Điền dữ liệu
                column.data().unique().sort().each(function (d) {
                    $select.append($('<option>').text(d).val(d));
                });
            });

            // Đồng bộ kích thước
            function syncColumnWidths() {
                $('#bookingTable thead th').each(function (idx) {
                    const width = $(this).width();
                    $filterRow.find('th').eq(idx).width(width);
                });
            }

            syncColumnWidths();
            $(window).resize(syncColumnWidths);
        }
    });

    // Đồng bộ scroll ngang
    $('#bookingTable').on('scroll', function () {
        $('.fake-thead-filter').scrollLeft($(this).scrollLeft());
    });
});

function confirmAction(form, event) {
    event.preventDefault();
    const submitter = event.submitter;
    const action = submitter.value;
    let message = "";
    if (action === "accept") {
        message = "Are you sure you want to accept this reservation?";
    } else if (action === "cancel") {
        message = "Are you sure you want to cancel this reservation?";
    }
    if (confirm(message)) {
        form.submit();
    }
}

document.addEventListener("DOMContentLoaded", function () {
    var detailButtons = document.querySelectorAll('.btn-detail');
    detailButtons.forEach(function (btn) {
        btn.addEventListener('click', function () {
            var bookingId = btn.getAttribute('data-bookingid');
            var roomNumber = btn.getAttribute('data-roomnumber');
            var roomType = btn.getAttribute('data-roomtype');
            var bookingDate = btn.getAttribute('data-bookingdate');
            var checkInDate = btn.getAttribute('data-checkindate');
            var checkOutDate = btn.getAttribute('data-checkoutdate');
            var totalPrice = btn.getAttribute('data-totalprice');
            var bookingStatus = btn.getAttribute('data-bookingstatus');
            var note = btn.getAttribute('data-note');
            var accountFullName = btn.getAttribute('data-accountfullname');
            var pricePerNight = btn.getAttribute('data-pricePerNight');

            document.getElementById('bookingId').textContent = bookingId;
            document.getElementById('roomNumber').textContent = roomNumber;
            document.getElementById('roomType').textContent = roomType;
            document.getElementById('bookingDate').textContent = bookingDate;
            document.getElementById('checkInDate').textContent = checkInDate;
            document.getElementById('checkOutDate').textContent = checkOutDate;
            document.getElementById('totalPrice').textContent = totalPrice;
            document.getElementById('bookingStatus').textContent = bookingStatus;
            document.getElementById('note').textContent = note;
            document.getElementById('fullName').textContent = accountFullName;
            document.getElementById('pricePerNight').textContent = pricePerNight;
        });
    });
});
