<%@ page import="com.samanecorp.secureapp.dto.AccountUserDto" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Users</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="/secureapp">SecureApp</a>
            <a class="navbar-brand" href="welcome">Welcome</a>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <h2>Welcome, ${sessionScope.user.email}</h2>
                </c:when>
                <c:otherwise>
                    <h2>Welcome, Guest</h2>
                </c:otherwise>
            </c:choose>
            <div class="container mt-5">
                <h2>Users List</h2>
                <c:choose>
                    <c:when test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Email</th>
                                <th>State</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="userDto" items="${usersList}">
                                <tr>
                                    <td>${userDto.id}</td>
                                    <td>${userDto.email}</td>
                                    <td>${userDto.state ? 'Active' : 'Inactive'}</td>
                                    <td>
                                        <a href="details?id=${userDto.id}" class="btn btn-info btn-sm">Details</a>
                                        <a href="edit?id=${userDto.id}" class="btn btn-warning btn-sm">Edit</a>
                                        <c:if test="${userDto.state}">
                                            <a href="delete?id=${userDto.id}" class="btn btn-danger btn-sm">Delete</a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>
