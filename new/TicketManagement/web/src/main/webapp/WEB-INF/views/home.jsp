<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<body>
    <style type="text/css">
        TH {
            background-color: #199619
        }
    </style>
    <h2>Main page</h2>
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

</body>
</html>