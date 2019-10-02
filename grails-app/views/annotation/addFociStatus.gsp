<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="Comment" />
    <title>IHC reason</title>
</head>
<body>
<a href="#create-annotation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div id="create-annotation" class="content scaffold-create" role="main">
    <h1>Assign reason for IHC request</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${this.multiplexImage}">
        <ul class="errors" role="alert">
            <g:eachError bean="${this.multiplexImage}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="saveAnnotationWithFocus" method="POST">
        <g:hiddenField name="imageId" value="${params.imageId}"/>
        <div class="row">
            <div class="col-lg-6" style="color:#8DD3C7">
                <label for="focus1"> Focus 1 </label><br>
                <g:select name="focus1" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 1}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
            </div>
            <div class="col-lg-6" style="color:#71a1c5">
                <label for="focus5"> Focus 5 </label><br>
                <g:select name="focus5" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 5}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
            </div>
            <div class="col-lg-6" style="color:#ffe92c">
                <label for="focus2"> Focus 2 </label><br>
                <g:select name="focus2" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 2}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
            </div>
            <div class="col-lg-6" style="color:#fdb563">
                <label for="focus6"> Focus 6 </label><br>
                <g:select name="focus6" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 6}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
            </div>
            <div class="col-lg-6" style="color:#d6d1f9">
                <label for="focus3"> Focus 3 </label><br>
                <g:select name="focus3" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 3}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
            </div>
            <div class="col-lg-6" style="color:#b3de68">
                <label for="focus7"> Focus 7 </label><br>
                <g:select name="focus7" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 7}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
            </div>
            <div class="col-lg-6" style="color:#fb8274">
                <label for="focus4"> Focus 4 </label><br>
                <g:select name="focus4" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 4}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
            </div>
            <div class="col-lg-6" style="color:#fccfe6">
                <label for="focus8"> Focus 8 </label><br>
                <g:select name="focus8" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 8}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-lg-12">
                <label for="comment">Comment</label><br>
                <g:textArea name="comment" placeholder='Please add you comments' style="height:200px;width:500px">${multiplexImage?.comment}</g:textArea>
            </div>
        </div>
        <br>
        <fieldset class="buttons">
            <g:submitButton name="create" class="save" value="Save" />
        </fieldset>
    </g:form>
</div>
<br>
<br>
<br>
</body>
</html>