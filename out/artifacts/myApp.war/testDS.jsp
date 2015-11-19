<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="taskManager.controller.Controller" %>
<%@ page import="locator.ServiceLocator" %>
<%@ taglib prefix="ex" uri="/WEB-INF/mytag.tld" %>
<html>
<% Controller controller = ServiceLocator.getInstance().getController();
    long id = 2;
%>
<head>

</head>
<body style="background-color: #f5f5ea">
<form method="get">
    <input type="checkbox" name="a" value="a">
    <input type="checkbox" name="b" value="b">
    <input type="checkbox" name="c" value="c">
    <input type="submit" value="dasd">
</form>
</body>
</html>