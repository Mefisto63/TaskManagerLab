<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 07.03.15
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ex" uri="/WEB-INF/mytag.tld" %>
<html>
<head>
    <script src="scripts/registrationCheck.js"></script>
    <title>Registration</title>
</head>
<body style="background-color: #f5f5ea" onload="getMessage()">
<form method="get" action="reg">
    <table border="0">
        <tr>
            <td>Login:
            </td>
            <td >
                <input type="text" name="login" id="login" size="30">
            </td>
        </tr>
        <tr >
            <td>Password:
            </td>
            <td>
                <input type="password" name="pass" id="pass" size="30">
            </td>
        </tr>
        <tr >
            <td>Confirm password:
            </td>
            <td>
                <input type="password" name="confPass" id="confPass" size="30" onkeyup="checkPass(); return false;">
            </td>
        </tr>
        <tr>
            <td>Select group:</td>
            <td><ex:selectGroup id='<%=String.valueOf(request.getSession(true).getAttribute("idUser"))%>'></ex:selectGroup></td>
        </tr>
        <tr >
            <td colspan="2" align="center">
                <input type="submit" id="ok" value="Ok" disabled>
                <a href="/myApp/tasks"><input type="button" value="Back"></a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
