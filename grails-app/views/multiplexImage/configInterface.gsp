<%@ page import="imageannotation.Expert" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Config Interface</title>
</head>
<body>
<h1><center>Configure Interface</center></h1>
<br/>
<div style="margin: 0 auto;">
    <div style="margin-left: 50px">
        <g:form action="saveConfiguration" method="POST">

            <label>Folder Name (assets/attachments/)</label>
            <g:textField name="folderName" value="${folderName}" required=""/><br><br>

            <label>Select Annotator</label>
            <g:select id="annotator" name="annotator" from="${Expert.list()}" optionKey="id" required="" noSelection="['':'-Choose annotator-']" /><br><br>
            %{--<g:select name="annotator" from="${Expert.list()}" noSelection="['':'-Choose annotator-']" required=""/><br><br>--}%
            <label>Edit JSON</label>
            <g:textArea name="annotationData" value=""  rows="5" cols="40" style="height: 400px;width: 1000px" required=""/>

            <hr/>

            <fieldset class="buttons">
                <g:submitButton name="create" class="save" value="Save" />
            </fieldset>

            <hr/>

        </g:form>
    </div>
    <hr>
    <p>Channels</p>
    <div style="margin-left: 50px">
        <g:form action="saveConfigurationChannel" method="POST">

            <label>Folder Name (assets/attachments/)</label>
            <g:textField name="folderName" value="${folderName}" required=""/><br><br>

            <label>Select Annotator</label>
            <g:select id="annotator" name="annotator" from="${Expert.list()}" optionKey="id" required="" noSelection="['':'-Choose annotator-']" /><br><br>
        %{--<g:select name="annotator" from="${Expert.list()}" noSelection="['':'-Choose annotator-']" required=""/><br><br>--}%
            <label>Edit JSON</label>
            <g:textArea name="annotationData" value=""  rows="5" cols="40" style="height: 400px;width: 1000px" required=""/>

            <hr/>

            <fieldset class="buttons">
                <g:submitButton name="create" class="save" value="Save" />
            </fieldset>

            <hr/>

        </g:form>
    </div>
</div>
<br/>
<br/>
<br/>
<br/>
</body>
</html>