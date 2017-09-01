<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'pathologyImage.label', default: 'Pathology Image')}" />
    <title>Annotated Pathology Images</title>
</head>
<body>
<a href="#list-pathologyImage" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<section id="list-participant" class="first">

    <table class="table table-bordered margin-top-medium">
        <thead>
        <tr>
            <th>Image Id</th>

            <th>Image Type</th>

            <th>Image Taken Date</th>

            <th>Action</th>

        </tr>
        </thead>
        <tbody>
        %{--<g:each in="${annotatedPathologyImages}" status="i" var="annotatedPathologyImage">--}%
            <tr>

                <td>1</td>

                <td>Slide</td>

                <td>2017-05-01</td>

                <td><a class='btn btn-primary btn-xs' <g:link controller="annotation" action="testAnnotationTools"><i class="glyphicon glyphicon-zoom-in"></i> View</g:link></td>

            </tr>
        %{--</g:each>--}%

        </tbody>
    </table>

</section>
</body>
</html>