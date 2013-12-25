<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<!-- <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">-->
		
		<r:require modules="bootstrap"/>
		<r:require modules="menu"/>
		
		
		
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
		<!-- <div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>-->
		 <!-- Fixed navbar -->
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Janpix - Paciente</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav  main-menu">
            <li><g:link controller="dashboard" action="index">
            	<span class="glyphicon glyphicon-home">&nbsp;Inicio</span>
            	</g:link></li>
            <li><g:link controller="patient" action="listPatientStudies">
            	<span class="glyphicon glyphicon-file">&nbsp;Estudios Cargados</span>
            	</g:link></li>
            <li><a href="#">
            <span class="glyphicon glyphicon-check">&nbsp;Entidades de Confianza</span>
            </a></li>
         </ul>
         <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              	<span class="glyphicon glyphicon-user">&nbsp;<jx:currentUser/></span>
              	<b class="caret"></b>
              </a>
              <ul class="dropdown-menu">
                <li><a href="#">Configuración</a></li>
                <li><g:link controller="login" action="logout">Salir</g:link></li>
              </ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
    
     <div class="container">
     	<br><br><br>
		<g:layoutBody/>
		
	</div>
	
	<div class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<g:javascript library="application"/>
		<r:layoutResources />
	</body>
</html>
