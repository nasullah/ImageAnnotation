<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'multiplexImage.label', default: 'Your Image')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-multiplexImage" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<section id="index-centre" class="first">

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
        <g:each in="${imageList}" status="i" var="image">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                <td>${fieldValue(bean: image, field: "study")}</td>

                <td>${fieldValue(bean: image, field: "multiplexImageIdentifier")}</td>

                <td>${fieldValue(bean: image, field: "multiplexImageName")}</td>

                <td><g:link controller="annotation" action="viewImageOnOS" params="['imageId': image.id, 'annotatorId':annotatorId]"><i class="glyphicon glyphicon-eye-open"></i> View</g:link></td>

            </tr>
        </g:each>
        </tbody>
    </table>

</section>

<hr>
<center><g:link controller="userSurvey" action="create"> Please complete this survey at the end of annotation task</g:link></center>
<hr>

</body>
</html>