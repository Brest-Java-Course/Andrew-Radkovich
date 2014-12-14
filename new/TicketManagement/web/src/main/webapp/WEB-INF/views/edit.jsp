<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <h2>Customer info</h2>
        <form action="updateCustomer" method="POST" modelAttribute="customer">
            <ul>
                <li>Id: <input type="text" name="id" value="${customer.customerId}" readonly="readonly"/></li>
                <li>Name: <input type="text" name="name" value="${customer.name}" /></li>
                <li>Number: <input type="text" name="pid" value="${customer.identificationNumber}" /></li>
            </ul>
            <input type="submit" name="update" />
        </form>
        <form action="removeCustomer" method="POST">
            <input type="submit" name="customerId" value="${customer.customerId}"/>
        </form>
    </body>
</html>