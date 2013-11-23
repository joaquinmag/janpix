<html>
<head>
	<meta name='layout' content='login'/>
	<title><g:message code="springSecurity.login.title"/></title>
	<r:script>
		<!--
		(function() {
			document.forms['loginForm'].elements['j_username'].focus();
		})();
		// -->
	</r:script>
</head>
<body>
		<div class="container">
		<div class="row">
					
			<div class="row">
				<div class="login-box">
					<h2><g:message code="springSecurity.login.header"/></h2>
					<g:if test='${flash.message}'>
						<div class='login_message'>${flash.message}</div>
					</g:if>
					<form action='${postUrl}' method='POST' id='loginForm' class="form-horizontal" autocomplete='off'>
						<fieldset>
							<input class="input-large col-xs-12" name="j_username" id="username" type="text" placeholder="type username" style="color: black !important"/>

							<input class="input-large col-xs-12" name="j_password" id="password" type="password" placeholder="type password" style="color: black !important"/>

							<div class="clearfix"></div>
							
							<label class="remember" for="remember"><input type="checkbox" id="remember" name='${rememberMeParameter}' <g:if test='${hasCookie}'>checked='checked'</g:if>/><g:message code="springSecurity.login.remember.me.label"/></label>
							
							<div class="clearfix"></div>
							
							<button type="submit" class="btn btn-primary col-xs-12" value='${message(code: "springSecurity.login.button")}'>Login</button>
						</fieldset>	

					</form>
					<hr>
					<h3>Forgot Password?</h3>
					<p>
						No problem, <a href="login.html#">click here</a> to get a new password.
					</p>	
				</div>
			</div><!--/row-->
			
				</div><!--/row-->		
		
	</div><!--/container-->
</body>
</html>
