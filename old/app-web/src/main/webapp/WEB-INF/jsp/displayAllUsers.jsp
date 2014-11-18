<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <title>User list</title>
    </head>
    <body>
        <table>
            <c:forEach users="${userList}" var="user">
                ${user.login}<br>
            </c:forEach>
        </table>
    </body>
</html>