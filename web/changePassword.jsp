<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #6a11cb, #2575fc);
            color: #fff;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .registration-form {
            background: #ffffff;
            color: #000;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }
        .form-control:focus {
            box-shadow: 0 0 10px rgba(38, 143, 255, 0.5);
            border-color: #268fff;
        }
        .form-header {
            text-align: center;
            margin-bottom: 1.5rem;
        }
        .form-header h2 {
            font-weight: bold;
        }
        .form-header p {
            font-size: 0.9rem;
            color: #6c757d;
        }
        .btn-custom {
            background: linear-gradient(135deg, #6a11cb, #2575fc);
            color: #fff;
            border: none;
            transition: background 0.3s ease;
        }
        .btn-custom:hover {
            background: linear-gradient(135deg, #2575fc, #6a11cb);
        }
        .text-muted {
            font-size: 0.8rem;
            text-align: center;
            margin-top: 1rem;
        }
    </style>
</head>
<body>
    <div class="registration-form">
        <div class="form-header">
            <h2>Change Password</h2>
            <p>Change your password every week to keep your account safe!</p>
        </div>
        <form action="ChangePassword" method="post">
            <div class="mb-3">
                <label for="password" class="form-label">Password:</label>
                <input type="password" class="form-control"  name="oldp" placeholder="Enter Password" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">New Password</label>
                <input type="password" class="form-control"  name="newp" placeholder="New Password" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Re-enter Password</label>
                <input type="password" class="form-control"  name="renewp" placeholder="Re-enter Password" required>
            </div>
            <button type="submit" class="btn btn-custom w-100">Change Password</button>
            <div class="mt-3">
            </div>
            <div>
                <p>${err}</p>
            </div>
        </form>
        <div class="text-muted">
            <a href="profile.jsp" class="text-primary">Go Back</a>
        </div>
        
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
