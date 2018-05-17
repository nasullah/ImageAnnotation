<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>ADIA</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
<sec:ifLoggedIn>
    <h3><center><i class="glyphicon glyphicon-dashboard"></i> Studies</center></h3>
    <hr>
</sec:ifLoggedIn>
<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_SHARE_HEMPATH">
    <div class="container-fluid">
        <section id="info">
            <div class="equal">
                <div class="col-md-3">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="glyphicon glyphicon-tasks"></i> Hempath
                        </div>
                        <div class="panel-body">
                            <g:link controller="multiplexImage" action="yourImageList" params="[study:'Hempath']"><i class="glyphicon glyphicon-list"></i> Image List</g:link>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="glyphicon glyphicon-tasks"></i> Megakaryocyte Detection
                        </div>
                        <div class="panel-body">
                            <g:link controller="multiplexImage" action="sharedImageList" params="[study:'Megakaryocyte_Detection']"><i class="glyphicon glyphicon-list"></i> Image List</g:link>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <hr>
</sec:ifAnyGranted>
<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_SHARE_PROSTATE">
    <div class="container-fluid">
        <section id="info">
            <div class="equal">
                <div class="col-md-3">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="glyphicon glyphicon-tasks"></i> Prostate ROI Marking
                        </div>
                        <div class="panel-body">
                            <g:link controller="multiplexImage" action="yourImageList" params="[study:'Prostate_Marking_ROI']"><i class="glyphicon glyphicon-list"></i> Image List</g:link>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="glyphicon glyphicon-tasks"></i> Prostate Cancer Annotations
                        </div>
                        <div class="panel-body">
                            <g:link controller="multiplexImage" action="yourImageList" params="[study:'Prostate_Cancer_Annotations ']"><i class="glyphicon glyphicon-list"></i> Image List</g:link>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="glyphicon glyphicon-tasks"></i> SSM Project
                        </div>
                        <div class="panel-body">
                            <g:link controller="multiplexImage" action="sharedImageList" params="[study:'SSM Project']"><i class="glyphicon glyphicon-list"></i> Image List</g:link><br><br>
                            <g:link controller="label" action="displayImage"><i class="glyphicon glyphicon-tag"></i> Label Images</g:link><br><br>
                            <g:link controller="label" action="reviewLabels"><i class="glyphicon glyphicon-ok-sign"></i> Review Labels</g:link>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <hr>
</sec:ifAnyGranted>
<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_FAT_TISSUE">
    <div class="container-fluid">
        <section id="info">
            <div class="equal">
                <div class="col-md-3">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="glyphicon glyphicon-tasks"></i> Fat Tissue
                        </div>
                        <div class="panel-body">
                            <g:link controller="multiplexImage" action="yourImageList" params="[study:'Fat_Tissue']"><i class="glyphicon glyphicon-list"></i> Image List</g:link>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <hr>
</sec:ifAnyGranted>
<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_IMPERIAL">
    <div class="container-fluid">
        <section id="info">
            <div class="equal">
                <div class="col-md-3">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="glyphicon glyphicon-tasks"></i> Imperial Image Annotation
                        </div>
                        <div class="panel-body">
                            <g:link controller="multiplexImage" action="yourImageList" params="[study:'Imperial_Image_Annotation']"><i class="glyphicon glyphicon-list"></i> Image List</g:link>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <hr>
</sec:ifAnyGranted>
<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_SHARE_KIDNEY">
    <div class="container-fluid">
        <section id="info">
            <div class="equal">
                <div class="col-md-3">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="glyphicon glyphicon-tasks"></i> Kidney Image Annotation
                        </div>
                        <div class="panel-body">
                            <g:link controller="multiplexImage" action="sharedImageList" params="[study:'Kidney_Image_Annotation']"><i class="glyphicon glyphicon-list"></i> Image List</g:link>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <hr>
</sec:ifAnyGranted>
</body>
</html>
