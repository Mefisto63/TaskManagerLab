<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 22.02.15
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ex" uri="/WEB-INF/mytag.tld" %>
<html>
<script>
    function hideTdFields() {
        var elem = document.getElementById("td");
        if (elem.style.visibility != "hidden") {
            elem.style.visibility = "hidden";
        } else {
            elem.style.visibility = "visible";
        }
    }
    function hideCreateFields() {
        var elem = document.getElementById("createFields");
        if (elem.style.visibility != "hidden") {
            elem.style.visibility = "hidden";
        } else {
            elem.style.visibility = "visible";
        }
    }
</script>
<head>
    <title>Task Information</title>
</head>
<body style="background-color: #f5f5ea" onload="hideTdFields(); hideCreateFields(); return false;">
<table width="80%">
    <tr>
        <td style="width: 60%">
            <div style="font-size: 14pt">
                <br><b>Name:</b>

                <p><%=request.getAttribute("name")%>
                </p>
                <hr>
                <br><b>Description:</b>

                <p><%=request.getAttribute("desc")%>
                </p>
                <ex:extraFields task='<%=request.getAttribute("task")%>'/>
                <hr>
                <br><b>Time:</b>
                <table style="width: 100%">
                    <tr>
                        <td style="font-size: 14pt; width: 50%">
                            <ex:timeList idTask='<%=request.getAttribute("id")%>'/>
                        </td>
                        <form method="get" action="changeTime">
                            <td id="td">
                                <ex:inputTimeList idTask='<%=request.getAttribute("id")%>'/>
                                <input type="submit" value="change" style="position: absolute;" onclick="hideFields();">
                            </td>
                            <input type="text" name="idTask" value="<%=request.getAttribute("id")%>" hidden="hidden">
                        </form>
                    </tr>
                </table>
            </div>
        </td>
        <td style="position: relative; left: 10%; width: auto">
            <a href="/myApp/update?id=<%=request.getAttribute("id")%>&ver=<%=request.getAttribute("ver")%>">
                <button style="font-size: 15pt; position: absolute; top: 10%">Edit</button>
            </a>
            <br>
            <br>
            <button style="font-size: 15pt; position: absolute; top: 25%;" onclick="hideTdFields(); return false;">
                Change
                time
            </button>
            <br>
            <br>
            <button style="font-size: 15pt; position: absolute; top: 40%;" onclick="hideCreateFields(); return false;">
                Create new time:
            </button>
            <br>
            <br>
            <a href="/myApp/delete?id=<%=request.getParameter("id")%>&ver=<%=request.getAttribute("ver")%>">
                <button style="font-size: 15pt; position: absolute; top: 55%;">Delete</button>
            </a>
            <a href="/myApp/tasks">
                <button style="font-size: 15pt; position: absolute; top: 70%;">Back</button>
            </a>
        </td>
        <td style="position: relative; left: 10%; width: auto">
            <form method="get" action="createTrig">
                <div style="font-size: 15pt; position: absolute; top: 40%;" id="createFields">
                    <input id="date" name="date" type="date">
                    <input id="time" name="time" type="time">
                    <input type="submit" value="ok">
                    <input name="id" type="text" value="<%=request.getParameter("id")%>" hidden="hidden">
                </div>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
