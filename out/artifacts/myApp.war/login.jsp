<%@ page import="taskManager.connection.TMConnection" %>
<%@ page import="taskManager.connection.TMConnectionPool" %>
<%@ page import="taskManager.controller.Controller" %>
<%@ page import="taskManager.idGenerator.IdGenerator" %>
<%@ page import="taskManager.idGenerator.TimeIdGenerator" %>
<%@ page import="taskManager.manager.LifeCycleManager" %>
<%@ page import="taskManager.manager.dbManager.DBBasedLifeCycleManager" %>
<%@ page import="taskManager.manager.dbManager.actions.*" %>
<%@ page import="taskManager.requests.factory.Factories" %>
<%@ page import="taskManager.requests.factory.genericFactory.*" %>
<%@ page import="taskManager.tasks.AlarmClock" %>
<%@ page import="taskManager.tasks.BirthdayReminder" %>
<%@ page import="taskManager.tasks.Notification" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="beans.CreateUserEJB" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.ejb.EJB" %>
<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 01.03.15
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="scripts/registrationCheck.js"></script>
    <title>Authorization</title>
</head>
<body style="background-color: #f5f5ea" onload="getMessage()">
<form method="get" action="auth">
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
            <td colspan="2" align="center">
                <input type="submit" value="Enter">
                <%--<a href="registration.jsp"><input type="button" value="Sing up"></a>--%>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
