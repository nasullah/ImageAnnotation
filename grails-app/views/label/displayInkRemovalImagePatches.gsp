<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Display Patches</title>
    <style>
    * {
        box-sizing: border-box;
    }

    .column {
        float: left;
        width: 33.33%;
        padding: 5px;
    }

    /* Clearfix (clear floats) */
    .row::after {
        content: "";
        clear: both;
        display: table;
    }
    </style>
</head>

<body>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<div align="center">
    <g:if test="${imagePath}">
        <h4>Please rate the quality of the restored image</h4>
        <p><font color="blue">You have labelled ${count} images so far!</font></p>
        <hr>
    %{--<div style="height: 512px;width: 512px;position: relative;">--}%
    %{--<img id="image" width="512" height="512"style="position: absolute;left: 0;top: 0;" src=${assetPath(src: '2.jpeg')}/>--}%
    %{--<div style="width:64px;height:64px;border:1px solid #ffff00;position: absolute;left: 224px;top: 224px;"></div>--}%
    %{--</div>--}%
        <div class="row">
            <div class="column">
                <img id="imageWithInk" width="400" height="400" src=${assetPath(src: imagePath)}/>
                <p><font color="blue">With ink</font></p>
            </div>
            <div class="column">
                <img id="imageOriginal" width="400" height="400" src=${assetPath(src: imagePath?.replace('red_','')?.replace('blue_','')?.replace('green_','')?.replace('black_',''))}/>
                <p><font color="blue">Original</font></p>
            </div>
            <div class="column">
                <img id="imageRestored" width="400" height="400" src=${assetPath(src: imagePath?.replace('.jpeg','.png'))}/>
                <p><font color="blue">Restored</font></p>
            </div>
        </div>
        <g:form action="saveInkRemovalRatings" name="theForm" method="post">
        <g:hiddenField name="patchId" value="${patchId}"/>
            <p id="selectLabel">Select a label after adding your comments</p>
            <p id="addComment"><font color="blue">Add your comments (optional)</font>
                <a href="#" id="button" onclick="showComments()" class="btn btn-link" role="button" aria-pressed="true"><i class="glyphicon glyphicon-plus"></i></a>
            </p>
            <g:textArea style="height:50px;width:840px" name="comment" id="comment"/>
            <h4>Has any content been lost: Rate 1 is ‘total loss’ and 5 is ‘no loss at all'</h4>
            <span style="font-size:22px"> 1 <g:radio style="height:22px; width:22px;" name="contentLoss" value="1" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 2 <g:radio style="height:22px; width:22px;" name="contentLoss" value="2" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 3 <g:radio style="height:22px; width:22px;" name="contentLoss" value="3" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 4 <g:radio style="height:22px; width:22px;" name="contentLoss" value="4" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 5 <g:radio style="height:22px; width:22px;" name="contentLoss" value="5" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> No opinion <g:radio style="height:22px; width:22px;" name="contentLoss" value="6" onChange="submitForm()"/></span>
            <hr>
            <h4>Has any additional unwanted content been added: Rate 1 is ‘total addition’ and 5 is ‘no addition at all'</h4>
            <span style="font-size:22px"> 1 <g:radio style="height:22px; width:22px;" name="contentAdd" value="1" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 2 <g:radio style="height:22px; width:22px;" name="contentAdd" value="2" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 3 <g:radio style="height:22px; width:22px;" name="contentAdd" value="3" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 4 <g:radio style="height:22px; width:22px;" name="contentAdd" value="4" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 5 <g:radio style="height:22px; width:22px;" name="contentAdd" value="5" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> No opinion <g:radio style="height:22px; width:22px;" name="contentAdd" value="6" onChange="submitForm()"/></span>
            <hr>
            <h4>General restoration quality: Rate 1 is ‘totally dissatisfied’ and 5 is ‘totally satisfied'</h4>
            <span style="font-size:22px"> 1 <g:radio style="height:22px; width:22px;" name="generalQuality" value="1" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 2 <g:radio style="height:22px; width:22px;" name="generalQuality" value="2" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 3 <g:radio style="height:22px; width:22px;" name="generalQuality" value="3" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 4 <g:radio style="height:22px; width:22px;" name="generalQuality" value="4" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 5 <g:radio style="height:22px; width:22px;" name="generalQuality" value="5" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> No opinion <g:radio style="height:22px; width:22px;" name="generalQuality" value="6" onChange="submitForm()"/></span>
            <hr>
            <h4>Is there sufficient quality in order to enable a diagnosis</h4>
            <span style="font-size:22px"> Yes <g:radio style="height:22px; width:22px;" name="diagnosisQuality" value="1" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> No <g:radio style="height:22px; width:22px;" name="diagnosisQuality" value="0" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> No opinion <g:radio style="height:22px; width:22px;" name="diagnosisQuality" value="6" onChange="submitForm()"/></span>
        </g:form>
        <hr>
    </g:if>
    <g:else>
        <h4>There is no image to be labelled</h4>
    </g:else>
</div>
<br>
<br>
<g:javascript plugin="jquery" library="jquery" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
    hideComments();
    function showComments(){
        $("#comment").show();
        $("#button").hide();
        $("#addComment").hide();
        $("#selectLabel").show();
        $('html,body').animate({scrollTop: document.body.scrollHeight},"fast");
    }
    function hideComments(){
        $("#comment").hide();
        $("#selectLabel").hide();
    }
    function submitForm(){
        if ($('input[name=contentLoss]:checked').length > 0 && $('input[name=contentAdd]:checked').length > 0 && $('input[name=generalQuality]:checked').length > 0 && $('input[name=diagnosisQuality]:checked').length > 0) {
            document.theForm.submit();
        }
    }
</script>
</body>
</html>