<%--
  Created by IntelliJ IDEA.
  User: nasulla
  Date: 12/03/2018
  Time: 16:14
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Review Labels</title>
</head>

<body>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<div align="center">
    %{--<g:if test="${imagePath}">--}%
    <h3>Label Review Page</h3>

    <h6><font color="blue">Image ${currentInstance + 1} of ${count}</font></h6>
    <g:if test="${currentInstance == 0}">
        <a class='btn btn-primary btn' <g:link controller="label" action="reviewTransplantLabels" params="['nextInstance': currentInstance]"><i class="glyphicon glyphicon-arrow-right"></i> Next </g:link>
    </g:if>
    <g:elseif test="${currentInstance == count - 1}">
        <a class='btn btn-primary btn' <g:link controller="label" action="reviewTransplantLabels" params="['previousInstance': currentInstance]"><i class="glyphicon glyphicon-arrow-left"></i> Back </g:link>
    </g:elseif>
    <g:else>
        <a class='btn btn-primary btn' <g:link controller="label" action="reviewTransplantLabels" params="['previousInstance': currentInstance]"><i class="glyphicon glyphicon-arrow-left"></i> Back </g:link> &nbsp;&nbsp;
            <a class='btn btn-primary btn' <g:link controller="label" action="reviewTransplantLabels" params="['nextInstance': currentInstance]"><i class="glyphicon glyphicon-arrow-right"></i> Next </g:link>
    </g:else>
    <hr>
    <div style="height: 512px;width: 512px;position: relative;">
        <img id="image" width="512" height="512"style="position: absolute;left: 0;top: 0;" src=${assetPath(src: imagePath)}/>
        %{--<div style="width:64px;height:64px;border:1px solid #ffff00;position: absolute;left: 224px;top: 224px;"></div>--}%
    </div>
    <br>
    <h3>Informative</h3>
    <br>
    <g:radioGroup style="height:22px; width:22px;" name="myGroup"
                  values="[1, 2, 3, 4, 5, 6]"
                  labels="['1', '2', '3', '4', '5', 'Not opinion']"
                  value="${labelInformative}"
                  disabled="${'true'}">
        <span style="font-size:22px">&nbsp; ${it.label} ${it.radio} &nbsp;</span>
    </g:radioGroup>
    <hr>
    <h3>Healthy</h3>
    <br>
    <g:radioGroup style="height:22px; width:22px;" name="labelInformative"
                  labels="['1', '2', '3', '4', '5', 'Not opinion']"
                  values="[1, 2, 3, 4, 5, 6]"
                  value="${labelHealthy}"
                  disabled="${'true'}" >
        <span style="font-size:22px">&nbsp; ${it.label} ${it.radio} &nbsp;</span>
    </g:radioGroup>
    <hr>

</div>
<br>
<br>
</body>
</html>