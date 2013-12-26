<html>
<head>
	<meta name='layout' content='login'/>
	<title>Ingreso ${nameHE}</title>
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
					<h2 style="text-align: center"><strong><g:message code="springSecurity.login.header"/> ${nameHE}</strong></h2>
					<g:if test='${flash.message}'>
						<div class='login_message'>${flash.message}</div>
					</g:if>
					<form action='${postUrl}' method='POST' id='loginForm' class="form-horizontal" autocomplete='off'>
						<fieldset>
							<label for="username">Usuario</label>
							<input class="input-large col-xs-12" name="j_username" id="username" type="text" placeholder="Usuario" style="color: black !important"/>
							<label for="password">Contraseña</label>
							<input class="input-large col-xs-12" name="j_password" id="password" type="password" placeholder="Contraseña" style="color: black !important"/>

							<div class="clearfix"></div>
							
							<label class="remember" for="remember"><input type="checkbox" id="remember" name='${rememberMeParameter}' <g:if test='${hasCookie}'>checked='checked'</g:if>/>Recordar usuario</label>
							
							<div class="clearfix"></div>
							
							<button type="submit" class="btn btn-primary col-xs-12" value='${message(code: "springSecurity.login.button")}'>Ingresar</button>
						</fieldset>	

					</form>
					<hr>
					<h3>Recordar contraseña</h3>
					<p>
						Hacé click <a href="login.html#">acá</a> para recuperar tu contraseña.
					</p>	
				</div>
			</div><!--/row-->
			
				</div><!--/row-->		
		
	</div><!--/container-->
</body>
</html>
