<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5 mb-5">
        <div class="row justify-content-center centered-form">
            <div class="col-md-6 mt-3">
                <h2>Signup</h2>
                <form action=signup method="post">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="form-group mb-3">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <c:if test="${not empty errorMessage}">
                        <div class='alert alert-danger mt-2'>${errorMessage}</div>
                    </c:if>
                    <button type="submit" class="btn btn-primary">Sign up</button>
                </form>
                <p>Already have an account? <a href="login">Login here</a></p>
            </div>
        </div>
    </div>
</body>
</html>
