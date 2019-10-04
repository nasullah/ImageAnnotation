<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="Comment" />
    <title>IHC reason</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
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
    <g:form action="saveFocus" method="POST">
        <g:hiddenField name="imageId" value="${params.imageId}"/>
        <div class="row">
            <br>
            <div class="col-lg-6" style="color:#8DD3C7">
                <div>
                    <label for="focus1"> Focus 1: </label>
                    <g:select name="focus1" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 1}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
                </div>
                <br>
                <label for="diagnosis1"> Final Diagnosis: </label>
                <g:select id="diagnosis1" name="diagnosis1" from="${imageannotation.Diagnosis.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 1}?.diagnosis?.id}" noSelection="['':'-Choose-']" onchange="showDiagnosisOther()"/>
                <label  id="diagnosisOtherLabel1"> Please specify: </label>
                <input type="text" id="diagnosisOther1" name="diagnosisOther1" value="${foci.find {it?.focusNumber == 1}?.diagnosisNameOther}" style="height: 20px">
            </div>
            <div class="col-lg-6" style="color:#71a1c5">
                <div>
                    <label for="focus5"> Focus 5: </label>
                    <g:select name="focus5" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 5}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
                </div>
                <br>
                <label for="diagnosis5"> Final Diagnosis: </label>
                <g:select id="diagnosis5" name="diagnosis5" from="${imageannotation.Diagnosis.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 5}?.diagnosis?.id}" noSelection="['':'-Choose-']"  onchange="showDiagnosisOther()"/>
                <label id="diagnosisOtherLabel5"> Please specify: </label>
                <input type="text" id="diagnosisOther5" name="diagnosisOther5" value="${foci.find {it?.focusNumber == 5}?.diagnosisNameOther}" style="height: 20px">
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-lg-6" style="color:#ffe92c">
                <div>
                    <label for="focus2"> Focus 2: </label>
                    <g:select name="focus2" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 2}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
                </div>
                <br>
                <label for="diagnosis2"> Final Diagnosis: </label>
                <g:select id="diagnosis2" name="diagnosis2" from="${imageannotation.Diagnosis.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 2}?.diagnosis?.id}" noSelection="['':'-Choose-']"  onchange="showDiagnosisOther()"/>

                <label id="diagnosisOtherLabel2"> Please specify: </label>
                <input type="text" id="diagnosisOther2" name="diagnosisOther2" value="${foci.find {it?.focusNumber == 2}?.diagnosisNameOther}" style="height: 20px">
            </div>
            <div class="col-lg-6" style="color:#fdb563">
                <div>
                    <label for="focus6"> Focus 6: </label>
                    <g:select name="focus6" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 6}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
                </div>
                <br>
                <label for="diagnosis6"> Final Diagnosis: </label>
                <g:select id="diagnosis6" name="diagnosis6" from="${imageannotation.Diagnosis.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 6}?.diagnosis?.id}" noSelection="['':'-Choose-']"  onchange="showDiagnosisOther()"/>

                <label id="diagnosisOtherLabel6"> Please specify: </label>
                <input type="text" id="diagnosisOther6" name="diagnosisOther6" value="${foci.find {it?.focusNumber == 6}?.diagnosisNameOther}" style="height: 20px">
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-lg-6" style="color:#d6d1f9">
                <div>
                    <label for="focus3"> Focus 3: </label>
                    <g:select name="focus3" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 3}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
                </div>
                <br>
                <label for="diagnosis3"> Final Diagnosis: </label>
                <g:select id="diagnosis3" name="diagnosis3" from="${imageannotation.Diagnosis.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 3}?.diagnosis?.id}" noSelection="['':'-Choose-']"  onchange="showDiagnosisOther()"/>

                <label id="diagnosisOtherLabel3"> Please specify: </label>
                <input type="text" id="diagnosisOther3" name="diagnosisOther3" value="${foci.find {it?.focusNumber == 3}?.diagnosisNameOther}" style="height: 20px">
            </div>
            <div class="col-lg-6" style="color:#b3de68">
                <div>
                    <label for="focus7"> Focus 7: </label>
                    <g:select name="focus7" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 7}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
                </div>
                <br>
                <label for="diagnosis7"> Final Diagnosis: </label>
                <g:select id="diagnosis7" name="diagnosis7" from="${imageannotation.Diagnosis.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 7}?.diagnosis?.id}" noSelection="['':'-Choose-']"  onchange="showDiagnosisOther()"/>

                <label id="diagnosisOtherLabel7"> Please specify: </label>
                <input type="text" id="diagnosisOther7" name="diagnosisOther7" value="${foci.find {it?.focusNumber == 7}?.diagnosisNameOther}" style="height: 20px">
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-lg-6" style="color:#fb8274">
                <div>
                    <label for="focus4"> Focus 4: </label>
                    <g:select name="focus4" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 4}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
                </div>
                <br>
                <label for="diagnosis4"> Final Diagnosis: </label>
                <g:select id="diagnosis4" name="diagnosis4" from="${imageannotation.Diagnosis.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 4}?.diagnosis?.id}" noSelection="['':'-Choose-']"  onchange="showDiagnosisOther()"/>

                <label id="diagnosisOtherLabel4"> Please specify: </label>
                <input type="text" id="diagnosisOther4" name="diagnosisOther4" value="${foci.find {it?.focusNumber == 4}?.diagnosisNameOther}" style="height: 20px">
            </div>
            <div class="col-lg-6" style="color:#fccfe6">
                <div>
                    <label for="focus8"> Focus 8: </label>
                    <g:select name="focus8" from="${imageannotation.FocusStatusDesc.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 8}?.status?.id}" noSelection="['':'-Choose a reason-']" style="width:500px"/>
                </div>
                <br>
                <label for="diagnosis8"> Final Diagnosis: </label>
                <g:select id="diagnosis8" name="diagnosis8" from="${imageannotation.Diagnosis.list()}" optionKey="id" value="${foci.find {it?.focusNumber == 8}?.diagnosis?.id}" noSelection="['':'-Choose-']"  onchange="showDiagnosisOther()"/>

                <label id="diagnosisOtherLabel8"> Please specify: </label>
                <input type="text" id="diagnosisOther8" name="diagnosisOther8" value="${foci.find {it?.focusNumber == 8}?.diagnosisNameOther}" style="height: 20px">
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-lg-12">
                <label for="comment">Comment</label>
                <g:textArea name="comment" placeholder='Please add your comments' style="height:100px;width:490px">${multiplexImage?.comment}</g:textArea>
            </div>
        </div>
        <br>
        <fieldset class="buttons">
            <g:submitButton name="create" class="save" value="Save" />
        </fieldset>
    </g:form>
    <g:hiddenField name="diagnosisOther" id="diagnosisOther" value="${imageannotation.Diagnosis.findByDiagnosisName('Other')?.id}" />
