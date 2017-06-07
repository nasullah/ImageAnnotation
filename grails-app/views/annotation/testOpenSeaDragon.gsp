<%--
  Created by IntelliJ IDEA.
  User: nasulla
  Date: 25/05/2017
  Time: 11:24
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'annotation.label', default: 'Annotation')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>

<div id="openseadragon1" style="width: 1100px; height: 600px;"></div>
<asset:javascript src="openseadragon.min.js"/>
<script type="text/javascript">
    var viewer = OpenSeadragon({
        id: "openseadragon1",
        prefixUrl: "${assetPath(src: 'openseadragon/')}",
        tileSources: {
            type: 'image',
            url:  "${assetPath(src: 'test.jpg')}",
            buildPyramid: false
        }
    });
</script>

</body>
</html>