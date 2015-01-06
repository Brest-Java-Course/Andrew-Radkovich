<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <script src="resources/js/jquery-1.11.1.js"></script>
        <a href=".">to main page</a><br>
        <form action="addCustomer" method="POST" onsubmit="return checkCustomerAvailability();">
            <table>
                <tr>
                    <td>Name</td>
                    <td><input type="text" required name="name"></td>
                </tr>
                <tr>
                    <td>PID</td>
                    <td><input type="text" required name="pid" id="personalNumber"></td>
                </tr>
            </table>
            <input type="submit" name="add">
        </form>
    </body>
<script>
    function checkCustomerAvailability() {

        var personalNumber = $('#personalNumber').val();
        //console.log(personalNumber);

        var xmlHttp = null;
        var exists;

        xmlHttp = new XMLHttpRequest();
        xmlHttp.open("GET", "http://localhost:8080/web-1.0-SNAPSHOT/rest/customer/check/" + personalNumber, false);
        xmlHttp.onreadystatechange = function() {

            if (xmlHttp.readyState == 4) {
                if(xmlHttp.status == 200) {
                    var resp = xmlHttp.responseText;
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
        xmlHttp.send();
        console.log('exists: ' + exists);
        return exists;
    }
</script>
</html>