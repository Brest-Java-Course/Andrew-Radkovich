<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
 <head>
  <meta charset="utf-8">
  <title>NEW ORDER</title>
<link rel="stylesheet" type="text/css" href="resources/css/background-gradient.css">
 </head>
    <body class="gradient">
        <h2>Order page</h2>
        <a href=".">to main page</a></br></br>
        <form action="filterByDateAndPid" method="GET" onsubmit="return checkForm(this);">
	    <label>PID: <input type="text" name="pid"></label>
	    <p><label>First Date: <input type="text" size="12" pattern="^\d+$|\d{4}-\d{1,2}-\d{1,2}" placeholder="yyyy-mm-dd" name="dateFirst"></label></p>
	    <p><label>Last Date: <input type="text" size="12" pattern="^\d+$|\d{4}-\d{1,2}-\d{1,2}"  placeholder="yyyy-mm-dd" name="dateLast"></label></p>
	    <p><input type="submit"></p>
        </form>
        <form action="placeOrder" method="GET" onsubmit="return isValuesChecked(this);">
            <ul>
                <c:choose>
                    <c:when test="${(customers.get(0)) == null}">
                        <h2>No customers found</h2>
                    </c:when>

                    <c:otherwise>
                        <table border="1">
                            <tr>
                                <td>Id</td>
                                <td>Name</td>
                                <td>Identification number</td>
                                <td>Count</td>
                                <td></td>
                            </tr>
                            <c:forEach items="${customers}" var="customer" varStatus="status">
                                <tr>
                                    <td>${customer.customerId}</td>
                                    <td>${customer.name}</td>
                                    <td>${customer.identificationNumber}</td>
                                    <td>${counts[status.index]}</td>
                                    <td>
                                        <input type="radio" name="customerId" value="${customer.customerId}">
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </ul>
            <ul>
            <c:choose>
                <c:when test="${(tickets.size()) == 0}">
                    <h3>No free tickets found</h3>
                </c:when>

                <c:otherwise>
                    <h3>Not taken tickets</h3>
                    <table border="1">
                        <tr>
                            <td>Title</td>
                            <td>Date</td>
                            <td>Cost</td>
                            <td>Location</td>
                        </tr>
                        <c:forEach items="${tickets}" var="ticket">
                            <tr>
                                <td>${ticket.title}</td>
                                <td>${ticket.date}</td>
                                <td>${ticket.cost}</td>
                                <td>${ticket.location}</td>
                                <td><input type="checkbox" name="ticketIdList" value="${ticket.ticketId}"</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
             </c:choose>
            </ul>
            <c:choose>
		    <c:when test="${(tickets.size() != 0) and ((customers.get(0)) != null)}">
		        <input type="submit" value="Place order">
		    </c:when>
             </c:choose>
        </form>
        <script src="resources/js/checkRadioAndBoxChosen.js"></script>
        <script src="resources/js/dateValidation.js"></script>
        <script>
            function checkForm(form) {

                if("" == form.dateFirst.value || "" == form.dateLast.value) {
                    form.dateFirst.value = form.dateLast.value = "";
                    return true;
                }
                return isValidDate(form.dateFirst.value) && isValidDate(form.dateLast.value);
            }
        </script>
    </body>
</html>