</div>
<br>
<br>
<br>
<script>
    showDiagnosisOther();
    function showDiagnosisOther(){
        var diagnosisOther = $("#diagnosisOther").val();
        var diagnosisOther1 = $("#diagnosis1").val();
        if(diagnosisOther1 == diagnosisOther){
            $("#diagnosisOther1").show();
            $("#diagnosisOtherLabel1").show();
        }else{
            $("#diagnosisOther1").hide();
            $("#diagnosisOther1").val('');
            $("#diagnosisOtherLabel1").hide();
        }
        var diagnosisOther2 = $("#diagnosis2").val();
        if(diagnosisOther2 == diagnosisOther){
            $("#diagnosisOther2").show();
            $("#diagnosisOtherLabel2").show();
        }else{
            $("#diagnosisOther2").hide();
            $("#diagnosisOther2").val('');
            $("#diagnosisOtherLabel2").hide();
        }
        var diagnosisOther3 = $("#diagnosis3").val();
        if(diagnosisOther3 == diagnosisOther){
            $("#diagnosisOther3").show();
            $("#diagnosisOtherLabel3").show();
        }else{
            $("#diagnosisOther3").hide();
            $("#diagnosisOther3").val('');
            $("#diagnosisOtherLabel3").hide();
        }
        var diagnosisOther4 = $("#diagnosis4").val();
        if(diagnosisOther4 == diagnosisOther){
            $("#diagnosisOther4").show();
            $("#diagnosisOtherLabel4").show();
        }else{
            $("#diagnosisOther4").hide();
            $("#diagnosisOther4").val('');
            $("#diagnosisOtherLabel4").hide();
        }
        var diagnosisOther5 = $("#diagnosis5").val();
        if(diagnosisOther5 == diagnosisOther){
            $("#diagnosisOther5").show();
            $("#diagnosisOtherLabel5").show();
        }else{
            $("#diagnosisOther5").hide();
            $("#diagnosisOther5").val('');
            $("#diagnosisOtherLabel5").hide();
        }
        var diagnosisOther6 = $("#diagnosis6").val();
        if(diagnosisOther6 == diagnosisOther){
            $("#diagnosisOther6").show();
            $("#diagnosisOtherLabel6").show();
        }else{
            $("#diagnosisOther6").hide();
            $("#diagnosisOther6").val('');
            $("#diagnosisOtherLabel6").hide();
        }
        var diagnosisOther7 = $("#diagnosis7").val();
        if(diagnosisOther7 == diagnosisOther){
            $("#diagnosisOther7").show();
            $("#diagnosisOtherLabel7").show();
        }else{
            $("#diagnosisOther7").hide();
            $("#diagnosisOther7").val('');
            $("#diagnosisOtherLabel7").hide();
        }
        var diagnosisOther8 = $("#diagnosis8").val();
        if(diagnosisOther8 == diagnosisOther){
            $("#diagnosisOther8").show();
            $("#diagnosisOtherLabel8").show();
        }else{
            $("#diagnosisOther8").hide();
            $("#diagnosisOther8").val('');
            $("#diagnosisOtherLabel8").hide();
        }
    }
</script>
</body>
</html>