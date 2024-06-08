<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User Details</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="/secureapp">SecureApp</a>
            <a class="navbar-brand" href="welcome">Welcome</a>
            <a class="navbar-brand" href="users">Users</a>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container col-md-6 mt-5 mb-5">
            <h2>User Details</h2>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty updateState}">
                <div class='alert alert-danger mt-2'>${updateState}</div>
            </c:if>
            <c:if test="${not empty userDto}">
                <table class="table table-bordered">
                    <tr>
                        <th>ID</th>
                        <td>${userDto.id}</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>${userDto.email}</td>
                    </tr>
                    <tr>
                        <th>State</th>
                        <td>${userDto.state ? 'Active' : 'Inactive'}</td>
                    </tr>
                </table>
                <c:if test="${not empty errorMessage}">
                    <div class='alert alert-danger mt-2'>${errorMessage}</div>
                </c:if>
                <a href="users" class="btn btn-secondary">Back to List</a>
                <a href="userEdit?id=${userDto.id}" class="btn btn-warning">Edit</a>
            </c:if>
        </div>
    </body>
</html>
