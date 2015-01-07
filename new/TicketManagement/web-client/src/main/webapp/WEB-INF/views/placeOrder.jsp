<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
 <head>
  <meta charset="utf-8">
  <title>PLACE ORDER</title>
<link rel="stylesheet" type="text/css" href="resources/css/background-gradient.css">
 </head>
    <body class="gradient">
        <h2>Customer info</h2>
        <a href=".">to main page</a><br>
        <ul>
            <li>Id: <input type="text" name="id" value="${customer.customerId}" readonly="readonly"/></li>
            <li>Name: <input type="text" name="name" value="${customer.name}" /></li>
            <li>Number: <input type="text" name="pid" value="${customer.identificationNumber}" /></li>
        </ul>
        <h2>Total cost : "${totalCost}"</h2>
        <h3>Tickets of customer</h3>
        <ul>
            <table border="1">
                <tr>
                    <td>Title</td>
                    <td>Date</td>
                    <td>Cost</td>
                    <td>Location</td>
                </tr>
                <c:forEach items="${ticketsOfCustomer}" var="ticket">
                    <tr>
                        <td>${ticket.title}</td>
                        <td>${ticket.date}</td>
                        <td>${ticket.cost}</td>
                        <td>${ticket.location}</td>
                    </tr>
                </c:forEach>
            </table>
        </ul>
    </body>
</html>