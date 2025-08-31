/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    var editButtons = document.querySelectorAll('.btn-edit');
    editButtons.forEach(function (btn) {
        btn.addEventListener('click', function () {
            const serviceId = btn.getAttribute('data-serviceId');
            const serviceName = btn.getAttribute('data-serviceName');
            const description = btn.getAttribute('data-description');
            const price = btn.getAttribute('data-price');
            const status = btn.getAttribute('data-status');

            // Kiểm tra các phần tử
            console.log(serviceId, serviceName, description, price, status);
            console.log(document.getElementById('editServiceId')); // Kiểm tra phần tử

            // Set the values in the modal
            document.getElementById('editServiceId').value = serviceId;
            document.getElementById('editServiceName').value = serviceName;
            document.getElementById('editServiceDescription').value = description;
            document.getElementById('editServicePrice').value = price;
            document.getElementById('editStatus').value = status;
        });
    });
});



$(document).ready(function() {
    // Xử lý khi modal ẩn đi
    $('#modalAddNewService').on('hidden.bs.modal', function (e) {
        // Reset form và xóa thông báo lỗi
        const form = $(this).find('form');
        form[0].reset(); // Reset giá trị về mặc định
        
        // Xóa các thông báo lỗi
        form.find('.form-control').removeClass('is-invalid');
        form.find('[id$="_error"]').text(''); 
    });

    // Xử lý click nút Cancel
    $(document).on('click', '[data-dismiss="modal"]', function() {
        $('#modalAddNewService').modal('hide'); // Kích hoạt sự kiện ẩn modal
    });
});
$(document).on('keydown', function(e) {
    if (e.key === "Escape" && $('#modalAddNewService').hasClass('show')) {
        $('#modalAddNewService').modal('hide');
    }
});


document.addEventListener("DOMContentLoaded", function () {
    const filterButtons = document.querySelectorAll(".btn-group-vertical button");
    const serviceItems = document.querySelectorAll(".list-group-item");
    const title = document.getElementById("tittle");

    filterButtons.forEach(button => {
        button.addEventListener("click", function () {
            const category = this.getAttribute("data-filter").toLowerCase();

            filterButtons.forEach(btn => btn.classList.remove("active"));
            this.classList.add("active");

            serviceItems.forEach(item => {
                const serviceStatus = item.querySelector("p:last-child").textContent.split(": ")[1].toLowerCase();
                if (category === "all" || category === serviceStatus) {
                    item.style.display = "block";
                } else {
                    item.style.display = "none";
                }
            });

            if (category === "all") {
                title.textContent = "All Services";
            } else {
                title.textContent = category.charAt(0).toUpperCase() + category.slice(1) + " Services";
            }
        });
    });
});

