<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <a href=".">to main page</a><br>
        <form action="createTickets" method="POST">
            <table>
                <tr>
                    <td>Title: </td>
                    <td><input type="text" name="title"></td>
                </tr>
                <tr>
                    <td>Date: </td>
                    <td><input type="text" name="date"></td></td>
                </tr>
                <tr>
                    <td>Cost: </td>
                    <td><input type="text" name="cost"></td></td>
                </tr>
                <tr>
                    <td>Locations: </td>
                    <td><input type="text" name="locations"></td>
                </tr>
            </table>
            <input type="submit" name="add">
        </form>
    </body>
</html>