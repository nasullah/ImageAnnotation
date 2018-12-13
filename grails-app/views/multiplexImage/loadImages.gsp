<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Load Images</title>
</head>
<body>
<h1><center>Load Images</center></h1>
<br/>
<div style="margin: 0 auto;">
    <div style="margin-left: 50px">
        <g:form action="saveLoadedImages" method="POST">

            <label>Folder Name (assets/attachments/)</label>
            <g:textField name="folderName" value="${folderName}" /><br>
            <label>Study Name</label>
            <g:textField name="studyName" value="${studyName}" />

            <hr/>

            <fieldset class="buttons">
                <g:submitButton name="create" class="save" value="Load" />
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