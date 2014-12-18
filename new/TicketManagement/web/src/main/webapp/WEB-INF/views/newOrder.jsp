<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <h2>Order page</h2>
        <a href=".">to main page</a><br>
        <form action="filterByDateAndPid" method="GET">
            <label>PID: <input type="text" name="pid"></label>
            <label>Date: <input type="text" name="date" id="dateLineEdit"/></label>
            <button id="dateSubmitBtn" >Filter</button>
        </form>
        <form action="placeOrder" method="GET">
            <ul>
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
            </ul>
            <h3>Not taken tickets</h3>
            <ul>
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
            </ul>
            <input type="submit" value="Place order">
        </form>
        <script src="<c:url value="/WEB-INF/classes/js/jquery-1.11.1.js" />"></script>
        <script>
        $(document).ready( function(){
            $('#dateSubmitBtn').click( function() {
                console.log('button click handler');
                if( isValidDate($('#dateLineEdit').val()) ) {
                    alert('Valid date');
                    return true;
                }
                else {
                    alert('Invalid date');
                    return false;
                }
            });
            )};

            function isValidDate(str){
                if(str=="" || str==null){return false;}
                var m = str.match(/(\d{4})-(\d{2})-(\d{2})/);
                if( m === null || typeof m !== 'object'){return false;}
                if (typeof m !== 'object' && m !== null && m.size!==3){return false;}
                var ret = true;
                var thisYear = new Date().getFullYear(); //YEAR NOW
                var minYear = 1993;
                if( (m[1].length < 4) || m[1] < minYear || m[1] > thisYear){ret = false;}
                if( (m[1].length < 2) || m[2] < 1 || m[2] > 12){ret = false;}
                if( (m[1].length < 2) || m[3] < 1 || m[3] > 31){ret = false;}
                return ret;
            }
        </script>
    </body>
</html>