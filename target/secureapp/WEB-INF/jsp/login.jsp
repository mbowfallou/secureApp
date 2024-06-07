<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Connexion</title>
        <link rel="stylesheet" type="text/css" href="/WEB-INF/css/style.css">
    </head>
    <body>
        <h2>Connexion sss</h2>
        <form action="login" method="post">
            <label>Nom d'utilisateur :</label>
            <input type="text" name="username" required>
            <label>Mot de passe :</label>
            <input type="password" name="password" required>
            <input type="submit" value="Se connecter">
        </form>
    </body>
</html>
