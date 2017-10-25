<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'multiplexImage.label', default: 'Multiplex Image')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <table class="table table-bordered margin-top-medium">
        <thead>
        <tr>

            <g:sortableColumn property="study" title="Study" />

            <g:sortableColumn property="multiplexImageIdentifier" title="Multiplex Image Identifier" />

            <g:sortableColumn property="multiplexImageName" title="Multiplex Image Name" />

            <th>View</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${multiplexImageList}" status="i" var="image">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                <td><g:link action="show" id="${image.id}">${fieldValue(bean: image, field: "study")}</g:link></td>

                <td>${fieldValue(bean: image, field: "multiplexImageIdentifier")}</td>

                <td>${fieldValue(bean: image, field: "multiplexImageName")}</td>

                <td><g:link controller="annotation" action="viewImageOnOS" params="['imageId': image.id, 'annotatorId':'sdfssfsdfdfdsf']"> View</g:link></td>

            </tr>
        </g:each>
        </tbody>
    </table>
    </body>
</html>
