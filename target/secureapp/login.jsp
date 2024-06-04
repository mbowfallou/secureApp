<%@page import="com.samanecorp.secureapp.dto.AccountUserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5 mb-5">
        <div class="row justify-content-center centered-form">
            <div class="col-md-6 mt-3">
                <h2>Login</h2>
                <form action="login" method="post">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="form-group mb-3">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
			        <%
			            //HttpSession session = request.getSession(false);
			            if (request.getAttribute("error") != null) {
		                    out.print("<div class='alert alert-danger mt-2'>" + request.getAttribute("error") + "</div>");
			            }
			        %>
                    <button type="submit" class="btn btn-primary">Login</button>
                </form>
                <p>Don't have an account? <a href="signup">Sign Up</a></p>
            </div>
        </div>
    </div>
</body>
</html>
