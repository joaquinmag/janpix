
<%@ page import="ar.com.healthentity.Patient" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Dashboard</title>
		<r:require module="fullcalendar" />
		<r:script>
			$(document).ready( function() {
				var date = new Date();
				var d = date.getDate();
				var m = date.getMonth();
				var y = date.getFullYear();
				$('.calendar-small').fullCalendar({
					monthNames: ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'],
					monthNamesShort: ['Ene','Feb','Mar','Abr','May','Jun','Jul','Ago','Sep','Oct','Nov','Dic'],
					dayNames: ['Domingo','Lunes','Martes','Miircoles','Jueves','Viernes','Sabado'],
					dayNamesShort: ['Dom','Lun','Mar','Mii','Jue','Vie','Sab'],
					header: {
						right: 'title',
						left: 'prev,next,today'
					},
					defaultView: 'month',
					editable: true,
					events: [
						{
							title: 'Congreso',
							start: new Date(date.getFullYear(), date.getMonth(), date.getDate() -17),
							allDay: true
						},
						{
							title: 'Paciente Rubén Gómez',
							start: new Date(date.getFullYear(), date.getMonth(), date.getDate() -15, 11,30,0),
							end: new Date(date.getFullYear(), date.getMonth(), date.getDate()-10, 12,30,0)
						},
						{
							id: 999,
							title: 'Paciente Mauro Álvarez',
							start: new Date(date.getFullYear(), date.getMonth(), date.getDate() -9, 8,0,0),
							end: new Date(date.getFullYear(), date.getMonth(), date.getDate() -9, 10,0,0),
							allDay: false
						},
						{
							id: 999,
							title: 'Paciente Mauro Álvarez',
							start: new Date(date.getFullYear(), date.getMonth(), date.getDate() -2, 8,0,0),
							end: new Date(date.getFullYear(), date.getMonth(), date.getDate() -2, 10,0,0),
							allDay: false
						},
						{
							title: 'Paciente Laura Sanchez',
							start: new Date(date.getFullYear(), date.getMonth(), date.getDate(), 11,30,0),
							end: new Date(date.getFullYear(), date.getMonth(), date.getDate(), 12,30,0),
							allDay: false
						},
						{
							title: 'Paciente Jorge Iglesias',
							start: new Date(date.getFullYear(), date.getMonth(), date.getDate(), 14,00,0),
							end: new Date(date.getFullYear(), date.getMonth(), date.getDate(), 15,30,0),
							allDay: false
						},
						{
							title: 'Paciente Juan Ignacio Martinez',
							start: new Date(date.getFullYear(), date.getMonth(), date.getDate() + 1, 19,30,0),
							end: new Date(date.getFullYear(), date.getMonth(), date.getDate() + 1, 22,30,0),
							allDay: false
						},
						{
							title: 'Evento',
							start: new Date(date.getFullYear(), date.getMonth(), date.getDate() + 10, 14,30,0),
							end: new Date(date.getFullYear(), date.getMonth(), date.getDate() + 11, 12,30,0),
							allDay: false
						}
					],
					dayClick: function(date, allDay, jsEvent, view) {
						
						$('.calendar-details > .events').html('');
						
						var weekday=new Array(7);
							weekday[0]="Domingo";
							weekday[1]="Lunes";
							weekday[2]="Martes";
							weekday[3]="Miércoles";
							weekday[4]="Jueves";
							weekday[5]="Viernes";
							weekday[6]="Sábado";
						
						var month=new Array();
							month[0]="Enero";
							month[1]="Febrero";
							month[2]="Marzo";
							month[3]="Abril";
							month[4]="Mayo";
							month[5]="Junio";
							month[6]="Julio";
							month[7]="Agosto";
							month[8]="Septiembre";
							month[9]="Octubre";
							month[10]="Noviembre";
							month[11]="Diciembre";
							
						var date2 = new Date(date.getFullYear(), date.getMonth(), date.getDate()+1);
						var todaysEvents = $('.calendar-small').fullCalendar('clientEvents', function(event) {
						
							function pad(n){return n<10 ? '0'+n : n}
							
							if (event.start >= date && event.start < date2) {
								
								var title = event.title;
								var start = event.start;
								var end = event.end;
								
								$('.calendar-details > .day').html(weekday[date.getDay()]);
								$('.calendar-details > .date').html(month[date.getMonth()] + ' ' + date.getDate());
								
								if(event.allDay) {
									
									$('.calendar-details > .events').append(
										'<li>'
										+ title 
										+ ' - todo el día' 
										+ '</li>');
									
								} else {
									
									$('.calendar-details > .events').append(
										'<li>'
										+ title 
										+ ' - ' 
										+ start.getHours() 
										+ ':'  
										+ pad(start.getMinutes())
										+ ' - '
										+ end.getHours() 
										+ ':'  
										+ pad(end.getMinutes())
										+'</li>');
									
								}
							
							} else if (date >= event.start && date <= event.end) {
								
								var title = event.title;
								var start = event.start;
								var end = event.end;
								
								$('.calendar-details > .day').html(weekday[date.getDay()]);
								$('.calendar-details > .date').html(month[date.getMonth()] + ' ' + date.getDate());
								$('.calendar-details > .events').append(
									'<li>'
									+ title 
									+ ' - '
									+ month[start.getMonth()] + ' ' + start.getDate()
									+ ' ' 
									+ start.getHours() 
									+ ':'  
									+ pad(start.getMinutes())
									+ ' - '
									+ month[end.getMonth()] + ' ' + end.getDate()
									+ ' '
									+ end.getHours() 
									+ ':'  
									+ pad(end.getMinutes())
									+'</li>');	
																							
							} else {
								
								$('.calendar-details > .day').html(weekday[date.getDay()]);
								$('.calendar-details > .date').html(month[date.getMonth()] + ' ' + date.getDate());
																		
							}	
						
						});
					
						//count li elements
						
						if ( $('.calendar-details ul li').length == 0 ) {
							$('.calendar-details > .events').html('<li>no hay eventos :)</li>');
						}

					}
				});
			});
		</r:script>
	</head>
	<body>
	<g:if test="${!ctx?.esConsultorio}">
		<div class="row">
			<div class="col-lg-12" style="text-align: center; margin-bottom: 50px;">
				<img src="images/centro.png" style="height: 200px" />
			</div>
		</div>
	</g:if>	
		<div class="row">
			<div class="col-lg-6">
				<div class="box calendar">
					<div class="calendar-details">
						<div class="day">Viernes</div>
						<div class="date">27 Diciembre</div>
						<g:if test="${ctx?.esConsultorio}">
							<ul class="events">
								<li>5 pacientes</li>
							</ul>
						</g:if>
						<div class="add-event">
							<i class="icon-plus"></i>
							<input type="text" class="new event" value="">
						</div>		
					</div>	
					<div class="calendar-small"></div>
					<div class="clearfix"></div>
				</div>
			</div><!--/col-->
			<g:if test="${!ctx?.esConsultorio}">
				<div class="col-lg-6">
					<div class="smallstat box">
						<i class="icon-user-md green"></i>
						<span class="title">Cantidad de turnos faltantes</span>
						<span class="value">15</span>
					</div>
				</div>
			</g:if>
		</div>
	</body>
</html>
