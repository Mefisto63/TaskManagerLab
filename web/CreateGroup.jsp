<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 22.03.15
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ex" uri="/WEB-INF/mytag.tld" %>
<html>
<script src="scripts/ajax.js"></script>
<head>
    <title></title>
</head>
<body style="background-color: #f5f5ea" onload="f()">
<form method="get" action="group">
    <table border="0">
        <tr>
            <td>Name:
            </td>
            <td >
                <input type="text" name="name" id="name" size="30">
            </td>
        </tr>
        <tr>
            <td>Select parent group group:</td>
            <td><ex:selectGroup id='<%=String.valueOf(request.getSession(true).getAttribute("idUser"))%>'></ex:selectGroup></td>
        </tr>
        <tr >
            <td colspan="2" align="center">
                <input type="submit" id="ok" value="Ok">
                <a href="tasksPage.jsp"><input type="button" value="Back"></a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
