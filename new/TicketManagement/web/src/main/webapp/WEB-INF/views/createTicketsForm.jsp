<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
 <head>
  <meta charset="utf-8">
  <title>CREATE TICKET</title>
<link rel="stylesheet" type="text/css" href="resources/css/background-gradient.css">
 </head>
    <body class="gradient">
        <script src="resources/js/jquery-1.11.1.js"></script>
        <a href=".">to main page</a><br>
        <form action="createTickets" method="POST" onsubmit="return checkForm(this);">
            <table>
                <tr>
                    <td>Title: </td>
                    <td><input type="text"  required name="title" id="title"></td>
                </tr>
                <tr>
                    <td>Date: </td>
                    <td><input type="text" required pattern="\d{4}-\d{1,2}-\d{1,2}"  placeholder="yyyy-mm-dd" name="date" id="date"></td></td>
                </tr>
                <tr>
                    <td>Cost: </td>
                    <td><input type="text"  required name="cost"></td></td>
                </tr>
                <tr>
                    <td>Locations: </td>
                    <td><input type="text"  required name="locations" id="location"></td>
                </tr>
            </table>
            <input type="submit" name="add">
        </form>

    <script src="resources/js/dateValidation.js"></script>
    <script>
        function checkTicketAvailability() {

            var date = $('#date').val();
            var title = $('#title').val();
            var location = $('#location').val();

            var xmlHttp = null;
            var exists;

            xmlHttp = new XMLHttpRequest();

            var url = "http://localhost:8080/web-1.0-SNAPSHOT/rest/ticket/check/" + date + "/" + title + "/" + location;
            console.log(url);
            xmlHttp.open("GET", url , false);
            xmlHttp.onreadystatechange = function () {

                if (xmlHttp.readyState == 4) {
                    if (xmlHttp.status == 200) {
                        var resp = xmlHttp.responseText;
                        if (resp === 'false') {
                            exists = true;
                            console.log('false stmt: ' + exists);
                        }
                        else if (resp === 'true') {
                            exists = false;
                            alert("One of tickets already exists");
                            console.log('true stmt: ' + exists);
                        }
                    }
                }
            };
            xmlHttp.send();
            if(exists === false) return false;
            else return true;
        }
    </script>
    <script>
        function checkForm(form) {

            if( !isValidDate(form.date.value) ) {
                return false;
            }
            else {
                return checkTicketAvailability();
            }
        }
    </script>
    </body>
</html>