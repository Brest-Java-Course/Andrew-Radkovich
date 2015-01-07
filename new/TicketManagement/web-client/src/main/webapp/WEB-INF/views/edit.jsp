<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
 <head>
  <meta charset="utf-8">
  <title>EDIT CUSTOMER</title>
<link rel="stylesheet" type="text/css" href="resources/css/background-gradient.css">
 </head>
    <body class="gradient">
        <script src="resources/js/jquery-1.11.1.js"></script>
        <h2>Customer info</h2>
        <a href=".">to main page</a><br>
        <form action="updateCustomer" method="POST" modelAttribute="customer"  onsubmit="return checkCustomerAvailability();">
            <ul>
                <li>Id: <input type="text" name="id" value="${customer.customerId}" readonly="readonly" id="customerId"/></li>
                <li>Name: <input type="text" name="name" value="${customer.name}" required /></li>
                <li>Number: <input type="text" name="pid" value="${customer.identificationNumber}" id="personalNumber" required /></li>
            </ul>
            <input type="submit" name="update" />
        </form>
        <form action="removeCustomer" method="POST">
		<button name="customerId" value="${customer.customerId}">remove</button>
        </form>
    <c:choose>
        <c:when test="${ticketsOfCustomer.size() != 0}">
        	<h3>Tickets of customer</h3>
            <h3>Total cost: ${totalCost}</h3>
		<h3>Amount of tickets: ${ticketsOfCustomer.size()}</h3>
        </c:when>
    </c:choose>
        <ul>
            <c:choose>
                <c:when test="${ticketsOfCustomer.size() != 0}">
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
                </c:when>
            </c:choose>
            </ul>
    </body>
<script>
    function checkCustomerAvailability() {

        var personalNumber = $('#personalNumber').val();
        var customerId = $('#customerId').val();
        //console.log(personalNumber);

        var checkByNumberRequest = null;
        var getByIdRequest = null;
        var exists;
        var pidUpdated = true;

        getByIdRequest = new XMLHttpRequest();
        checkByNumberRequest = new XMLHttpRequest();

        getByIdRequest.open("GET", "http://localhost:8080/web-1.0-SNAPSHOT/rest/customer/" + customerId, false);
        getByIdRequest.onreadystatechange = function() {

            if (getByIdRequest.readyState == 4) {
                if(getByIdRequest.status == 200) {
                    var resp = jQuery.parseJSON( getByIdRequest.responseText );
                    if( resp.identificationNumber == personalNumber) {
                        //alert(resp.identificationNumber);
                        pidUpdated = false;
                    }
                }
            }
        };
        getByIdRequest.send();

        if(!pidUpdated) {
            return true;
        }

        checkByNumberRequest.open("GET", "http://localhost:8080/web-1.0-SNAPSHOT/rest/customer/check/" + personalNumber, false);
        checkByNumberRequest.onreadystatechange = function() {

            if (checkByNumberRequest.readyState == 4) {
                if(checkByNumberRequest.status == 200) {
                    var resp = checkByNumberRequest.responseText;
                    if(resp === 'false') {
                        exists = true;
                        console.log('false stmt: ' + exists);
                    }
                    else if(resp === 'true') {
                        exists = false;
                        alert("Customer with such personal number already exists");
                        console.log('true stmt: ' + exists);
                    }
                }
            }
        };
        checkByNumberRequest.send();
        console.log('exists: ' + exists);
        return exists;
    }
</script>
</html>