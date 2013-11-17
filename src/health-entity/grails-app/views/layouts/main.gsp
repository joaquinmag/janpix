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
		
		<r:require modules="theme"/>
		
		<r:require modules="application"/>
		
		<g:setProvider library="jquery"/>
		
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
		<g:render template="/header/header" />
	
	
	<div class="container">
		<div class="row">
			<g:render template="/sidebar/sidebar" />	

			<!-- start: Content -->
			<div id="content" class="col-lg-10 col-sm-11">
		
					<g:layoutBody/>
			</div>
			<!-- end: Content -->
				
		</div><!--/row-->
	</div> <!-- /container -->
		
		<!-- Modal -->
		<div class="modal fade" id="myModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">×</button>
						<h3><g:message code="defailt.modal.notification.message" default="Notification" /></h3>
					</div>
					<div class="modal-body" id="modalBody">
						<p>...</p>
					</div>
					<div class="modal-footer">
						<a href="#" class="btn" data-dismiss="modal"><g:message code="defailt.modal.close.message" default="Close" /></a>
						<a href="#" class="btn btn-primary"><g:message code="defailt.modal.accept.message" default="Ok" /></a>
					</div>
				</div>
			</div>
		</div>
		
		<div class="clearfix"></div>
		
		<footer>
			<p>
				<span style="text-align:left;float:left">&copy; 2013 FIUBA</span>
			</p>

		</footer>

		<r:layoutResources />
	</body>
</html>
