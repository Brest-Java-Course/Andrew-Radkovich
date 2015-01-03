<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <body>
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
                    <c:when test="${(customers.size()) == 0}">
                        <h2>No customers found</h2>
                    </c:when>

                    <c:otherwise>
                        <table border="1">
                            <tr>
                                <td>Id</td>
                                <td>Name</td>
                                <td>Identification number</td>
                                <td></td>
                            </tr>
                            <c:forEach items="${customers}" var="customer">
                                <tr>
                                    <td>${customer.customerId}</td>
                                    <td>${customer.name}</td>
                                    <td>${customer.identificationNumber}</td>
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
		    <c:when test="${(tickets.size() != 0) and (customers.size() != 0)}">
		        <input type="submit" value="Place order">
		    </c:when>
             </c:choose>
        </form>
        <script>
        function isValuesChecked(form) {

                var customerRadio = form.customerId;
                var ticketCheckbox = form.ticketIdList;
                var customerRadioChecked = false;
                var ticketCheckboxChecked = false;

                if(customerRadio.checked) {
                customerRadioChecked = true;
                }
                else {
                for(var i = 0; i < customerRadio.length; i++) {
                    if(customerRadio[i].checked) {
                        customerRadioChecked = true;
                        break;
                    }
                }
                }
                if(!customerRadioChecked) {
                alert("Choose customer!");
                return false;
                }

                if(ticketCheckbox.checked) {
                ticketCheckboxChecked = true;
                }
                else {
                for(var i = 0; i < ticketCheckbox.length; i++) {
                    if(ticketCheckbox[i].checked) {
                        ticketCheckboxChecked = true;
                        break;
                    }
                }
                }
                if(!ticketCheckboxChecked) {
                alert("Choose at least one ticket!");
                return false;
                }

                return true;
            }
            function isValidDate(dateToValidate) {

                var re = /(\d{4})-(\d{1,2})-(\d{1,2})/;
                var tokens;
                if(dateToValidate != '') {
                    if(tokens = dateToValidate.match(re)) {

                        var day = tokens[3];
                        var month = tokens[2];
                        var year = tokens[1];

                        if(day < 1 || day > 31) {
                            alert("Invalid value for day: " + day);
                            return false;
                        }
                        if(month < 1 || month > 12) {
                            alert("Invalid value for month: " + month);
                            return false;
                        }
                        if(year < 1902 || year > (new Date()).getFullYear()) {
                            alert("Invalid value for year: " + year + " - must be between 1902 and " + (new Date()).getFullYear());
                            return false;
                        }
                        if(month == 2) {
                            if(year % 4 == 0) {
                                if(day > 29) {
                                    alert("Invalid day for year: " + year + " and february. Must be less or equals than 29");
                                    return false;
                                }
                            }
                            else {
                                if(day > 28) {
                                    alert("Invalid day for year: " + year + " and february. Must be less or equals than 28");
                                    return false;
                                }
                            }
                        }
                    } else {
                        alert("Invalid date format: " + dateToValidate);
                        return false;
                    }
                }
                return true;
            }
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