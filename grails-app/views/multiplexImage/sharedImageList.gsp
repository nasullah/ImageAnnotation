<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'multiplexImage.label', default: 'Shared Image')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-multiplexImage" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<section id="index-centre" class="first">

    <table class="table table-bordered margin-top-medium">
        <thead>
        <tr>

            <g:sortableColumn property="study" title="Study" />

            <g:sortableColumn property="multiplexImageName" title="Multiplex Image Name" />

            <g:if test="${imageList?.study?.studyName?.first() == 'TCGA_Prostate_Study'}">
                <th>Annotation</th>
            </g:if>

            <th>View</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${imageList}" status="i" var="image">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                <td>${fieldValue(bean: image, field: "study")}</td>

                <td>${fieldValue(bean: image, field: "multiplexImageName")}</td>

                <g:if test="${image?.study?.studyName == 'TCGA_Prostate_Study'}">
                    <g:if test="${image?.annotations?.findAll {it.imageAnnotator.id == 250}?.size() > 1}">
                        <td style="color: forestgreen">Complete</td>
                    </g:if>
                    <g:else>
                        <td style="color: red">Incomplete</td>
                    </g:else>
                </g:if>

                <g:if test="${image?.study?.studyName == 'TCGA_Prostate_Study'}">
                    <td><g:link controller="annotation" action="viewImageOnOS" params="['imageId': image.id, 'annotatorId':250]" target="_blank"><i class="glyphicon glyphicon-eye-open"></i> View</g:link></td>
                </g:if>
                <g:else>
                    <td><g:link controller="annotation" action="viewImageOnOS" params="['imageId': image.id, 'annotatorId':annotatorId]" target="_blank"><i class="glyphicon glyphicon-eye-open"></i> View</g:link></td>
                </g:else>
            </tr>
        </g:each>
        </tbody>
    </table>
</section>

<hr>
<center><g:link controller="userSurvey" action="create"> Please complete this survey at the end of annotation task</g:link></center>
<hr>

<br>
<br>
<br>

</body>
</html>