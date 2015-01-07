<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
 <head>
  <meta charset="utf-8">
  <title>HOME</title>
<link rel="stylesheet" type="text/css" href="resources/css/background-gradient.css">
 </head>
<body class="gradient">
    <h2>Main page</h2>
<!--    <form action="searchByPid" method="GET">
        <label><input type="text" name="pid"></label>
        <input type="submit" name="search"/>
    </form>-->
    <ul>
        <table border="1">
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Identification number</td>
                <td></td>
            </tr>
            <c:forEach items="${Customers}" var="customer">
                <tr>
                    <td>${customer.customerId}</td>
                    <td>${customer.name}</td>
                    <td>${customer.identificationNumber}</td>
                    <td>
                        <a href='<spring:url value="/edit" ><spring:param name="id" value="${customer.customerId}" /> </spring:url>'>edit</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </ul>
    <h3>Add Customer</h3>
    <form action="addCustomerForm" method="GET">
        <input type="submit" value="Add Customer"/>
    </form>

    <h3>Create tickets</h3>
    <form action="createTicketsForm" method="GET">
        <input type="submit" value="Create tickets"/>
    </form>

    <h3>New order</h3>
    <form action="newOrder" method="GET">
        <input type="submit" value="New order"/>
    </form>
</body>
</html>