USE [HotelManagement]
GO

/***************************************
 * 1. Thêm dữ liệu cho bảng Role
 ***************************************/
INSERT INTO [dbo].[Role] (RoleName) VALUES ('Owner');
INSERT INTO [dbo].[Role] (RoleName) VALUES ('Staff');
INSERT INTO [dbo].[Role] (RoleName) VALUES ('Customer');


/***************************************
 * 2. Thêm dữ liệu cho bảng Category (cho phòng)
 ***************************************/
INSERT INTO [dbo].[Category]
	(CategoryId, CategoryName, Capacity, PricePerNight, Description, Image, Size, Bed)
VALUES
	(1, 'Standard', 2, 100.000, 'Standard room', 'standard.jpg', 20.0, 1);


/***************************************
 * 3. Thêm dữ liệu cho bảng Account
 *    - Sử dụng RoleId theo thứ tự: Owner = 1, Staff = 2, Customer = 3
 *    - Status: Active hoặc Inactive
 ***************************************/
INSERT INTO [dbo].[Account]
	(RoleId, FullName, Email, Password, Phone, Address, CreatedDate, Status, Image)
VALUES
	(1, 'Alice Owner', 'alice.owner@example.com', 'password123', '1234567890', '123 Owner Street', GETDATE(), 'Active', 'alice.jpg');

INSERT INTO [dbo].[Account]
	(RoleId, FullName, Email, Password, Phone, Address, CreatedDate, Status, Image)
VALUES
	(2, 'Bob Staff', 'bob.staff@example.com', 'password123', '0987654321', '456 Staff Avenue', GETDATE(), 'Active', 'bob.jpg');

INSERT INTO [dbo].[Account]
	(RoleId, FullName, Email, Password, Phone, Address, CreatedDate, Status, Image)
VALUES
	(3, 'Charlie Customer', 'charlie.customer@example.com', 'password123', '1112223333', '789 Customer Road', GETDATE(), 'Inactive', 'charlie.jpg');


/***************************************
 * 4. Thêm dữ liệu cho bảng Room
 *    - RoomNumber: số phòng mẫu
 *    - CategoryId: tham chiếu Category đã thêm ở trên (ở đây dùng 1)
 *    - Status: Available, Maintenance, Unavailable
 ***************************************/
INSERT INTO [dbo].[Room]
	(RoomNumber, CategoryId, Status, Image)
VALUES
	('101', 1, 'Available', 'room101.jpg');

INSERT INTO [dbo].[Room]
	(RoomNumber, CategoryId, Status, Image)
VALUES
	('102', 1, 'Maintenance', 'room102.jpg');

INSERT INTO [dbo].[Room]
	(RoomNumber, CategoryId, Status, Image)
VALUES
	('103', 1, 'Unavailable', 'room103.jpg');


/***************************************
 * 5. Thêm dữ liệu cho bảng Booking
 *    - AccountId: sử dụng tài khoản Customer (giả sử có AccountId = 3)
 *    - RoomId: tham chiếu các phòng vừa tạo
 *    - BookingStatus: sử dụng các giá trị: Not Yet, On going, Cancel, Done, Late
 *    - Các trường ngày dùng GETDATE() hoặc giá trị cố định theo ví dụ
 ***************************************/
-- Booking với trạng thái 'Not Yet'
INSERT INTO [dbo].[Booking]
	(AccountId, RoomId, CheckInDate, CheckOutDate, TotalPrice, BookingStatus, BookingDate, Note)
VALUES
	(3, 1, '2025-03-10', '2025-03-12', 200.00, 'Not Yet', GETDATE(), 'Booking chưa bắt đầu');

-- Booking với trạng thái 'On going'
INSERT INTO [dbo].[Booking]
	(AccountId, RoomId, CheckInDate, CheckOutDate, TotalPrice, BookingStatus, BookingDate, Note)
VALUES
	(3, 2, '2025-03-05', '2025-03-08', 300.00, 'On going', GETDATE(), 'Booking đang diễn ra');

-- Booking với trạng thái 'Cancel'
INSERT INTO [dbo].[Booking]
	(AccountId, RoomId, CheckInDate, CheckOutDate, TotalPrice, BookingStatus, BookingDate, Note)
VALUES
	(3, 3, '2025-02-20', '2025-02-25', 500.00, 'Cancel', GETDATE(), 'Booking đã hủy');

-- Booking với trạng thái 'Done'
INSERT INTO [dbo].[Booking]
	(AccountId, RoomId, CheckInDate, CheckOutDate, TotalPrice, BookingStatus, BookingDate, Note)
VALUES
	(3, 1, '2025-01-15', '2025-01-20', 600.00, 'Done', GETDATE(), 'Booking đã hoàn thành');

-- Booking với trạng thái 'Late'
INSERT INTO [dbo].[Booking]
	(AccountId, RoomId, CheckInDate, CheckOutDate, TotalPrice, BookingStatus, BookingDate, Note)
VALUES
	(3, 2, '2025-03-01', '2025-03-03', 250.00, 'Late', GETDATE(), 'Booking trễ');
