<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit User</title>
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
        <div class="container mt-5 mb-5">
            <div class="row justify-content-center centered-form">
                <div class="col-md-6 mt-3">
                    <h2>Edit User</h2>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${not empty userDto}">
                        <form action=userEdit method="post">
                            <input type="hidden" name="id" value="${userDto.id}">
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" value="${userDto.email}" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="password">Password</label>
                                <input type="password" class="form-control" id="password" name="password" min="5" max="10" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="state">State</label>
                                <select class="form-control" id="state" name="state" required>
                                    <option value="true" ${userDto.state ? 'selected' : ''}>Active</option>
                                    <option value="false" ${!userDto.state ? 'selected' : ''}>Inactive</option>
                                </select>
                            </div>
                            <c:if test="${not empty errorMessage}">
                                <div class='alert alert-danger mt-2'>${errorMessage}</div>
                            </c:if>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                            <a href="users" class="btn btn-secondary">Cancel</a>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
