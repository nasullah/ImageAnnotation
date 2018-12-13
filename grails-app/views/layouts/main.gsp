<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:stylesheet src="application.css"/>
    <g:layoutHead/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
</head>
<body>
    <div class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container" style="min-width: 1400px;">
            <div class="navbar-header">

            </div>
            <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
                <ul class="nav navbar-nav navbar-left pull-left">

                    <a class="navbar-brand" href="/AIDA"><span class="glyphicon glyphicon-home"></span>
                        AIDA
                    </a>

                    <li class="">
                        <a href="https://imageannotation.nds.ox.ac.uk:8443/aidademo" target="_blank">
                            <i class="glyphicon glyphicon-play-circle"></i>
                            AIDA Demo
                        </a>
                    </li>

                    <sec:ifLoggedIn>
                        <li class="">
                            <g:if test="${sec?.username()?.toString()?.contains('.')}">
                                <g:link controller="login" action="auth"><span class="glyphicon glyphicon-user"></span> ${sec?.username()?.toString()?.substring(0, sec?.username()?.toString()?.lastIndexOf('.'))?.capitalize()}</g:link>
                            </g:if>
                            <g:else>
                                <g:link controller="login" action="auth"><span class="glyphicon glyphicon-user"></span> ${sec?.username()?.capitalize()}</g:link>
                            </g:else>
                        </li>

                        <sec:ifAnyGranted roles="ROLE_ADMIN">
                            <li class="">
                                <a href="${createLink(uri: '/study/index')}">
                                    <i class="glyphicon glyphicon-list"></i>
                                    Studies
                                </a>
                            </li>

                            <li class="">
                                <a href="${createLink(uri: '/pathologyImage/create')}">
                                    <i class="glyphicon glyphicon-upload"></i>
                                    Upload Images
                                </a>
                            </li>

                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                    Image Annotation <b class="caret"></b>
                                </a>

                                <ul class="dropdown-menu" style="height: auto; max-height: 350px; width: 270px; overflow-x: hidden;">

                                    <li class="">
                                        <a href="${createLink(uri: '/multiplexImage/index')}">
                                            <i class="glyphicon glyphicon-ok-sign"></i>
                                            All Images
                                        </a>
                                    </li>

                                </ul>

                            </li>

                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                    <i class="glyphicon glyphicon-cog white"></i>
                                    Administration <b class="caret"></b>
                                </a>

                                <ul class="dropdown-menu" style="height: auto; max-height: 510px; width: 270px; overflow-x: hidden;">
                                    <li class="">
                                        <a tabindex="-1" href="#">
                                            <b>User Access Management</b></a>
                                    </li>

                                    <p>

                                    <li class="">
                                        <a href="${createLink(uri: '/role/create')}">
                                            <i class="glyphicon glyphicon-plus-sign"></i>
                                            Add Role
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/role')}">
                                            <i class="glyphicon glyphicon-search"></i>
                                            Search Role
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/user/create')}">
                                            <i class="glyphicon glyphicon-plus-sign"></i>
                                            Add User
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/user')}">
                                            <i class="glyphicon glyphicon-search"></i>
                                            Search User
                                        </a>
                                    </li>

                                    <li class="">
                                        <a tabindex="-1" href="#">
                                            <b>Dropdown List Management</b></a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/expert')}">
                                            <i class="glyphicon glyphicon-list"></i>
                                            Annotator
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/annotationStep')}">
                                            <i class="glyphicon glyphicon-list"></i>
                                            Annotation Step
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/annotationTask')}">
                                            <i class="glyphicon glyphicon-list"></i>
                                            Annotation Task
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/annotationTool')}">
                                            <i class="glyphicon glyphicon-list"></i>
                                            Annotation Tool
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/centre')}">
                                            <i class="glyphicon glyphicon-list"></i>
                                            Centre
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/imageType')}">
                                            <i class="glyphicon glyphicon-list"></i>
                                            Image Type
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/speciality')}">
                                            <i class="glyphicon glyphicon-list"></i>
                                            Speciality
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/studyType')}">
                                            <i class="glyphicon glyphicon-list"></i>
                                            Study Type
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/multiplexImage/loadImages')}">
                                            <i class="glyphicon glyphicon-upload"></i>
                                            Load small images
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="${createLink(uri: '/multiplexImage/configInterface')}">
                                            <i class="glyphicon glyphicon-ok-sign"></i>
                                            Config Interface
                                        </a>
                                    </li>

                                </ul>

                            </li>
                        </sec:ifAnyGranted>
                    </sec:ifLoggedIn>
                    <sec:ifNotLoggedIn>
                        <g:form controller="login" class="navbar-form navbar-left" >

                            <li class="active">
                                <g:link controller="login" action="auth"><span class="glyphicon glyphicon-log-in"></span>   Login</g:link>
                            </li>

                        </g:form>
                    </sec:ifNotLoggedIn>
                    <sec:ifLoggedIn>
                        <g:form controller="logout" action="index" class="navbar-form navbar-left" >

                            <li class="active">
                                %{--<g:link controller="logout" action="index"><span class="glyphicon glyphicon-log-out"></span>   Log out</g:link>--}%
                                <g:form controller="logout">
                                    <a href="#" onclick="document.forms[0].submit();" [0].submit();><span class="glyphicon glyphicon-log-out"></span> Log out</a>
                                </g:form>
                            </li>

                        </g:form>
                    </sec:ifLoggedIn>
                    <i class="fa grails-icon" style="position:absolute;top:5px;right:45px;bottom:5px">
                        <asset:image src="oxford-logo.png"/>
                    </i>
                </ul>

            </div>
        </div>
    </div>

    <g:layoutBody/>

    <footer style="position:absolute;bottom:0;width:100%;height:60px;   /* Height of the footer */background:#f0f0f0;">
        <div class="container" style="text-align:center">
            <div>
                <br/>
                &#169; University of Oxford 2018
            </div>
        </div>
    </footer>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

</body>
</html>
