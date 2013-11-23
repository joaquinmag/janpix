	<!-- start: Header -->
	<header class="navbar">
		<div class="container">
			<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".sidebar-nav.nav-collapse">
			      <span class="icon-bar"></span>
			      <span class="icon-bar"></span>
			      <span class="icon-bar"></span>
			</button>
			<a id="main-menu-toggle" class="hidden-xs open"><i class="icon-reorder"></i></a>		
				<g:link mapping="dashboard" class="navbar-brand col-lg-2 col-sm-1 col-xs-12"><span>Consultorio</span></g:link>
			<!-- start: Header Menu -->
			<div class="nav-no-collapse header-nav">
				<ul class="nav navbar-nav pull-right">
					<!-- start: User Dropdown -->
					<li>
						<a class="btn">
								<span class="hello"><g:message code="main.welcome.message" /></span>
								<span class="name">${ ctx.user.name }</span>
						</a>
					</li>
					<li><g:link controller="logout" class="btn"><i class="icon-off"></i> <g:message code="main.logout.label" /></g:link></li>
					<!-- end: User Dropdown -->
				</ul>
			</div>
			<!-- end: Header Menu -->
			
		</div>	
	</header>
	<!-- end: Header -->
