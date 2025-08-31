package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Booking;
import java.math.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.Category;
import model.Room;
import model.Service;

public class BookingDAO extends DBContext {

    public List<Booking> getBookingByAccountId(int accountId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.[BookingId], b.[AccountId], a.[FullName], b.[RoomId], r.[RoomNumber], "
                + "c.[CategoryName], b.[CheckInDate], b.[CheckOutDate], COALESCE(b.[TotalPrice], 0) AS [TotalPrice], "
                + "b.[BookingStatus], b.[BookingDate], b.[Note], c.[PricePerNight] "
                + "FROM [dbo].[Booking] b "
                + "JOIN [dbo].[Account] a ON b.[AccountId] = a.[AccountId] "
                + "JOIN [dbo].[Room] r ON b.[RoomId] = r.[RoomId] "
                + "JOIN [dbo].[Category] c ON r.[CategoryId] = c.[CategoryId] "
                + "WHERE b.[AccountId] = ? "
                + "ORDER BY b.[BookingDate] DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setBookingId(rs.getInt("BookingId"));

                    Account account = new Account();
                    account.setAccountId(rs.getInt("AccountId"));
                    account.setFullName(rs.getString("FullName"));
                    booking.setAccount(account);

                    Room room = new Room();
                    room.setId(rs.getInt("RoomId"));
                    room.setRoomNumber(rs.getString("RoomNumber"));

                    Category category = new Category();
                    category.setCategoryName(rs.getString("CategoryName"));
                    category.setPricePerNight(rs.getDouble("PricePerNight"));
                    room.setCategory(category);

                    booking.setRoom(room);
                    Timestamp checkInTimestamp = rs.getTimestamp("CheckInDate");
                    Timestamp checkOutTimestamp = rs.getTimestamp("CheckOutDate");
                    Timestamp bookingTimestamp = rs.getTimestamp("BookingDate");

                    booking.setCheckInDate(checkInTimestamp != null ? checkInTimestamp.toLocalDateTime() : null);
                    booking.setCheckOutDate(checkOutTimestamp != null ? checkOutTimestamp.toLocalDateTime() : null);
                    booking.setBookingDate(bookingTimestamp != null ? bookingTimestamp.toLocalDateTime() : null);
                    booking.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                    booking.setBookingStatus(rs.getString("BookingStatus"));
                    booking.setNote(rs.getString("Note"));

                    list.add(booking);
                }
            }
        } catch (Exception e) {
            System.out.println("Error getBookingByAccountId: " + e.getMessage());
        }
        return list;
    }

    public Booking getBookingById(int bookingId) {
    String sql = "SELECT b.[BookingId], b.[AccountId], a.[FullName], b.[RoomId], r.[RoomNumber], "
            + "c.[CategoryName], b.[CheckInDate], b.[CheckOutDate], COALESCE(b.[TotalPrice], 0) AS [TotalPrice], "
            + "b.[BookingStatus], b.[BookingDate], b.[Note], c.[PricePerNight] "
            + "FROM [dbo].[Booking] b "
            + "JOIN [dbo].[Account] a ON b.[AccountId] = a.[AccountId] "
            + "JOIN [dbo].[Room] r ON b.[RoomId] = r.[RoomId] "
            + "JOIN [dbo].[Category] c ON r.[CategoryId] = c.[CategoryId] "
            + "WHERE b.[BookingId] = ?";

    Booking booking = null;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, bookingId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                booking = new Booking();
                booking.setBookingId(rs.getInt("BookingId"));

                Account account = new Account();
                account.setAccountId(rs.getInt("AccountId"));
                account.setFullName(rs.getString("FullName"));
                booking.setAccount(account);

                Room room = new Room();
                room.setId(rs.getInt("RoomId"));
                room.setRoomNumber(rs.getString("RoomNumber"));

                Category category = new Category();
                category.setCategoryName(rs.getString("CategoryName"));
                category.setPricePerNight(rs.getDouble("PricePerNight"));
                room.setCategory(category);

                booking.setRoom(room);
                
                Timestamp checkInTimestamp = rs.getTimestamp("CheckInDate");
                Timestamp checkOutTimestamp = rs.getTimestamp("CheckOutDate");
                Timestamp bookingTimestamp = rs.getTimestamp("BookingDate");

                booking.setCheckInDate(checkInTimestamp != null ? checkInTimestamp.toLocalDateTime() : null);
                booking.setCheckOutDate(checkOutTimestamp != null ? checkOutTimestamp.toLocalDateTime() : null);
                booking.setBookingDate(bookingTimestamp != null ? bookingTimestamp.toLocalDateTime() : null);
                booking.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                booking.setBookingStatus(rs.getString("BookingStatus"));
                booking.setNote(rs.getString("Note"));
            }
        }
    } catch (Exception e) {
        System.out.println("Error getBookingById: " + e.getMessage());
    }

    return booking;
}


    public List<Booking> getTodayNotYetBooking() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.[BookingId], b.[AccountId], a.[FullName], b.[RoomId], r.[RoomNumber], "
                + "c.[CategoryName], b.[CheckInDate], b.[CheckOutDate], COALESCE(b.[TotalPrice], 0) AS [TotalPrice], "
                + "b.[BookingStatus], b.[BookingDate], b.[Note], f.[FacilityName], s.[ServiceName], "
                + "s.[Price], su.[Quantity], c.[PricePerNight] "
                + "FROM [dbo].[Booking] b "
                + "JOIN [dbo].[Account] a ON b.[AccountId] = a.[AccountId] "
                + "JOIN [dbo].[Room] r ON b.[RoomId] = r.[RoomId] "
                + "JOIN [dbo].[Category] c ON r.[CategoryId] = c.[CategoryId] "
                + "LEFT JOIN [dbo].[Categories_Facilities] cf ON c.[CategoryId] = cf.[CategoryId] "
                + "LEFT JOIN [dbo].[Facilities] f ON f.[FacilityId] = cf.[FacilityId] "
                + "LEFT JOIN [dbo].[ServiceUsage] su ON su.[BookingId] = b.[BookingId] "
                + "LEFT JOIN [dbo].[Service] s ON s.[ServiceId] = su.[ServiceId] "
                + "WHERE b.[BookingStatus] = 'Not yet' "
                + "AND CONVERT(DATE, b.[CheckInDate]) = CONVERT(DATE, GETDATE()) "
                + "ORDER BY b.[BookingId]";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("BookingId"));

                Account account = new Account();
                account.setAccountId(rs.getInt("AccountId"));
                account.setFullName(rs.getString("FullName"));
                booking.setAccount(account);

                Room room = new Room();
                room.setId(rs.getInt("RoomId"));
                room.setRoomNumber(rs.getString("RoomNumber"));

                Category category = new Category();
                category.setCategoryName(rs.getString("CategoryName"));
                category.setPricePerNight(rs.getDouble("PricePerNight"));
                room.setCategory(category);

                booking.setRoom(room);

                Timestamp checkInTimestamp = rs.getTimestamp("CheckInDate");
                Timestamp checkOutTimestamp = rs.getTimestamp("CheckOutDate");
                Timestamp bookingTimestamp = rs.getTimestamp("BookingDate");

                booking.setCheckInDate(checkInTimestamp != null ? checkInTimestamp.toLocalDateTime() : null);
                booking.setCheckOutDate(checkOutTimestamp != null ? checkOutTimestamp.toLocalDateTime() : null);
                booking.setBookingDate(bookingTimestamp != null ? bookingTimestamp.toLocalDateTime() : null);
                booking.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                booking.setBookingStatus(rs.getString("BookingStatus"));
                booking.setNote(rs.getString("Note"));

                list.add(booking);
            }
        } catch (Exception e) {
            System.out.println("Error getTodayNotYetBooking: " + e.getMessage());
        }

        return list;
    }

    public int countBookingByAccountID(String id) {
        String sql = "SELECT COUNT(*) FROM BOOKING WHERE AccountId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int a = rs.getInt(1);
                    if (a > 3) {
                        return 3;
                    } else {
                        return a;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
    
    public static void main(String[] args) {
        BookingDAO bd = new BookingDAO();
        
        List<Booking> a = bd.getBookingList();
        for (Booking booking : a) {
            System.out.println(booking.getBookingId()+" "+booking.getBookingStatus());
        }
    }

    public List<Booking> getAllNotYetBookingBeforeToday() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.[BookingId], b.[AccountId], a.[FullName], b.[RoomId], r.[RoomNumber], c.[CategoryName], [CheckInDate], [CheckOutDate], COALESCE(b.[TotalPrice], 0) AS [TotalPrice], [BookingStatus], [BookingDate], [Note], f.[FacilityName], s.[ServiceName], s.[Price], c.[PricePerNight] " +
             "FROM [dbo].[Booking] b JOIN [dbo].[Account] a ON b.AccountId = a.AccountId " +
             "JOIN [dbo].[Room] r ON b.RoomId = r.RoomId " +
             "JOIN [dbo].[Category] c ON r.CategoryId = c.CategoryId " +
             "LEFT JOIN [dbo].[Categories_Facilities] cf ON c.CategoryId = cf.CategoryId " +
             "LEFT JOIN [dbo].[Facilities] f ON f.FacilityId = cf.FacilityId " +
             "LEFT JOIN [dbo].[ServiceUsage] su ON su.BookingId = b.BookingId " +
             "LEFT JOIN [dbo].[Service] s ON s.ServiceId = su.ServiceId " +  
             "WHERE b.[BookingStatus] = 'Not yet' " +
             "AND b.[CheckInDate] < CONVERT(DATE, GETDATE()) " +
             "ORDER BY b.[CheckInDate] ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("BookingId"));

                Account account = new Account();
                account.setAccountId(rs.getInt("AccountId"));
                account.setFullName(rs.getString("FullName"));
                booking.setAccount(account);

                Room room = new Room();
                room.setId(rs.getInt("RoomId"));
                room.setRoomNumber(rs.getString("RoomNumber"));

                Category category = new Category();
                category.setCategoryName(rs.getString("CategoryName"));
                category.setPricePerNight(rs.getDouble("PricePerNight"));
                room.setCategory(category);

                booking.setRoom(room);

                Timestamp checkInTimestamp = rs.getTimestamp("CheckInDate");
                Timestamp checkOutTimestamp = rs.getTimestamp("CheckOutDate");
                Timestamp bookingTimestamp = rs.getTimestamp("BookingDate");

                booking.setCheckInDate(checkInTimestamp != null ? checkInTimestamp.toLocalDateTime() : null);
                booking.setCheckOutDate(checkOutTimestamp != null ? checkOutTimestamp.toLocalDateTime() : null);
                booking.setBookingDate(bookingTimestamp != null ? bookingTimestamp.toLocalDateTime() : null);
                booking.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                booking.setBookingStatus(rs.getString("BookingStatus"));
                booking.setNote(rs.getString("Note"));

                list.add(booking);
            }
        } catch (Exception e) {
            System.out.println("Error getAllNotYetBookingBeforeToday: " + e.getMessage());
        }

        return list;
    }

    public List<Booking> getBookingList() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.[BookingId],b.[AccountId],a.[FullName],b.[RoomId],r.[RoomNumber],c.[CategoryName],[CheckInDate],[CheckOutDate],COALESCE(b.[TotalPrice], 0) AS [TotalPrice],[BookingStatus],[BookingDate],[Note],c.[PricePerNight]\n"
                + "FROM [dbo].[Booking] b JOIN [dbo].[Account] a ON b.AccountId = a.AccountId\n"
                + "		 JOIN [dbo].[Room] r ON b.RoomId = r.RoomId\n"
                + "		 JOIN [dbo].[Category] c ON r.CategoryId = c.CategoryId\n";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("BookingId"));

                Account account = new Account();
                account.setAccountId(rs.getInt("AccountId"));
                account.setFullName(rs.getString("FullName"));
                booking.setAccount(account);

                Room room = new Room();
                room.setId(rs.getInt("RoomId"));
                room.setRoomNumber(rs.getString("RoomNumber"));

                Category category = new Category();
                category.setCategoryName(rs.getString("CategoryName"));
                category.setPricePerNight(rs.getDouble("PricePerNight"));
                room.setCategory(category);

                booking.setRoom(room);

                Timestamp checkInTimestamp = rs.getTimestamp("CheckInDate");
                Timestamp checkOutTimestamp = rs.getTimestamp("CheckOutDate");
                Timestamp bookingTimestamp = rs.getTimestamp("BookingDate");

                booking.setCheckInDate(checkInTimestamp != null ? checkInTimestamp.toLocalDateTime() : null);
                booking.setCheckOutDate(checkOutTimestamp != null ? checkOutTimestamp.toLocalDateTime() : null);
                booking.setBookingDate(bookingTimestamp != null ? bookingTimestamp.toLocalDateTime() : null);

                booking.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                booking.setBookingStatus(rs.getString("BookingStatus"));

                booking.setNote(rs.getString("Note"));

                list.add(booking);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    

    public List<Booking> getCheckInList() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.[BookingId], b.[AccountId], a.[FullName], b.[RoomId], r.[RoomNumber], "
                + "c.[CategoryName], b.[CheckInDate], b.[CheckOutDate], COALESCE(b.[TotalPrice], 0) AS [TotalPrice], "
                + "b.[BookingStatus], b.[BookingDate], b.[Note], c.[PricePerNight] "
                + "FROM [dbo].[Booking] b "
                + "JOIN [dbo].[Account] a ON b.[AccountId] = a.[AccountId] "
                + "JOIN [dbo].[Room] r ON b.[RoomId] = r.[RoomId] "
                + "JOIN [dbo].[Category] c ON r.[CategoryId] = c.[CategoryId] "
                + "WHERE b.[BookingStatus] = 'Not Yet'";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("BookingId"));

                Account account = new Account();
                account.setAccountId(rs.getInt("AccountId"));
                account.setFullName(rs.getString("FullName"));
                booking.setAccount(account);

                Room room = new Room();
                room.setId(rs.getInt("RoomId"));
                room.setRoomNumber(rs.getString("RoomNumber"));

                Category category = new Category();
                category.setCategoryName(rs.getString("CategoryName"));
                category.setPricePerNight(rs.getDouble("PricePerNight"));
                room.setCategory(category);

                booking.setRoom(room);
                Timestamp checkInTimestamp = rs.getTimestamp("CheckInDate");
                Timestamp checkOutTimestamp = rs.getTimestamp("CheckOutDate");
                Timestamp bookingTimestamp = rs.getTimestamp("BookingDate");

                booking.setCheckInDate(checkInTimestamp != null ? checkInTimestamp.toLocalDateTime() : null);
                booking.setCheckOutDate(checkOutTimestamp != null ? checkOutTimestamp.toLocalDateTime() : null);
                booking.setBookingDate(bookingTimestamp != null ? bookingTimestamp.toLocalDateTime() : null);
                booking.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                booking.setBookingStatus(rs.getString("BookingStatus"));
                booking.setNote(rs.getString("Note"));

                list.add(booking);
            }
        } catch (Exception e) {
            System.out.println("Error getCheckInList: " + e.getMessage());
        }
        return list;
    }

    public List<Booking> getCheckOutList() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.[BookingId], b.[AccountId], a.[FullName], b.[RoomId], r.[RoomNumber], "
                + "c.[CategoryName], b.[CheckInDate], b.[CheckOutDate], COALESCE(b.[TotalPrice], 0) AS [TotalPrice], "
                + "b.[BookingStatus], b.[BookingDate], b.[Note], c.[PricePerNight] "
                + "FROM [dbo].[Booking] b "
                + "JOIN [dbo].[Account] a ON b.[AccountId] = a.[AccountId] "
                + "JOIN [dbo].[Room] r ON b.[RoomId] = r.[RoomId] "
                + "JOIN [dbo].[Category] c ON r.[CategoryId] = c.[CategoryId] "
                + "WHERE b.[BookingStatus] = 'On Going'";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("BookingId"));

                Account account = new Account();
                account.setAccountId(rs.getInt("AccountId"));
                account.setFullName(rs.getString("FullName"));
                booking.setAccount(account);

                Room room = new Room();
                room.setId(rs.getInt("RoomId"));
                room.setRoomNumber(rs.getString("RoomNumber"));

                Category category = new Category();
                category.setCategoryName(rs.getString("CategoryName"));
                category.setPricePerNight(rs.getDouble("PricePerNight"));
                room.setCategory(category);

                booking.setRoom(room);
                Timestamp checkInTimestamp = rs.getTimestamp("CheckInDate");
                Timestamp checkOutTimestamp = rs.getTimestamp("CheckOutDate");
                Timestamp bookingTimestamp = rs.getTimestamp("BookingDate");

                booking.setCheckInDate(checkInTimestamp != null ? checkInTimestamp.toLocalDateTime() : null);
                booking.setCheckOutDate(checkOutTimestamp != null ? checkOutTimestamp.toLocalDateTime() : null);
                booking.setBookingDate(bookingTimestamp != null ? bookingTimestamp.toLocalDateTime() : null);
                booking.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                booking.setBookingStatus(rs.getString("BookingStatus"));
                booking.setNote(rs.getString("Note"));

                list.add(booking);
            }
        } catch (Exception e) {
            System.out.println("Error getCheckOutList: " + e.getMessage());
        }
        return list;
    }

    public List<Booking> getAllNotYetBooking() {
    List<Booking> list = new ArrayList<>();
    String sql = "SELECT b.[BookingId], b.[AccountId], a.[FullName], b.[RoomId], r.[RoomNumber], "
            + "c.[CategoryName], b.[CheckInDate], b.[CheckOutDate], COALESCE(b.[TotalPrice], 0) AS [TotalPrice], "
            + "b.[BookingStatus], b.[BookingDate], b.[Note], c.[PricePerNight] "
            + "FROM [dbo].[Booking] b "
            + "JOIN [dbo].[Account] a ON b.[AccountId] = a.[AccountId] "
            + "JOIN [dbo].[Room] r ON b.[RoomId] = r.[RoomId] "
            + "JOIN [dbo].[Category] c ON r.[CategoryId] = c.[CategoryId] "
            + "WHERE b.[BookingStatus] = 'Not yet' "
            + "ORDER BY b.[CheckInDate] ASC;";

    try (PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Booking booking = new Booking();
            booking.setBookingId(rs.getInt("BookingId"));

            Account account = new Account();
            account.setAccountId(rs.getInt("AccountId"));
            account.setFullName(rs.getString("FullName"));
            booking.setAccount(account);

            Room room = new Room();
            room.setId(rs.getInt("RoomId"));
            room.setRoomNumber(rs.getString("RoomNumber"));

            Category category = new Category();
            category.setCategoryName(rs.getString("CategoryName"));
            category.setPricePerNight(rs.getDouble("PricePerNight"));
            room.setCategory(category);

            booking.setRoom(room);

            Timestamp checkInTimestamp = rs.getTimestamp("CheckInDate");
            Timestamp checkOutTimestamp = rs.getTimestamp("CheckOutDate");
            Timestamp bookingTimestamp = rs.getTimestamp("BookingDate");

            booking.setCheckInDate(checkInTimestamp != null ? checkInTimestamp.toLocalDateTime() : null);
            booking.setCheckOutDate(checkOutTimestamp != null ? checkOutTimestamp.toLocalDateTime() : null);
            booking.setBookingDate(bookingTimestamp != null ? bookingTimestamp.toLocalDateTime() : null);
            booking.setTotalPrice(rs.getBigDecimal("TotalPrice"));
            booking.setBookingStatus(rs.getString("BookingStatus"));
            booking.setNote(rs.getString("Note"));

            list.add(booking);
        }
    } catch (Exception e) {
        System.out.println("Error getAllNotYetBooking: " + e.getMessage());
    }

    return list;
}


    public List<Booking> getAllOnGoingBooking() {
    List<Booking> list = new ArrayList<>();
    String sql = "SELECT b.[BookingId], b.[AccountId], a.[FullName], b.[RoomId], r.[RoomNumber], "
            + "c.[CategoryName], b.[CheckInDate], b.[CheckOutDate], COALESCE(b.[TotalPrice], 0) AS [TotalPrice], "
            + "b.[BookingStatus], b.[BookingDate], b.[Note], c.[PricePerNight] "
            + "FROM [dbo].[Booking] b "
            + "JOIN [dbo].[Account] a ON b.[AccountId] = a.[AccountId] "
            + "JOIN [dbo].[Room] r ON b.[RoomId] = r.[RoomId] "
            + "JOIN [dbo].[Category] c ON r.[CategoryId] = c.[CategoryId] "
            + "WHERE b.[BookingStatus] = 'On Going' "
            + "ORDER BY b.[CheckOutDate] ASC";

    try (PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Booking booking = new Booking();
            booking.setBookingId(rs.getInt("BookingId"));

            Account account = new Account();
            account.setAccountId(rs.getInt("AccountId"));
            account.setFullName(rs.getString("FullName"));
            booking.setAccount(account);

            Room room = new Room();
            room.setId(rs.getInt("RoomId"));
            room.setRoomNumber(rs.getString("RoomNumber"));

            Category category = new Category();
            category.setCategoryName(rs.getString("CategoryName"));
            category.setPricePerNight(rs.getDouble("PricePerNight"));
            room.setCategory(category);

            booking.setRoom(room);

            Timestamp checkInTimestamp = rs.getTimestamp("CheckInDate");
            Timestamp checkOutTimestamp = rs.getTimestamp("CheckOutDate");
            Timestamp bookingTimestamp = rs.getTimestamp("BookingDate");

            booking.setCheckInDate(checkInTimestamp != null ? checkInTimestamp.toLocalDateTime() : null);
            booking.setCheckOutDate(checkOutTimestamp != null ? checkOutTimestamp.toLocalDateTime() : null);
            booking.setBookingDate(bookingTimestamp != null ? bookingTimestamp.toLocalDateTime() : null);
            booking.setTotalPrice(rs.getBigDecimal("TotalPrice"));
            booking.setBookingStatus(rs.getString("BookingStatus"));
            booking.setNote(rs.getString("Note"));

            list.add(booking);
        }
    } catch (Exception e) {
        System.out.println("Error getAllOnGoingBooking: " + e.getMessage());
    }

    return list;
}

    public void acceptBooking(int bookingId, String bookingStatus) {
        String sql = "UPDATE [dbo].[Booking]\n"
                + "SET [BookingStatus] = ?\n"
                + "WHERE [BookingId] = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bookingStatus);
            ps.setInt(2, bookingId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeStatusDone(String id, String totalPrice) {
        String sql = "UPDATE [dbo].[Booking] SET [BookingStatus] = 'Done', [TotalPrice] = ? WHERE [BookingId] = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, totalPrice);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeStatusOnGoing(int id) {
        String sql = "UPDATE [dbo].[Booking] SET [BookingStatus] = 'On Going' WHERE [BookingId] = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeStatusCancel(int id) {
        String sql = "UPDATE [dbo].[Booking] SET [BookingStatus] = 'Cancel' WHERE [BookingId] = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (accountId, roomId, checkInDate, checkOutDate, totalPrice, bookingStatus, bookingDate, note, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getAccount().getAccountId());
            pstmt.setInt(2, booking.getRoom().getId());
            pstmt.setTimestamp(3, Timestamp.valueOf(booking.getCheckInDate()));
            pstmt.setTimestamp(4, Timestamp.valueOf(booking.getCheckOutDate()));
            pstmt.setBigDecimal(5, booking.getTotalPrice());
            pstmt.setString(6, "Pending");
            pstmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(8, booking.getNote());
            pstmt.setString(9, "Active");

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                booking.setBookingId(rs.getInt(1));
            }
        }
    }

    public void updateBookingStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE bookings SET bookingStatus = ? WHERE bookingId = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, bookingId);

            pstmt.executeUpdate();
        }
    }

    
}
