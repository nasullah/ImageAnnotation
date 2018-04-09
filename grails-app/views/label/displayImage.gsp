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
    <title>Display Patches</title>
</head>

<body>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<div align="center">
    <g:if test="${imagePath}">
        <h4>Please choose an appropriate label for the majority of the box contents</h4>
        <p>Additional information about the labels can be found by moving the mouse over the labels</p>
        <p><font color="blue">You have labelled ${count} images so far!</font></p>
        <hr>
        <div style="height: 512px;width: 512px;position: relative;">
            <img id="image" width="512" height="512"style="position: absolute;left: 0;top: 0;" src=${assetPath(src: imagePath)}/>
        <div style="width:64px;height:64px;border:1px solid #ffff00;position: absolute;left: 224px;top: 224px;"></div>
        </div>
        <hr>
        <g:form action="saveLabel" method="post">
            <g:hiddenField name="patchId" value="${patchId}"/>
            <p id="selectLabel">Select a label after adding your comments</p>
            <p id="addComment">Add your comments (optional)
                <a href="#" id="button" onclick="showComments()" class="btn btn-link" role="button" aria-pressed="true"><i class="glyphicon glyphicon-plus"></i></a>
            </p>
            <g:textArea style="height:50px;width:840px" name="comment" id="comment"/>
            <hr>
            <p><b><font color="red">* Your response will be saved by selecting a label *</font></b></p>
            <span style="font-size:22px" title="This label should be used for all definite invasive tumour. Do not include intraductal spread of adenocarcinoma in this category."> Tumour <g:radio style="height:22px; width:22px;" name="labelName" value="1" onChange="this.form.submit();this.disabled=true;this.value='Sending, please wait...';" title="This label should be used for all definite invasive tumour. Do not include intraductal spread of adenocarcinoma in this category."/></span>&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px" title="This label should be used for all benign glands. Do not include PIN, which should be assigned to the ''PIN'' category."> Benign <g:radio style="height:22px; width:22px;" name="labelName" value="2" onChange="this.form.submit();this.disabled=true;this.value='Sending, please wait...';" title="This label should be used for all benign glands.  Do not include PIN, which should be assigned to the ''PIN'' category."/></span>&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px" title="This label should be used for stroma and should include all morphological variations of intraprostatic or extraprostatic stroma. This label should also be used for areas including (but no limited to) vessels, nerves or adipose tissue."> Stroma <g:radio style="height:22px; width:22px;" name="labelName" value="3" onChange="this.form.submit();this.disabled=true;this.value='Sending, please wait...';" title="This label should be used for stroma and should include all morphological variations of intraprostatic or extraprostatic stroma. This label should also be used for areas including (but no limited to) vessels, nerves or adipose tissue."/></span>&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px" title="This label should be used for empty lumina or luminal contents, such as mucin, crystalloids or corpora amylacea."> Lumen <g:radio style="height:22px; width:22px;" name="labelName" value="4" onChange="this.form.submit();this.disabled=true;this.value='Sending, please wait...';" title="This label should be used for empty lumina or luminal contents, such as mucin, crystalloids or corpora amylacea."/></span>&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px" title="This label should be used for PIN (high grade PIN) in previous definitions.  Do not use this label for what would have been previously called low or intermediate grade PIN, this should be assigned as ''benign'' or ''not sure'' as appropriate."> PIN <g:radio style="height:22px; width:22px;" name="labelName" value="5" onChange="this.form.submit();this.disabled=true;this.value='Sending, please wait...';" title="This label should be used for PIN (high grade PIN) in previous definitions. Do not use this label for what would have been previously called low or intermediate grade PIN, this should be assigned as ''benign'' or ''not sure'' as appropriate."/></span>&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px" title="This label should be used when the majority of the box contains lymphoid or inflammatory cells of any type - including (but not necessarily limited to) neutrophils, lymphocytes, plasma cells, eosinophils and macrophages."> Lymphoid cells <g:radio style="height:22px; width:22px;" name="labelName" value="7" onChange="this.form.submit();this.disabled=true;this.value='Sending, please wait...';" title="This label should be used when the majority of the box contains lymphoid or inflammatory cells of any type - including (but not necessarily limited to) neutrophils, lymphocytes, plasma cells, eosinophils and macrophages."/></span>&nbsp;&nbsp;&nbsp;
            <span style="font-size:22px" title="This label should be used when no other label fits or the observer is genuinely uncertain as to what the correct diagnostic label for the box should be."> Not sure <g:radio style="height:22px; width:22px;" name="labelName" value="6" onChange="this.form.submit();this.disabled=true;this.value='Sending, please wait...';" title="This label should be used when no other label fits or the observer is genuinely uncertain as to what the correct diagnostic label for the box should be."/></span>
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
</script>
</body>
</html>