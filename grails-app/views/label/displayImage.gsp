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
    <g:set var="entityName" value="${message(code: 'annotation.label', default: 'Annotation')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<div align="center">
    <g:if test="${imagePath}">
        <h4>Please choose an appropriate label for the area within the box</h4>

        <p><b><font color="red">* Your response will be saved by selecting a label *</font></b></p>
        <h6><font color="blue">You have labelled ${count} images so far!</font></h6>
        <hr>
        <div style="height: 512px;width: 512px;position: relative;">
            <img id="image" width="512" height="512"style="position: absolute;left: 0;top: 0;" src=${assetPath(src: imagePath)}/>
        <div style="width:64px;height:64px;border:1px solid #ffff00;position: absolute;left: 224px;top: 224px;"></div>
        </div>
        <hr>
        <g:form action="saveLabel" method="post">
            <g:hiddenField name="patchId" value="${patchId}"/>
            <g:radioGroup style="height:22px; width:22px;" name="labelName"
                          labels="['Tumour', 'Benign', 'Stroma', 'Lumen', 'PIN', 'Not sure']"
                          values="[1, 2, 3, 4, 5, 6]"
                          onChange="this.form.submit();this.disabled=true;this.value='Sending, please wait...';">
                <span style="font-size:22px">&nbsp; ${it.label} ${it.radio} &nbsp;</span>
            </g:radioGroup>
        </g:form>
        <hr>
    </g:if>
    <g:else>
        <h4>There is no image to be labelled</h4>
    </g:else>
</div>
<br>
<br>
</body>
</html>