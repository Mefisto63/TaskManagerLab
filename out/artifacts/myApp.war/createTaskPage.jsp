
<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 23.02.15
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="ex" uri="/WEB-INF/mytag.tld" %>
<script>
    function check(){
        var select = document.getElementById("typeTasks");
        var value = select.options[select.selectedIndex].value;
        if (value != "2"){
            document.getElementById("interval").disabled = true;
            document.getElementById("countRepeat").disabled = true;
        } else {
            document.getElementById("interval").disabled = false;
            document.getElementById("countRepeat").disabled = false;
        }
    }

</script>


<html>
<head>
    <title>Create Task</title>
</head>
<body style="background-color: #f5f5ea" onload="check()">
<form method="get" action="create">
<table cellpadding="15">
    <tr>
        <td style="font-size: 15pt" align="right">
            Select type of task
        </td>
        <td>
            <select id="typeTasks" name="type" style="font-size: large" onchange="check()">
                <option value="1">Notification</option>
                <option value="3">Birthday reminder</option>
                <option value="2">Alarm clock</option>
            </select>
        </td>
    </tr>
    <tr>
        <td style="font-size: 15pt" align="right">
            Enter description:
        </td>
        <td>
            <textarea id="desc" name="desc" cols="40" rows="5"></textarea>
        </td>
    </tr>
    <tr>
        <td style="font-size: 15pt" align="right">
            Enter time:
        </td>
        <td>
            <input id="date" name = "date" type="date">
            <input id="time" name = "time" type="time">
        </td>
    </tr>
    <tr>
        <td style="font-size: 15pt" align="right">Select groups which can see this task:</td>
        <td><ex:checkBoxGroup id='<%=request.getSession(true).getAttribute("idUser")%>'></ex:checkBoxGroup></td>
    </tr>
    <tr>
        <td style="font-size: 15pt" align="right">Select users which can see this task:</td>
        <td><ex:checkBoxUsers id='<%=request.getSession(true).getAttribute("idUser")%>'></ex:checkBoxUsers></td>
    </tr>
    <tr>
        <td style="font-size: 15pt" align="right">
            Enter count of repeats:
        </td>
        <td>
            <input id="countRepeat" name="countRepeat" type="number" min="1" max="500">
        </td>
    </tr>
    <tr>
        <td style="font-size: 15pt" align="right">
            Enter interval between repeats:
        </td>
        <td>
            <input id="interval" name="interval" type="number" min="1" max="500">
        </td>
    </tr>
    <tr>
        <td align="right">
            <input type="submit" value="Create" style="font-size: large">
        </td>

    </tr>
</table>
</form>
<div style="position: relative;left: 30%; bottom: 9%"><a href="/myApp/tasks"><button style="font-size: large">Back</button></a></div>
</body>
</html>
