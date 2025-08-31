/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Service;

/**
 *
 * @author Admin
 */
public class ServiceManagerController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServiceManagerController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceManagerController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
         ServiceDAO serviceDAO = new ServiceDAO();
        int pageSize = 6; // Số dịch vụ trên mỗi trang
        int currentPage = 1; // Trang mặc định

        // Lấy trang hiện tại từ request
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            currentPage = Integer.parseInt(pageParam);
        }

        // Lấy từ khóa và trạng thái từ request
        String keyword = request.getParameter("keyword");
        String statusFilter = request.getParameter("status");

        // Lấy danh sách dịch vụ dựa trên từ khóa và trạng thái
        ArrayList<Service> serviceList;
        int totalServices;
        if ((keyword != null && !keyword.isEmpty()) || (statusFilter != null && !statusFilter.isEmpty())) {
            totalServices = serviceDAO.getSearchAndFilterServicesCount(keyword, statusFilter);
            serviceList = serviceDAO.searchService(keyword, statusFilter, currentPage, pageSize);
        } else {
            totalServices = serviceDAO.getAllServicesCount();
            serviceList = serviceDAO.pagingService(currentPage, pageSize);
        }

        int totalPages = (int) Math.ceil((double) totalServices / pageSize);

        ArrayList<Service> top4Services = serviceDAO.getTop4ServicesBooked();

        // Đặt các thuộc tính cho JSP
        request.setAttribute("top4Services", top4Services);
        request.setAttribute("serviceList", serviceList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("statusFilter", statusFilter);

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher("manageService.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("alert", "Error: " + e.getMessage());
        response.sendRedirect("manageService");
    }
}

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        String submit = request.getParameter("submit");
        ServiceDAO serviceDAO = new ServiceDAO();
        ArrayList<Service> serviceList = serviceDAO.getAllServices();
        switch (submit) {
            case "add": {
                String serviceName = request.getParameter("serviceName");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                String status = request.getParameter("status");

                Service service = new Service();
                service.setServiceName(serviceName);
                service.setDescription(description);
                service.setPrice(price);
                service.setStatus(status);

                serviceDAO.addService(service);
                request.getSession().setAttribute("alert", "Service added successfully!");
                break;
            }
            case "update-service": {
                int serviceId = Integer.parseInt(request.getParameter("serviceId"));
                String serviceName = request.getParameter("serviceName");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                String status = request.getParameter("status");

                Service service = new Service();
                service.setServiceId(serviceId);
                service.setServiceName(serviceName);
                service.setDescription(description);
                service.setPrice(price);
                service.setStatus(status);

                serviceDAO.updateService(service);
                request.getSession().setAttribute("alert", "Update successfully!");
                break;
            }
            case "delete": {
                int serviceId = Integer.parseInt(request.getParameter("serviceId"));
                boolean isDeleted = serviceDAO.deleteService(serviceId);
            if (isDeleted) {
                request.setAttribute("alert", "Dịch vụ đã được xóa (ẩn khỏi danh sách).");
            } else {
                request.setAttribute("alert", "Xóa dịch vụ thất bại.");
            }
                request.getSession().setAttribute("alert", "Service deleted successfully!");
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid action: ");
        }

        // Cập nhật danh sách dịch vụ sau khi xử lý xong
        request.setAttribute("serviceList", serviceDAO.getAllServices());
        response.sendRedirect(request.getContextPath() + "/manageService");
    } catch (Exception e) {
        e.printStackTrace();
        request.getSession().setAttribute("alert", "Error: " + e.getMessage());
        response.sendRedirect("manageService");
    }
}
private ArrayList<Service> getPaginatedServices(ArrayList<Service> services, int currentPage, int pageSize) {
    int startIndex = (currentPage - 1) * pageSize;
    int endIndex = Math.min(startIndex + pageSize, services.size());
    return new ArrayList<>(services.subList(startIndex, endIndex));
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}