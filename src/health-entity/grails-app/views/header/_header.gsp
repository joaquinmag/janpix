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
					<li class="dropdown">
						<a class="btn account dropdown-toggle" data-toggle="dropdown" href="index.html#">
							<div class="avatar"><img src="${resource(dir: 'theme/img', file: 'avatar.jpg')}" alt="Avatar"></div>
							<div class="user">
								<span class="hello"><g:message code="main.welcome.message" /></span>
								<span class="name">${ ctx.user.name }</span>
							</div>
						</a>
						<ul class="dropdown-menu">
							<li class="dropdown-menu-title">
								
							</li>
							<li><a href="index.html#"><i class="icon-user"></i> <g:message code="main.profile.label" /></a></li>
							<li><a href="index.html#"><i class="icon-cog"></i> <g:message code="main.settings.label" /></a></li>
							<li><a href="login.html"><i class="icon-off"></i> <g:message code="main.logout.label" /></a></li>
						</ul>
					</li>
					<!-- end: User Dropdown -->
				</ul>
			</div>
			<!-- end: Header Menu -->
			
		</div>	
	</header>
	<!-- end: Header -->
