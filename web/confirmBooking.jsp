<style>
    body {
        background-color: #f8f9fa;
    }
    .container {
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
    }
    h2 {
        color: #007bff;
        font-weight: bold;
    }
    h4 {
        color: #343a40;
    }
    .btn-success {
        padding: 10px 20px;
        font-size: 18px;
    }
    .form-group textarea {
        resize: none;
    }
</style>

<div class="container mt-5">
    <h2 class="text-center mb-4">Booking Details</h2>
    <form id="bookingForm" action="payment" method="post">
        <div class="row">
            <!-- Room Information -->
            <div class="col-md-6">
                <h4>Room Information</h4>
                <p><strong>Category:</strong> ${categoryName}</p>
                <p><strong>Price per Night:</strong> <span id="pricePerNight">${pricePerNight}</span> VND</p>
                <p><strong>Check-in Date:</strong> ${param.checkinDate}</p>
                <p><strong>Check-out Date:</strong> ${param.checkoutDate}</p>
                <p><strong>Total Nights:</strong> <span id="totalNights">${totalNights}</span></p>
                <p><strong>Total Price:</strong> <span id="totalPrice">${totalNights * pricePerNight}</span> VND</p>
                
                <input type="hidden" name="checkinDate" value="${param.checkinDate}">
                <input type="hidden" name="checkoutDate" value="${param.checkoutDate}">
                <input type="hidden" name="totalPrice" value="${totalNights * pricePerNight}">
            </div>
            
            <!-- Account Information -->
            <div class="col-md-6">
                <h4>Your Information</h4>
                <p><strong>Full Name:</strong> ${account.fullName}</p>
                <p><strong>Email:</strong> ${account.email}</p>
                <p><strong>Phone:</strong> ${account.phone}</p>
            </div>
        </div>
        
        <!-- Additional Note -->
        <div class="form-group mt-3">
            <label for="note">Additional Note</label>
            <textarea name="note" id="note" class="form-control" rows="3" placeholder="Enter any additional requests..."></textarea>
        </div>
        
        <!-- Confirm Booking Button -->
        <div class="text-center mt-4">
            <button type="submit" class="btn btn-success">Confirm Booking</button>
        </div>
    </form>
</div>