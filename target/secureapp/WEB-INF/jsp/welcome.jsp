<%@ page import="com.samanecorp.secureapp.dto.AccountUserDto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bienvenue</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="/secureapp">SecureApp</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="logout">Logout</a>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <%
            //HttpSession session = request.getSession(false);
            if (session != null) {
                AccountUserDto user = (AccountUserDto) session.getAttribute("user");
                if (user != null) {
                    out.print("<h2>Welcome, " + user.getEmail() + "</h2>");
                }
            }
        %>
        <p>You have successfully logged in.</p>
        
        <c:choose>
		    <c:when test="${not empty sessionScope.user}">
		        <h2>Welcome, ${sessionScope.user.email}</h2>
		    </c:when>
		    <c:otherwise>
		        <h2>Welcome, Guest</h2>
		    </c:otherwise>
		</c:choose>
		<h2>Welcome, ${sessionScope.user.email}</h2>
		<h2>Welcome, <c:out value="${sessionScope.user.email}" /></h2>
		
    </div>
</body>
</html>
