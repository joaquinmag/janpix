<!-- start: Main Menu -->
<div id="sidebar-left" class="col-lg-2 col-sm-1">
	
	<!-- <input type="text" class="search hidden-sm" placeholder="..." /> -->
	
	<div class="nav-collapse sidebar-nav collapse navbar-collapse bs-navbar-collapse">
		<ul class="nav nav-tabs nav-stacked main-menu">
			<li><g:link mapping="dashboard"><i class="icon-bar-chart"></i><span class="hidden-sm"> Dashboard</span></g:link></li>	
			<li>
				<a class="dropmenu" href="#"><i class="icon-user"></i><span class="hidden-sm"> Pacientes</span></a>
				<ul>
					<li><g:link mapping="patients"><i class="icon-table"></i><span class="hidden-sm"> Ver pacientes</span></g:link></li>
					<li><g:link class="btn-settings" controller="patient" action="create"><i class="icon-plus "></i><span class="hidden-sm"> Crear nuevo paciente</span></g:link></li>
				</ul>
			</li>
		</ul>
	</div>
</div>
<!-- end: Main Menu -->
