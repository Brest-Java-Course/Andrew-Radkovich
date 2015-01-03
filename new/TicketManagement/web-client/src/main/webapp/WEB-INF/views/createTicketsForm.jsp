<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <a href=".">to main page</a><br>
        <form action="createTickets" method="POST" onsubmit="return checkForm(this);">
            <table>
                <tr>
                    <td>Title: </td>
                    <td><input type="text"  required name="title"></td>
                </tr>
                <tr>
                    <td>Date: </td>
                    <td><input type="text" required pattern="\d{4}-\d{1,2}-\d{1,2}"  placeholder="yyyy-mm-dd" name="date"></td></td>
                </tr>
                <tr>
                    <td>Cost: </td>
                    <td><input type="text"  required name="cost"></td></td>
                </tr>
                <tr>
                    <td>Locations: </td>
                    <td><input type="text"  required name="locations"></td>
                </tr>
            </table>
            <input type="submit" name="add">
        </form>
        <script>
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

            return isValidDate(form.date.value);
        }
        </script>
    </body>
</html>