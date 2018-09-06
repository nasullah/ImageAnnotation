<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="Comment" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
<a href="#create-annotation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div id="create-annotation" class="content scaffold-create" role="main">
    <h1>Assign a stage</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${this.annotation}">
        <ul class="errors" role="alert">
            <g:eachError bean="${this.annotation}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="saveAnnotationWithComment" method="POST">
        <g:hiddenField name="imageId" value="${params.imageId}"/>
        <div class="row">
            <div class="col-lg-6">
                <label for="stage"> Stage <span style="color: red">*</span></label><br>
                <g:select name="stage" from="${['pT2', 'pT2+', 'pT3a']}" value="${stage}" noSelection="['':'-Choose a stage-']" required=""/>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-lg-12">
                <label for="comment">Comment</label><br>
                <g:textArea name="comment" placeholder='Please add you comments' style="height:300px;width:600px"/>
            </div>
        </div>
        <br>
        <fieldset class="buttons">
            <g:submitButton name="create" class="save" value="Save" />
        </fieldset>
    </g:form>
</div>
</body>
</html>