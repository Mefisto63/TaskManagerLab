<%@ page import="locator.ServiceLocator" %>
<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 08.03.15
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function check(){
        var value = <%=request.getAttribute("type")%>;/*document.location.href.split("?")[1].split("=")[2];*/
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
    <title>Update Task</title>
</head>
<body style="background-color: #f5f5ea" onload="check()">
<form method="get" action="change">
    <table cellpadding="15">
        <tr>
            <td hidden="true">
                <input type="text" id="id" name="id" value="<%=request.getParameter("id")%>">
                <input type="text" id="type" name="type" value="<%=request.getAttribute("type")%>">
            </td>
            <td style="font-size: 15pt" align="right">
                Enter description:
            </td>
            <td>
                <textarea id="desc" name="desc" cols="40" rows="5"><%=request.getAttribute("desc")%></textarea>
            </td>
        </tr>
        <tr>
            <td style="font-size: 15pt" align="right">
                Enter count of repeats:
            </td>
            <td>
                <input id="countRepeat" name="countRepeat" type="number" min="1" max="500" value="<%=request.getAttribute("repeat")%>">
            </td>
        </tr>
        <tr>
            <td style="font-size: 15pt" align="right">
                Enter interval between repeats:
            </td>
            <td>
                <input id="interval" name="interval" type="number" min="1" max="500" value="<%=request.getAttribute("interval")%>">
            </td>
        </tr>
        <tr>
            <td align="right">
                <input type="submit" value="Set change" style="font-size: large">
            </td>
        </tr>
    </table>
    <input type="text" name="ver" id="ver" value="<%=request.getAttribute("version")%>" hidden="hidden">
</form>
<div style="position: relative;left: 30%; bottom: 9%"><a href="/myApp/unlock?id=<%=request.getParameter("id")%>"><button style="font-size: large">Back</button></a></div>
</body>
</html>
