<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userSurvey.label', default: 'UserSurvey')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
    <br/>
    <div style="margin: 0 auto;">
        <div style="margin-left: 50px">
            <g:form resource="${this.userSurvey}" method="POST">
                <h3>Please answer the following questions</h3>
                <hr>
                <h4>1.  How easy did you find it to use AIDA for the annotation studies? <span style="color: red">*</span></h4>
                <g:radioGroup name="easeOfUse"
                              labels="['Very difficult','Difficult','No opinion','Easy','Very easy']"
                              values="[1,2,3,4,5]">
                    ${it.label} ${it.radio} &nbsp; &nbsp; &nbsp;
                </g:radioGroup>

                <hr/>

                <h4>2.  Would you be prepared to use the system again for a future study? <span style="color: red">*</span></h4>
                <g:radioGroup name="prepareToUseInFuture"
                              labels="['Yes','No']"
                              values="['Yes','No']">
                    ${it.label} ${it.radio} &nbsp; &nbsp; &nbsp;
                </g:radioGroup>

                <hr/>

                <h4>3.  Were there any aspects that were difficult to use and how could they be improved?</h4>
                <g:textArea name="feedBackForImprovement" value="" style="width:700px; height: 120px;"/>

                <hr style="height: 1px;"/>

                <h4>4.  Any further comments about the system?</h4>
                <g:textArea name="furtherComments" value="" style="width:700px; height: 120px;"/>

                <hr/>

                <fieldset class="buttons">
                <g:submitButton name="create" class="save" value="Submit" />
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
