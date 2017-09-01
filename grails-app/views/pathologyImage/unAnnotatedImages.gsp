<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'pathologyImage.label', default: 'Pathology Image')}" />
    <title>Un-annotated Pathology Images</title>
</head>
<body>
<a href="#list-pathologyImage" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="list-pathologyImage" class="content scaffold-list" role="main">
    <h1>Un-annotated Pathology Image List</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <f:table collection="${unAnnotatedPathologyImages}" />

    %{--<div class="pagination">--}%
        %{--<g:paginate total="${pathologyImageCount ?: 0}" />--}%
    %{--</div>--}%
</div>
</body>
</html>