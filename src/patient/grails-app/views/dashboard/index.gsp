<%@ page import="ar.com.janpix.patient.PatientCommand" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entity" value="${patientInstance}" />
		<title>Escritorio ${entity}</title>
	</head>
	<body>
		<div class="row">		
			<div class="box col-sm-12">
				<h1>
					Bienvenido ${entity}
				</h1>
			</div>
		</div>

		<div class="row">	
			<div class="col-sm-12">
			
				<div class="jumbotron">
  					<div class="container">
						<h3>¿Qué desea hacer?</h3>
						<div class="row">
  							<div class="col-sm-6 col-md-4">
    							<g:img dir="images" file="study-icon.png" width="128" /><br>
    							<g:link class="btn btn-warning btn-large" controller="patient" action="listPatientStudies"> 
    								Ver Estudios Cargados
    							</g:link>
    						</div>
    						<div class="col-sm-6 col-md-4">
    							<g:img dir="images" file="center-icon.png" width="128" /><br>
    							<a class="btn btn-warning btn-large"> 
    								Ver Entidades de Confianza
    							</a>
  							</div>
  						</div><!-- row -->
  					</div><!-- container -->
  				</div><!-- jumbotron -->
  				
			</div>
		</div>			
	</body>
</html>
