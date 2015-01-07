<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <h2>Ticket with such parameters is present in database</h2>
        <button onclick="goBack()">Go Back</button>

        <script>
        function goBack() {
            window.history.back()
        }
        </script>
<!--	    <a href='<spring:url value="/addCustomerForm" ></spring:url>'>back</a> -->
    </body>
</html>