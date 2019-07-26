<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'multiplexImage.label', default: 'Your Image')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-multiplexImage" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<section id="index-centre" class="first">

    <div class="row">
        <div>
            <label class="control-label"><small>Export Annotations</small></label>
            <div>
                <a class='btn btn-success btn-sm' <g:link controller="multiplexImage" action="exportAnnotations" params="['format': 'csv', 'extension': 'csv', 'annotator': annotatorId, 'study': study]"><i class="glyphicon glyphicon-export"></i> CSV Format</g:link>
            </div>
        </div>
    </div>

    <hr>

    <table class="table table-bordered margin-top-medium">
        <thead>
        <tr>

            <g:sortableColumn property="study" title="Study" />

            <g:sortableColumn property="multiplexImageIdentifier" title="Multiplex Image Identifier" />

            <th>View</th>

            <th>Annotation</th>

            %{--<g:if test="${imageList?.study?.studyName?.first() == 'Prostate_Annotations_Study' || imageList?.study?.studyName?.first() == 'TCGA_Prostate_Study' || imageList?.study?.studyName?.first() == 'Bladder_Annotation_Study' ||--}%
                          %{--imageList?.study?.studyName?.first() == 'Bladder_Annotation_Study' || imageList?.study?.studyName?.first() == 'Bladder_ROI_Annotation_Study' || imageList?.study?.studyName?.first() == 'Bladder_Tiles_Annotation_Study' ||--}%
                          %{--imageList?.study?.studyName?.first() == 'Bladder_ROI_Annotation_Study_Round_2' || imageList?.study?.studyName?.first() == 'Bladder_Tiles_Annotation_Study_Round_2' || imageList?.study?.studyName?.first() == 'CODEX_Annotation_Study' ||--}%
                          %{--imageList?.study?.studyName?.first() == 'Prostate_TMA_Annotation_Study' || imageList?.study?.studyName?.first() == 'Prostate_ICR_Annotation_Study' || imageList?.study?.studyName?.first() == 'EUX247_Annotation_Study' || imageList?.study?.studyName?.first() == 'MIF_annotation_study' ||--}%
                          %{--imageList?.study?.studyName?.first() == 'Bladder_Tiles_Annotation_Study_Round_3'}">--}%
                %{--<th>Annotation</th>--}%
            %{--</g:if>--}%
            %{--<g:elseif test="${imageList?.study?.studyName?.first() == 'Prostate_Annotations_Study_Review' || imageList?.study?.studyName?.first() == 'Imperial_Image_Annotation_Study_Review' || imageList?.study?.studyName?.first() == 'Bladder_Prediction_Review_Study'}">--}%
                %{--<th>Review</th>--}%
            %{--</g:elseif>--}%

        </tr>
        </thead>
        <tbody>
        <g:each in="${imageList}" status="i" var="image">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <g:if test="${image?.study?.studyName == 'Megakaryocyte_Prediction_Validation'}">
                    <td>${fieldValue(bean: image, field: "study")}</td>

                    <td>|||||</td>

                    <td><g:link controller="annotation" action="viewImageOnOS" params="['imageId': image.id, 'annotatorId':annotatorId]" target="_blank"><i class="glyphicon glyphicon-eye-open"></i> View</g:link></td>
                </g:if>
                <g:else>
                    <td>${fieldValue(bean: image, field: "study")}</td>

                    <td>${fieldValue(bean: image, field: "multiplexImageIdentifier")}</td>

                    <td><g:link controller="annotation" action="showViewImageOnOS" params="['imageId': image.id, 'annotatorId':annotatorId]"><i class="glyphicon glyphicon-eye-open"></i> View</g:link></td>

                    <g:if test="${!image?.annotations?.findAll {it?.status}?.isEmpty()}">
                        <g:if test="${image?.annotations?.findAll {it?.status}?.last()?.status == 'complete'}">
                            <td style="color: forestgreen">Complete</td>
                        </g:if>
                    </g:if>
                    <g:else>
                        <td style="color: red">Incomplete</td>
                    </g:else>
                </g:else>

            </tr>
        </g:each>
        </tbody>
    </table>
</section>

<hr>
<center><g:link controller="userSurvey" action="create"> Please complete this survey at the end of annotation task</g:link></center>
<hr>

<br>
<br>
<br>

</body>
</html>