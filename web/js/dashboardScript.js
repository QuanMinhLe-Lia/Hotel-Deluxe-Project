/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


$(document).ready(function () {

    //Account Chart
    var canvas = document.getElementById('accountChart');

    var activeCustomer = parseInt(canvas.getAttribute('data-activeCustomer'), 10);
    var inactiveCustomer = parseInt(canvas.getAttribute('data-inactiveCustomer'), 10);
    var activeStaff = parseInt(canvas.getAttribute('data-activeStaff'), 10);
    var inactiveStaff = parseInt(canvas.getAttribute('data-inactiveStaff'), 10);
    var activeOwner = parseInt(canvas.getAttribute('data-activeOwner'), 10);
    var inactiveOwner = parseInt(canvas.getAttribute('data-inactiveOwner'), 10);

    new Chart(canvas.getContext('2d'), {
        type: 'radar',
        data: {
            labels: ['Customer', 'Staff', 'Owner'],
            datasets: [
                {
                    label: 'Active',
                    data: [activeCustomer, activeStaff, activeOwner],
                    backgroundColor: 'rgba(0, 255, 0, 0.2)',
                    borderColor: 'green',
                    borderWidth: 3
                },
                {
                    label: 'Inactive',
                    data: [inactiveCustomer, inactiveStaff, inactiveOwner],
                    backgroundColor: 'rgba(255, 0, 0, 0.2)',
                    borderColor: 'red',
                    borderWidth: 3
                }
            ]
        },
        options: {
            responsive: false,
            plugins: {
                legend: {
                    position: 'left',
                    labels: {
                        boxWidth: 35,
                        usePointStyle: true,
                        padding: 20
                    }
                },
                datalabels: {
                    color: '#000',
                    font: {
                        weight: 'bold',
                        size: 14
                    },
                    anchor: 'center',
                    align: 'center'
                }
            }
        }
    });

    //Room Chart
    var canvas = document.getElementById('roomChart');

    var available = parseInt(canvas.getAttribute('data-availableRoom'), 10);
    var unavailable = parseInt(canvas.getAttribute('data-unavailableRoom'), 10);
    var maintenance = parseInt(canvas.getAttribute('data-maintenanceRoom'), 10);

    new Chart(canvas.getContext('2d'), {
        type: 'pie',
        data: {
            labels: ['Available', 'In Use', 'Maintenance'],
            datasets: [
                {
                    label: 'Number',
                    data: [available, unavailable, maintenance],
                    backgroundColor: [
                        'rgba(54, 198, 0, 1)',
                        'rgba(190, 0, 0, 1)',
                        'rgba(200, 130, 70, 1)'
                    ],
                    borderColor: [
                        'rgba(54, 198, 0, 1)',
                        'rgba(190, 0, 0, 1)',
                        'rgba(200, 130, 70, 1)'
                    ],
                    borderWidth: 3
                }
            ]
        },
        options: {
            responsive: false,
            plugins: {
                legend: {
                    position: 'left',
                    labels: {
                        boxWidth: 35,
                        usePointStyle: true,
                        padding: 20
                    }
                },
                datalabels: {
                    color: '#000',
                    font: {
                        weight: 'bold',
                        size: 14
                    },
                    anchor: 'center',
                    align: 'center'
                }
            }
        }
    });

    // Feedback Chart
    var canvas = document.getElementById('feedbackChart');

    var one = parseInt(canvas.getAttribute('data-one'), 10);
    var two = parseInt(canvas.getAttribute('data-two'), 10);
    var three = parseInt(canvas.getAttribute('data-three'), 10);
    var four = parseInt(canvas.getAttribute('data-four'), 10);
    var five = parseInt(canvas.getAttribute('data-five'), 10);

    new Chart(canvas.getContext('2d'), {
        type: 'bar',
        data: {
            // Dùng labels để đặt tên cho từng cột
            labels: ['One', 'Two', 'Three', 'Four', 'Five'],
            datasets: [
                {
                    // Dataset label không ảnh hưởng tới legend khi dùng custom generateLabels
                    label: 'Feedback',
                    data: [one, two, three, four, five],
                    backgroundColor: [
                        'rgba(54, 198, 0, 1)',
                        'rgba(190, 0, 0, 1)',
                        'rgba(200, 130, 70, 1)',
                        'rgba(0, 123, 255, 1)',
                        'rgba(255, 193, 7, 1)'
                    ],
                    borderColor: [
                        'rgba(54, 198, 0, 1)',
                        'rgba(190, 0, 0, 1)',
                        'rgba(200, 130, 70, 1)',
                        'rgba(0, 123, 255, 1)',
                        'rgba(255, 193, 7, 1)'
                    ],
                    borderWidth: 3
                }
            ]
        },
        options: {
            responsive: false,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        boxWidth: 35,
                        usePointStyle: true,
                        padding: 20,
                        // Tùy chỉnh generateLabels để tạo legend item cho từng cột dữ liệu
                        generateLabels: function (chart) {
                            var data = chart.data;
                            if (data.labels.length && data.datasets.length) {
                                return data.labels.map(function (label, index) {
                                    var dataset = data.datasets[0];
                                    return {
                                        text: label,
                                        fillStyle: dataset.backgroundColor[index],
                                        strokeStyle: dataset.borderColor[index],
                                        lineWidth: dataset.borderWidth,
                                        hidden: false,
                                        index: index
                                    };
                                });
                            }
                            return [];
                        }
                    }
                },
                datalabels: {
                    color: '#000',
                    font: {
                        weight: 'bold',
                        size: 14
                    },
                    anchor: 'center',
                    align: 'center'
                }
            }
        }
    });
});
