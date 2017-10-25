<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'study.label', default: 'Your Study')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-study" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<section id="index-centre" class="first">

    <table class="table table-bordered margin-top-medium">
        <thead>
        <tr>

            <g:sortableColumn property="studyName" title="Study Name" />

            <g:sortableColumn property="studyOwner" title="Study Owner" />

        </tr>
        </thead>
        <tbody>
        <g:each in="${studyList}" status="i" var="study">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                <td>${fieldValue(bean: study, field: "studyName")}</td>

                <td>${fieldValue(bean: study, field: "studyOwner")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

</section>
</body>
</html>