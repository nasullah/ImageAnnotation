<html>
<head>
	<meta name="layout" content="main"/>
	<title><g:message code="springSecurity.login.title"/></title>
	<g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
	<g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
</head>

<body>
<div class="container" style="margin-top: 20px;">
<section id="login" class="first">
<div class="row">
	<div class="col-md-3"></div>
	<div class="col-md-6">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4><g:message code="springSecurity.login.header"/></h4>
			</div>
			<div class="panel-body">
				<g:if test='${flash.message}'>
					<div class="alert alert-danger">${flash.message}</div>
				</g:if>
				<form role="form" id='loginForm' action="${postUrl ?: '/login/authenticate'}" method='POST' autocomplete='off'>
					<div class="form-group">
						<label for='username' class="control-label">
							<g:message code="springSecurity.login.username.label"/>:
						</label>
						<div class="controls">
							<input type='text' class='form-control col-md-4' name="${usernameParameter ?: 'username'}" id="username"/>
						</div>
					</div>

					<div class="form-group">
						<label for='password' class="control-label">
							<g:message code="springSecurity.login.password.label"/>:
						</label>
						<div class="controls">
							<input type='password' class='form-control col-md-4' name="${passwordParameter ?: 'password'}" id="password"/>
						</div>
					</div>

					%{--<div id="remember_me_holder" class="form-group">--}%
						%{--<label for='remember_me' class="control-label">--}%
							%{--<g:message code="springSecurity.login.remember.me.label"/>--}%
						%{--</label>--}%
						%{--<div>--}%
							%{--<bs-checkbox name="${rememberMeParameter}" checked="${hasCookie}"></bs-checkbox>--}%
						%{--</div>--}%
						%{--<div class="controls">--}%
							%{--<g:checkBox class="form-control col-md-4" name="${rememberMeParameter}" value="${hasCookie}" />--}%
						%{--</div>--}%
					%{--</div>--}%

					<div class="form-group pull-right">
						<g:link controller="home" action="index" class="btn btn-default">Cancel</g:link>
						<input type='submit' id="submit" class="btn btn-primary" value='${message(code: "springSecurity.login.button")}'/>
					</div>
				</form>
			</div>
		</div>

	</div>
	<div class="col-md-3"></div>
</div>
</section>
</div>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['username'].focus();
	})();
	// -->
</script>

</body>
</html>
