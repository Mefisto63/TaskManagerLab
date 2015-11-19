<%@ page import="taskManager.controller.Controller" %>
<%@ page import="taskManager.TaskData" %>
<%@ page import="locator.ServiceLocator" %>
<%@ taglib prefix="ex" uri="/WEB-INF/mytag.tld" %>
<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 21.02.15
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<script src="scripts/registrationCheck.js"></script>
<script src="scripts/ajax.js"></script>
<script type="text/javascript">
    /**
     * @return {boolean}
     */
    function UnHide(eThis) {
        if (eThis.innerHTML.charCodeAt(0) == 9658) {
            eThis.innerHTML = '&#9660;';
            eThis.parentNode.parentNode.parentNode.className = '';
        } else {
            eThis.innerHTML = '&#9658;';
            eThis.parentNode.parentNode.parentNode.className = 'cl';
        }
        return false;
    }

</script>

<head>
    <title>Home page</title>
    <link rel="stylesheet" type="text/css" href="style/TreeView.css">

</head>
<body style="background-color: #f5f5ea" onload="f(); getMessage()">
<div>
    <div align="right">
        <a href="/myApp/logout">
            <button style="font-size: large">Logout</button>
        </a>
    </div>
    <div align="left">
        <a href="createTaskPage.jsp">
            <button style="font-size: large">Create new Task</button>
        </a>
        <a href="registration.jsp"><input  style="font-size: large; margin-left: 20pt" type="button" value="Add user"></a>
        <a href="CreateGroup.jsp"><input  style="font-size: large; margin-left: 20pt" type="button" value="Add group"></a>
    </div>
</div>
<hr>
<%--<h3>Your tasks:</h3>--%>
<%--<table border="1" style="border: double; width: 60%">--%>
    <%--<ex:taskTable idUser='<%=request.getSession(true).getAttribute("idUser").toString()%>'/>--%>
<%--</table>--%>
<%--<hr>--%>
<%--<h3>Group's tasks:</h3>--%>
<%--<table border="1" style="border: double; width: 60%">--%>
    <%--<ex:tasksOfGroup idUser='<%=request.getSession(true).getAttribute("idUser").toString()%>'/>--%>
<%--</table>--%>
<ex:treeView idUser='<%=request.getSession(true).getAttribute("idUser").toString()%>'></ex:treeView>
</body>
</html>
