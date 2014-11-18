<html>
<body>
    <form action="../mvc/submitData" method="post">
        <label path="login">Login:</label>
        <input type="text" name="login"/><br/>

        <label path="name">Name:</label>
        <input type="text" name="name"/><br/>

        <p><input type="submit" value="Add user"/></p>
    </form>

    <form action="../mvc/getAllUsers" method="get">
        <p><input type="submit" value="Show user list"/></p>
    </form>
</body>
</html>