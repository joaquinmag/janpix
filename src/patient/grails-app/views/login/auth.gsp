<html>
<head>
	<meta name='layout' content='login'/>
	<title>Ingresar al sistema</title>
</head>
<body>
<div class="container">
		
      <form action="login" method='POST' id='loginForm' class="form-signin" role="form">
      	<g:img dir="images" file="logo-janpix.png" height="160" style="margin-left: 70px;"/>      
        <h2 class="form-signin-heading">Janpix - Pacientes</h2>
        <g:if test='${flash.message}'>
			<div class="alert alert-danger">
				<strong>Error!.</strong>
				${flash.message}
			</div>
		</g:if>
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
