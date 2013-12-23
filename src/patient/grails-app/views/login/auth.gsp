<html>
<head>
	<meta name='layout' content='login'/>
	<title>Ingresar al sistema</title>
</head>
<body>
<div class="container">
		<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
		</g:if>
					
      <form action="login" method='POST' id='loginForm' class="form-signin" role="form">      
        <h2 class="form-signin-heading">Janpix - Pacientes</h2>
        <input name="user" id="user" type="text" class="form-control" placeholder="CUIS" required autofocus>
        <input name="pass" id="pass" type="password" class="form-control" placeholder="Contraseña" required>
        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Recordarme
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Ingresar</button>
      </form>

  </div> <!-- /container -->
    
</body>
</html>
