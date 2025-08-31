/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Room;


/**
 *
 * @author Admin
 */
public class RoomDAO extends DBContext {

    public ArrayList<Room> getAllRooms() {
        ArrayList<Room> listRooms = new ArrayList<>();
        String sql = "SELECT [RoomId],[RoomNumber],r.[CategoryId],[CategoryName],[Status]\n"
                + "FROM [dbo].[Room] r JOIN [dbo].[Category] c ON r.CategoryId = c.CategoryId";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("RoomId"));
                room.setRoomNumber(rs.getString("RoomNumber"));

                Category category = new Category(rs.getInt("CategoryId"), rs.getString("CategoryName"));
                room.setCategory(category);
                room.setStatus(rs.getString("Status"));
                listRooms.add(room);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRooms;
    }

    public List<Category> getAllRoomType() {
        List<Category> listRoomType = new ArrayList<>();
        String sql = "SELECT [CategoryId],[CategoryName]\n"
                + "FROM [dbo].[Category]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listRoomType.add(new Category(rs.getInt("CategoryId"), rs.getString("CategoryName")));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRoomType;
    }

    public Room getRoomByID(String id) {
        String sql = "SELECT [RoomId], [RoomNumber], [CategoryId], [Status], [Image],[RoomType] "
                + "FROM [Room] WHERE [RoomId] = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("RoomId"));
                room.setRoomNumber(rs.getString("RoomNumber"));
                room.setRoomType(rs.getString("RoomType"));
                return room;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        RoomDAO rd = new RoomDAO();
        Room room = rd.getRoomByID("1");
        System.out.println(room.getRoomType() + " " + room.getRoomNumber());
    }

    public Category getRoomCategoryById(int categoryId) {
        Category category = null;
        try {
            String sql = "SELECT [CategoryId]\n"
                    + "      ,[CategoryName]\n"
                    + "      ,[Capacity]\n"
                    + "      ,[PricePerNight]\n"
                    + "      ,[Description]\n"
                    + "      ,[Image]\n"
                    + "      ,[Size]\n"
                    + "      ,[Bed]\n"
                    + "  FROM [Category] WHERE CategoryId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("CategoryId"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setCapacity(rs.getInt("Capacity"));
                category.setPricePerNight(rs.getDouble("PricePerNight"));
                category.setDescription(rs.getString("Description"));
                category.setImage(rs.getString("Image"));
                category.setSize(rs.getDouble("Size"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    public ArrayList<Category> getSimilarRoomCategories(int currentCategoryId) {
        ArrayList<Category> similarCategories = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Category WHERE CategoryId != ? ORDER BY CategoryId OFFSET 0 ROWS FETCH NEXT 2 ROWS ONLY";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, currentCategoryId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("CategoryId"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setCapacity(rs.getInt("Capacity"));
                category.setPricePerNight(rs.getDouble("PricePerNight"));
                category.setDescription(rs.getString("Description"));
                category.setImage(rs.getString("Image"));
                category.setSize(rs.getDouble("Size"));
                similarCategories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return similarCategories;
    }

    public int getRoomCountByCategoryId(int categoryId) {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) AS RoomCount FROM Room WHERE CategoryId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                count = rs.getInt("RoomCount");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<Category> getTop3Category() {

        ArrayList<Category> list = new ArrayList<>();
        String sql = " \n"
                + "SELECT TOP (3)\n"
                + "    c.CategoryId,c.CategoryName,c.Description,c.Capacity,c.Image,c.PricePerNight,\n"
                + "    COUNT(*) AS NumberOfBookings\n"
                + "FROM \n"
                + "    [dbo].[Booking] b\n"
                + "join Room r on r.RoomId = b.RoomId\n"
                + "join Category c on r.CategoryId = c.CategoryId\n"
                + "GROUP BY \n"
                + "    c.CategoryId,c.CategoryName,c.Description,c.Capacity,c.Image,c.PricePerNight\n"
                + "ORDER BY \n"
                + "    NumberOfBookings DESC;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            DecimalFormat df = new DecimalFormat();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("CategoryId"));
                c.setCategoryName(rs.getString("CategoryName"));
                c.setCapacity(rs.getInt("Capacity"));
                c.setDescription(rs.getString("Description"));
                c.setImage(rs.getString("Image"));
                c.setPricePerNight(rs.getDouble("PricePerNight"));
                c.setFormattedPrice(df.format(c.getPricePerNight()));
                list.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int RoomCountByCategoryId(int categoryId, String checkOutDate, String checkInDate) {
        int availableRooms = 0; // lưu trữ số lượng phòng trống
        LocalDate currentDate = LocalDate.now();
        String defaultCheckInDate = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String defaultCheckOutDate = LocalDate.of(currentDate.getYear(), 12, 31)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        checkInDate = (checkInDate != null && !checkInDate.trim().isEmpty()) ? checkInDate : defaultCheckInDate;
        checkOutDate = (checkOutDate != null && !checkOutDate.trim().isEmpty()) ? checkOutDate : defaultCheckOutDate;

        String sql = "SELECT COUNT(r.RoomId) AS NumberOfAvailableRooms "
                + "FROM Room r "
                + "WHERE r.CategoryId = ? " // Điều kiện lọc theo categoryId
                + "AND r.Status = 'Available' "
                + "AND NOT EXISTS ( "
                + "    SELECT 1 FROM Booking b "
                + "    WHERE b.RoomId = r.RoomId "
                + "    AND (b.CheckInDate <= ? AND b.CheckOutDate >= ?) "
                + "    AND (b.BookingStatus = 'Done' OR b.BookingStatus = 'Late') "
                + ")";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setString(2, checkOutDate);
            ps.setString(3, checkInDate);

            ResultSet rs = ps.executeQuery();

            // Lấy kết quả trả về
            if (rs.next()) {
                availableRooms = rs.getInt("NumberOfAvailableRooms");
            }

        } catch (SQLException e) {
            System.out.println("Error counting available rooms: " + e.getMessage());
            e.printStackTrace();
        }

        return availableRooms; // Trả về số lượng phòng trống
    }

    public ArrayList<Category> ListCategory() {

        ArrayList<Category> list = new ArrayList<>();
        String sql = "SELECT [CategoryId]\n"
                + "      ,[CategoryName]\n"
                + "      ,[Capacity]\n"
                + "      ,[PricePerNight]\n"
                + "      ,[Description]\n"
                + "      ,[Image]\n"
                + "      ,[Size]\n"
                + "      ,[Bed]\n"
                + "  FROM [Category]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            DecimalFormat df = new DecimalFormat();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("CategoryId"));
                c.setCategoryName(rs.getString("CategoryName"));
                c.setCapacity(rs.getInt("Capacity"));
                c.setDescription(rs.getString("Description"));
                c.setImage(rs.getString("Image"));
                c.setPricePerNight(rs.getDouble("PricePerNight"));
                c.setFormattedPrice(df.format(c.getPricePerNight()));
                c.setSize(rs.getDouble("Size"));
                c.setBed(rs.getInt("Bed"));
                list.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Category> getAllBeds() {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "SELECT DISTINCT [Bed]\n"
                + "  FROM [Category]\n"
                + "  ORDER BY [Bed] ASC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setBed(rs.getInt("Bed"));
                list.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Category> getAllCapacities() {
        ArrayList<Category> list = new ArrayList<>();
        //lấy số lượng người trong một phòng 
        String sql = "SELECT DISTINCT [Capacity]\n"
                + "  FROM [Category]\n"
                + "  ORDER BY [Capacity] ASC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCapacity(rs.getInt("Capacity"));
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Category> searchCategory(String categoryName) {
        ArrayList<Category> list = new ArrayList<>();

        String sql = "SELECT [CategoryId]\n"
                + "      ,[CategoryName]\n"
                + "      ,[Capacity]\n"
                + "      ,[PricePerNight]\n"
                + "      ,[Description]\n"
                + "      ,[Image]\n"
                + "      ,[Size]\n"
                + "      ,[Bed]\n"
                + "  FROM [Category] c "
                + "  where c.CategoryName like ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, categoryName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryName(rs.getString("CategoryName"));
                c.setCapacity(rs.getInt("Capacity"));
                c.setDescription(rs.getString("Description"));
                c.setImage(rs.getString("Image"));
                c.setPricePerNight(rs.getDouble("PricePerNight"));
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Category> filterRoomCategory(Double minPrice, Double maxPrice, String categoryName, int capacity, int bed,
            String checkOutDate, String checkInDate) {
        ArrayList<Category> list = new ArrayList<>();
        double effectiveMinPrice;
        double effectiveMaxPrice;
        //lay minPrice
        if (minPrice != null && minPrice >= 0) {
            effectiveMinPrice = minPrice;
        } else {
            effectiveMinPrice = 0;
        }
        //lay Maxprice
        if (maxPrice != null && maxPrice > 0) {
            effectiveMaxPrice = maxPrice;
        } else {
            effectiveMaxPrice = getMaxPriceFromDatabase();
        }

        // Xử lý giá trị mặc định cho checkInDate và checkOutDate
        LocalDate currentDate = LocalDate.now();
        String defaultCheckInDate = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String defaultCheckOutDate = LocalDate.of(currentDate.getYear(), 12, 31)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        checkInDate = (checkInDate != null && !checkInDate.trim().isEmpty()) ? checkInDate : defaultCheckInDate;
        checkOutDate = (checkOutDate != null && !checkOutDate.trim().isEmpty()) ? checkOutDate : defaultCheckOutDate;

        // Xây dựng câu truy vấn SQL
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ")
                .append("c.CategoryId, ")
                .append("c.CategoryName, ")
                .append("c.Capacity, c.Size, ")
                .append("c.Bed, ")
                .append("c.PricePerNight, ")
                .append("c.Image, ")
                .append("COUNT(*) AS NumberOfRooms ")
                .append("FROM Room r ")
                .append("INNER JOIN Category c ON r.CategoryId = c.CategoryId ")
                .append("WHERE r.Status = 'Available' ")
                .append("AND NOT EXISTS ( ")
                .append("    SELECT 1 FROM Booking b ")
                .append("    WHERE b.RoomId = r.RoomId ")
                .append("    AND (b.CheckInDate <= ? AND b.CheckOutDate >= ?) ")
                .append("    AND (b.BookingStatus = 'Done' OR b.BookingStatus = 'Late') ")
                .append(") ");

        // Danh sách tham số cho PreparedStatement
        ArrayList<Object> params = new ArrayList<>();
        params.add(checkOutDate);
        params.add(checkInDate);

        // Thêm điều kiện lọc theo categoryName nếu có
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            sqlBuilder.append("AND c.CategoryName = ? ");
            params.add(categoryName);
        }

        if (bed > 0) {
            sqlBuilder.append("AND c.Bed = ? ");
            params.add(bed);
        }

        if (capacity > 0) {
            sqlBuilder.append("AND c.Capacity = ? ");
            params.add(capacity);
        }

        // Thêm điều kiện lọc theo minPrice và maxPrice
        sqlBuilder.append("AND c.PricePerNight >= ? AND c.PricePerNight <= ? ");
        params.add(effectiveMinPrice);
        params.add(effectiveMaxPrice);

        // Hoàn tất câu truy vấn
        sqlBuilder.append("GROUP BY c.CategoryId, c.CategoryName, c.Capacity,c.Size, c.Bed, c.PricePerNight, c.Image ")
                .append("ORDER BY c.CategoryName");

        try {
            // Chuẩn bị câu lệnh SQL
            PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString());

            // Gán tham số vào PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            // Thực thi truy vấn
            ResultSet rs = ps.executeQuery();

            // Xử lý kết quả trả về
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("CategoryId"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setCapacity(rs.getInt("Capacity"));
                category.setSize(rs.getDouble("Size"));
                category.setBed(rs.getInt("Bed"));
                category.setPricePerNight(rs.getDouble("PricePerNight"));
                category.setNumberOfRooms(rs.getInt("NumberOfRooms"));
                category.setImage(rs.getString("Image"));
                list.add(category);
            }

        } catch (SQLException e) {
            System.out.println("Error filtering room categories: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

// Hàm lấy giá trị giá tối đa từ cơ sở dữ liệu
    private double getMaxPriceFromDatabase() {
        double maxPrice = 0.0; // Giá trị mặc định nếu không tìm thấy

        String sql = "SELECT MAX(PricePerNight) AS MaxPrice FROM Category";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maxPrice = rs.getDouble("MaxPrice");
            }

        } catch (SQLException e) {
            System.out.println("Error getting max price: " + e.getMessage());
            e.printStackTrace();
        }

        return maxPrice;
    }

    public int getTotalRoom() {
        ArrayList<Room> list = new ArrayList<>();
        String sql = "SELECT  [RoomId]\n"
                + "      ,[RoomNumber]\n"
                + "      ,[CategoryId]\n"
                + "      ,[Status]\n"
                + "  FROM [Room]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Room r = new Room();
                r.setId(rs.getInt("RoomId"));
                r.setRoomNumber(rs.getString("RoomNumber"));
                r.setStatus(rs.getString("Status"));
                list.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list.size();
    }

    public int[] getTotalRoomWithStatus() {
        String sql = "SELECT COUNT(RoomId) AS TotalRooms,\n"
                + "          COUNT(CASE WHEN Status = 'Available' THEN 1 END) AS AvailableRooms,\n"
                + "          COUNT(CASE WHEN Status = 'Unavailable' THEN 1 END) AS UnavailableRooms,\n"
                + "          COUNT(CASE WHEN Status = 'Maintenance' THEN 1 END) AS MaintenanceRooms\n"
                + " FROM [dbo].[Room]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int totalRooms = rs.getInt("TotalRooms");
                int availableRooms = rs.getInt("AvailableRooms");
                int unavailableRooms = rs.getInt("UnavailableRooms");
                int maintenanceRooms = rs.getInt("MaintenanceRooms");
                return new int[]{totalRooms, availableRooms, unavailableRooms, maintenanceRooms};
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new int[]{0, 0, 0, 0};
    }

    public boolean checkRoomNumberExist(String roomNumber) {
        String sql = "SELECT [RoomNumber]\n"
                + "FROM [dbo].[Room]\n"
                + "WHERE [RoomNumber] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomNumber);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void addRoom(String roomNumber, int categoryId, String status) {
        String sql = "INSERT INTO [dbo].[Room]([RoomNumber],[CategoryId],[Status])\n"
                + "VALUES (?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomNumber);
            st.setInt(2, categoryId);
            st.setString(3, status);
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRoom(int categoryId, String status, String roomNumber) {
        String sql = "UPDATE [dbo].[Room]\n"
                + "   SET [CategoryId] = ?,[Status] = ?\n"
                + "   WHERE [RoomNumber] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, categoryId);
            st.setString(2, status);
            st.setString(3, roomNumber);
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int[] getTotalFeedbackWithRating() {
        String sql = "SELECT COUNT(*) AS TotalFeedback,\n"
                + "          SUM(CASE WHEN Rating = 1 THEN 1 ELSE 0 END) AS OneStar,\n"
                + "          SUM(CASE WHEN Rating = 2 THEN 1 ELSE 0 END) AS TwoStar,\n"
                + "          SUM(CASE WHEN Rating = 3 THEN 1 ELSE 0 END) AS ThreeStar,\n"
                + "          SUM(CASE WHEN Rating = 4 THEN 1 ELSE 0 END) AS FourStar,\n"
                + "          SUM(CASE WHEN Rating = 5 THEN 1 ELSE 0 END) AS FiveStar\n"
                + "FROM [dbo].[Feedback];";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int totalFeedback = rs.getInt("TotalFeedback");
                int oneStar = rs.getInt("OneStar");
                int twoStar = rs.getInt("TwoStar");
                int threeStar = rs.getInt("ThreeStar");
                int fourStar = rs.getInt("FourStar");
                int fiveStar = rs.getInt("FiveStar");
                return new int[]{totalFeedback, oneStar, twoStar, threeStar, fourStar, fiveStar};
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new int[]{0, 0, 0, 0, 0, 0};
    }
}
