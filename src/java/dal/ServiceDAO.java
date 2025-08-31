/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Service;

/**
 *
 * @author Admin
 */
public class ServiceDAO extends DBContext{
    
   

    

    // Get the total count of services
    public int getAllServicesCount() {
        String sql = " SELECT COUNT(*) FROM Service\n" +
"      Where Service.Status = 'Available' Or Service.Status = 'Unavailable'";
        try (PreparedStatement stmt = connection.prepareStatement(sql); 
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Service> searchService(String keyword, String status, int page, int pageSize) {
        ArrayList<Service> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT s.ServiceId, s.ServiceName, s.Description, s.Price, s.Status, "
                + "COUNT(su.ServiceId) AS TotalQuantity, "
                + "COUNT(su.ServiceId) * s.Price AS TotalRevenue "
                + "FROM Service s "
                + "LEFT JOIN ServiceUsage su ON s.ServiceId = su.ServiceId "
                + "WHERE s.ServiceName LIKE ? "
                + (status != null && !status.isEmpty() ? "AND s.Status = ? " : "")
                + "GROUP BY s.ServiceId, s.ServiceName, s.Description, s.Price, s.Status "
                + "ORDER BY s.ServiceId DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1,keyword + "%");
            int paramIndex = 2;
            if (status != null && !status.isEmpty()) {
                stmt.setString(paramIndex++, status);
            }
            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex, pageSize);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Service service = new Service();
                service.setServiceId(rs.getInt("ServiceId"));
                service.setServiceName(rs.getString("ServiceName"));
                service.setDescription(rs.getString("Description"));
                service.setPrice(rs.getDouble("Price"));
                service.setStatus(rs.getString("Status"));
                service.setQuantity(rs.getInt("TotalQuantity"));
                service.setTotalRevenue(rs.getDouble("TotalRevenue"));
                list.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

// Phương thức để đếm tổng số dịch vụ phù hợp với cả từ khóa và trạng thái
    public int getSearchAndFilterServicesCount(String keyword, String status) {
        String sql = "SELECT COUNT(*) FROM Service WHERE ServiceName LIKE ? " 
                + (status != null && !status.isEmpty() ? "AND Status = ?" : "");
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1,keyword + "%");
            if (status != null && !status.isEmpty()) {
                stmt.setString(2, status);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Service> getTop4ServicesBooked() {
        ArrayList<Service> list = new ArrayList<>();
        String sql = "SELECT TOP (4)\n"
                + "    s.ServiceName, s.Description,s.Status, \n"
                + "   COUNT(su.ServiceId) AS TotalQuantity, \n"
                + "             COUNT(su.ServiceId) * s.Price AS TotalRevenue \n"
                + "FROM \n"
                + "    ServiceUsage su\n"
                + "JOIN \n"
                + "    Service s ON su.ServiceId = s.ServiceId\n"
                + "Where s.Status = 'Available'"
                + "GROUP BY \n"
                + "    s.ServiceName, s.Description,s.Status,s.Price\n"
                + "ORDER BY \n"
                + "    TotalQuantity DESC;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Service service = new Service();
                service.setServiceName(rs.getString("ServiceName"));
                service.setDescription(rs.getString("Description"));
                service.setStatus(rs.getString("Status"));
                service.setQuantity(rs.getInt("TotalQuantity"));
                service.setTotalRevenue(rs.getDouble("TotalRevenue"));
                list.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get services for a specific page using OFFSET-FETCH
    public ArrayList<Service> pagingService(int page, int pageSize) {
        ArrayList<Service> list = new ArrayList<>();
        int offset = (page - 1) * pageSize; // Calculate the offset for pagination
        String sql = "SELECT s.ServiceId, s.ServiceName, s.Description, s.Price, s.Status "
                + "FROM Service s "
                 + "Where s.Status = 'Available' Or s.Status = 'Unavailable' "
                + "ORDER BY s.ServiceId DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"; // Use OFFSET and FETCH for pagination

        // Get service usage summary
        String sql2 = "SELECT "
                + "    s.ServiceName, "
                + "   COUNT(su.ServiceId) AS TotalQuantity, \n"
                + "             COUNT(su.ServiceId) * s.Price AS TotalRevenue \n"
                + "FROM "
                + "    ServiceUsage su "
                + "JOIN "
                + "    Service s ON su.ServiceId = s.ServiceId "
                + "GROUP BY "
                + "    s.ServiceName,s.Price "
                + "ORDER BY "
                + "    TotalRevenue DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Service service = new Service();
                service.setServiceId(rs.getInt("ServiceId"));
                service.setServiceName(rs.getString("ServiceName"));
                service.setDescription(rs.getString("Description"));
                service.setPrice(rs.getDouble("Price"));
                service.setStatus(rs.getString("Status"));

                try (PreparedStatement stm = connection.prepareStatement(sql2)) {
                    ResultSet rs2 = stm.executeQuery();
                    while (rs2.next()) {
                        if (rs2.getString("ServiceName").equals(service.getServiceName())) {
                            service.setQuantity(rs2.getInt("TotalQuantity"));
                            service.setTotalRevenue(rs2.getDouble("TotalRevenue"));
                            break;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                list.add(service);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

//    public static void main(String[] args) {
//        ServiceDAO s = new ServiceDAO();
//        Service service = new Service();
////        ArrayList<Service> services = s.searchService("n", 1, 6);
////        for (Service service1 : services) {
////            System.out.println(service1.getServiceName());
////        }
//
////        for (Service service1 : services) {
////            System.out.println("Service Name: " + service1.getServiceName());
////            System.out.println("Total Quantity: " + service1.getTotalQuantity());
////            System.out.println("Total Revenue: " + service1.getTotalRevenue());
////            System.out.println();
////        }
//        //System.out.print(s.getAllServicesCount() + ", " + s.pagingService(1, 6));
//    }

    public boolean updateExistedService(Service service) {
        String sql = "UPDATE Service SET ServiceName = ?, Description = ?, Price = ?, Status = ? WHERE ServiceId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, service.getServiceName());
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getPrice());
            stmt.setString(4, service.getStatus());
            stmt.setInt(5, service.getServiceId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateService(Service service) {
        String sql = "UPDATE Service SET ServiceName = ?, Description = ?, Price = ?, Status = ? WHERE ServiceId = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, service.getServiceName());
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getPrice());
            stmt.setString(4, service.getStatus());
            stmt.setInt(5, service.getServiceId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Service updated successfully!");
            } else {
                System.out.println("Failed to update service. No rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Service> getAllServices() {
        ArrayList<Service> list = new ArrayList<>();

        // Get all services
        String sql1 = "SELECT s.ServiceId, s.ServiceName, s.Description, s.Price, s.Status "
                + "FROM Service s "
                                + "Where s.Status = 'Available' Or s.Status = 'Unavailable' "
                + "ORDER BY s.ServiceId ASC";

        // Get service usage summary
        String sql2 = "SELECT "
                + "    s.ServiceName, "
                + "   COUNT(su.ServiceId) AS TotalQuantity, \n"
                + "             COUNT(su.ServiceId) * s.Price AS TotalRevenue \n"
                + "FROM "
                + "    ServiceUsage su "
                + "JOIN "
                + "    Service s ON su.ServiceId = s.ServiceId "
                + "GROUP BY "
                + "    s.ServiceName,s.Price "
                + "ORDER BY "
                + "    TotalRevenue DESC";

        try {
            PreparedStatement st1 = connection.prepareStatement(sql1);
            ResultSet rs1 = st1.executeQuery();

            // Map service details from first query
            while (rs1.next()) {
                Service service = new Service();
                service.setServiceId(rs1.getInt("ServiceId"));
                service.setServiceName(rs1.getString("ServiceName"));
                service.setDescription(rs1.getString("Description"));
                service.setPrice(rs1.getDouble("Price"));
                service.setStatus(rs1.getString("Status"));

                // Find the corresponding service usage summary
                PreparedStatement st2 = connection.prepareStatement(sql2);
                ResultSet rs2 = st2.executeQuery();
                while (rs2.next()) {
                    if (rs2.getString("ServiceName").equals(service.getServiceName())) {
                        service.setQuantity(rs2.getInt("TotalQuantity"));
                        service.setTotalRevenue(rs2.getDouble("TotalRevenue"));
                        break;
                    }
                }

                list.add(service);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    // Lấy thông tin một dịch vụ theo ID
    public Service getServiceById(int serviceId) {
        Service service = null;
        String sql = "SELECT s.ServiceId, s.ServiceName, s.Description, s.Price, s.Status, cs.CategoryId "
                + "FROM Service s "
                + "WHERE s.ServiceId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, serviceId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                service = new Service();
                service.setServiceId(rs.getInt("ServiceId"));
                service.setServiceName(rs.getString("ServiceName"));
                service.setDescription(rs.getString("Description"));
                service.setPrice(rs.getDouble("Price"));
                service.setStatus(rs.getString("Status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return service;
    }

    // Thêm một dịch vụ mới
    public void addService(Service service) {
        String serviceSql = "INSERT INTO Service (ServiceName, Description, Price, Status) VALUES (?, ?, ?, ?)";
        try {
            // Thêm vào bảng Service
            PreparedStatement serviceSt = connection.prepareStatement(serviceSql);
            serviceSt.setString(1, service.getServiceName());
            serviceSt.setString(2, service.getDescription());
            serviceSt.setDouble(3, service.getPrice());
            serviceSt.setString(4, service.getStatus());
            serviceSt.executeUpdate();

            // Lấy ServiceId tự động tạo
            ResultSet generatedKeys = serviceSt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int serviceId = generatedKeys.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // delete a service -> thuc te la chuyen status ve trang thai da xoa va khong list ra man hinh
    public boolean deleteService(int serviceId) {
    String sql = "UPDATE Service SET Status = 'deleted' WHERE ServiceId = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, serviceId);
        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    
    public List<Service> getServiceUsageByBookingId(int bookingId) {
    List<Service> serviceUsages = new ArrayList<>();
    String sql = "SELECT s.ServiceName, su.Quantity, s.Price " +
                 "FROM ServiceUsage su " +
                 "JOIN Service s ON su.ServiceId = s.ServiceId " +
                 "WHERE su.BookingId = ?";

    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, bookingId);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Service serviceUsage = new Service();
            serviceUsage.setServiceName(rs.getString("ServiceName"));
            serviceUsage.setQuantity(rs.getInt("Quantity"));
            serviceUsage.setPrice(rs.getDouble("Price"));
            serviceUsages.add(serviceUsage);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return serviceUsages;
}
    

    // Lấy thông tin một dịch vụ theo ID
    

    // Thêm một dịch vụ mới
    

    // Xóa một dịch vụ
    
    
    
    
    public ArrayList<Service> getAvailableServices() {
    ArrayList<Service> list = new ArrayList<>();
    String sql = "SELECT [ServiceId], [ServiceName], [Description], [Price], [Status] " +
                 "FROM [HotelManagement].[dbo].[Service] " +
                 "WHERE [Status] = 'Available' " +
                 "ORDER BY [ServiceId] ASC";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Service service = new Service();
            service.setServiceName(rs.getString("ServiceName"));
            service.setDescription(rs.getString("Description"));
            service.setPrice(rs.getDouble("Price"));
            service.setStatus(rs.getString("Status"));
            list.add(service);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}
    
    public ArrayList<Service> getServiceByBookingID(String id) {
    ArrayList<Service> services = new ArrayList<>();
    String sql = "SELECT s.[ServiceId], s.[ServiceName], s.[Description], s.[Price], s.[Status] " +
                 "FROM [ServiceUsage] su " +
                 "JOIN [Service] s ON su.[ServiceId] = s.[ServiceId] " +
                 "WHERE su.[BookingId] = ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, id); // Đặt giá trị BookingId vào câu lệnh SQL
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Service service = new Service();
                service.setServiceId(rs.getInt("ServiceId"));
                service.setServiceName(rs.getString("ServiceName"));
                service.setDescription(rs.getString("Description"));
                service.setPrice(rs.getDouble("Price"));
                service.setStatus(rs.getString("Status"));
                services.add(service);
            }
        }
    } catch (Exception e) {
        System.out.println("Error getServiceByBookingID: " + e.getMessage());
    }
    return services;
}
    
    public static void main(String[] args) {
        ServiceDAO sv = new ServiceDAO();
        ArrayList<Service> lt = sv.getServiceByBookingID(String.valueOf(2));
        for (Service service : lt) {
            System.out.println(service.getServiceName()+" "+service.getPrice());
        }
    }
    
    public ArrayList<Service> getServiceByCategory(){
        ArrayList<Service> services = new ArrayList<>();
        
        
        return services;
    }
}
