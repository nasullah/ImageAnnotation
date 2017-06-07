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
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="/#">
                    Image Annotation
                </a>
            </div>
            <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
                <ul class="nav navbar-nav navbar-right pull-right">

                    <li><a href="${createLink(uri: '/')}"><span class="glyphicon glyphicon-home"></span> Home</a></li>

                    <sec:ifLoggedIn>
                        <li class="">
                            <a href="${createLink(uri: '/study/index')}">
                                <i class="glyphicon glyphicon-list"></i>
                                Studies
                            </a>
                        </li>

                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                <i class="glyphicon glyphicon-pencil"></i>
                                Image Annotation <b class="caret"></b>
                            </a>

                            <ul class="dropdown-menu" style="height: auto; max-height: 350px; width: 270px; overflow-x: hidden;">

                                <li class="">
                                    <a href="${createLink(uri: '/annotation/list')}">
                                        <i class="glyphicon glyphicon-question-sign"></i>
                                        Un-annotated Images
                                    </a>
                                </li>

                                <li class="">
                                    <a href="${createLink(uri: '/annotation/list')}">
                                        <i class="glyphicon glyphicon-ok-sign"></i>
                                        Annotated Images
                                    </a>
                                </li>

                            </ul>

                        </li>

                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                <i class="glyphicon glyphicon-cog white"></i>
                                Administration <b class="caret"></b>
                            </a>

                            <ul class="dropdown-menu" style="height: auto; max-height: 350px; width: 270px; overflow-x: hidden;">
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

                            </ul>

                        </li>
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

                    %{--<g:pageProperty name="page.nav" />--}%
                    <i class="fa grails-icon">
                        <asset:image src="oxford-logo.png"/>
                    </i>
                </ul>

            </div>
        </div>
    </div>

    <g:layoutBody/>

    %{--<div class="footer" role="contentinfo"></div>--}%

    <footer class="footer">
        <div class="container" style="margin-bottom: 10px;">
            <div class="row text-center">
                &#169; University of Oxford 2017
            </div>
        </div>
    </footer>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

</body>
</html>
