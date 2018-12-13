<%--
  Created by IntelliJ IDEA.
  User: nasulla
  Date: 12/03/2018
  Time: 16:14
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Display Transplant Patches</title>
</head>

<body>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<div align="center">
    <g:if test="${imagePath}">
        <h4>Please rate the image on a rating scale of 1-5</h4>
        <p><b><font color="red">* Your response will be saved by rating the image *</font></b></p>
        <p><font color="blue">You have rated ${count} images so far!</font></p>
        <hr>
        <div style="height: 512px;width: 512px;position: relative;">
            <img id="image" width="512" height="512"style="position: absolute;left: 0;top: 0;" src=${assetPath(src: imagePath)}/>
            %{--<div style="width:64px;height:64px;border:1px solid #ffff00;position: absolute;left: 224px;top: 224px;"></div>--}%
        </div>
        <hr>
        <g:form name="theForm" action="saveRatings" method="post">
            <g:hiddenField name="patchId" value="${patchId}"/>
            %{--<p id="selectLabel">Select a label after adding your comments</p>--}%
            %{--<p id="addComment">Add your comments (optional)--}%
                %{--<a href="#" id="button" onclick="showComments()" class="btn btn-link" role="button" aria-pressed="true"><i class="glyphicon glyphicon-plus"></i></a>--}%
            %{--</p>--}%
            %{--<g:textArea style="height:50px;width:840px" name="comment" id="comment"/>--}%
            <h4>How informative is the image? Rate 1 is ‘not at all informative’ and 5 is ‘very informative'</h4>
            <span style="font-size:22px"> 1 <g:radio style="height:22px; width:22px;" name="labelInformative" value="1" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 2 <g:radio style="height:22px; width:22px;" name="labelInformative" value="2" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 3 <g:radio style="height:22px; width:22px;" name="labelInformative" value="3" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 4 <g:radio style="height:22px; width:22px;" name="labelInformative" value="4" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 5 <g:radio style="height:22px; width:22px;" name="labelInformative" value="5" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> Not opinion <g:radio style="height:22px; width:22px;" name="labelInformative" value="6" onChange="submitForm()"/></span>
            <hr>
            <h4>How healthy is the image? Rate 1 is ‘not at all healthy’ and 5 is ‘very healthy'</h4>
            <span style="font-size:22px"> 1 <g:radio style="height:22px; width:22px;" name="labelHealthy" value="1" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 2 <g:radio style="height:22px; width:22px;" name="labelHealthy" value="2" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 3 <g:radio style="height:22px; width:22px;" name="labelHealthy" value="3" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 4 <g:radio style="height:22px; width:22px;" name="labelHealthy" value="4" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> 5 <g:radio style="height:22px; width:22px;" name="labelHealthy" value="5" onChange="submitForm()"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px"> Not opinion <g:radio style="height:22px; width:22px;" name="labelHealthy" value="6" onChange="submitForm()"/></span>
            <hr>
        </g:form>
    </g:if>
    <g:else>
        <h4>You have completed rating all images. Thank you!</h4>
    </g:else>
</div>
<br>
<br>
<g:javascript plugin="jquery" library="jquery" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
    function submitForm(){
        if ($('input[name=labelInformative]:checked').length > 0 && $('input[name=labelHealthy]:checked').length > 0) {
            document.theForm.submit();
        }
    }

</script>
</body>
</html>