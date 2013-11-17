<% import grails.persistence.Event %>
<%=packageName%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row">		
			<div class="box col-sm-12">
				<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			</div>
		</div>

		<g:if test="\${flash.message}">
			<div class="row">
				<div class="col-sm-12">
					<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">×</button>
							\${flash.message}
					</div>
				</div>
			</div>
		</g:if>

		<div class="row">
			<div class="col-sm-12">
				<g:link class="btn btn-success" action="create"><i class="icon-plus "></i><g:message code="default.new.label" args="[entityName]" /></g:link>
			</div>
		</div>
		<br>
		<div class="row">		
			<div class="box col-sm-12">
				<div class="box-header" data-original-title>
					<h2><i class="icon-user"></i><span class="break"></span><g:message code="default.boxheader.label" args="[entityName]" /></h2>
					<div class="box-icon">
						<a href="#" class="btn-minimize"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
				<table class="table table-striped table-bordered bootstrap-datatable datatable">
				<thead>
					<tr>
					<%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
						allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
						props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) }
						Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
						props.eachWithIndex { p, i ->
							if (i < 6) {
								if (p.isAssociation()) { %>
						<th><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></th>
					<%      } else { %>
						<g:sortableColumn property="${p.name}" title="\${message(code: '${domainClass.propertyName}.${p.name}.label', default: '${p.naturalName}')}" />
					<%  }   }   } %>
						<th><g:message code="defaut.actions.label" default="Actions" /></th>
					</tr>
				</thead>
				<tbody>
				<g:each in="\${${propertyName}List}" status="i" var="${propertyName}">
					<tr class="\${(i % 2) == 0 ? 'even' : 'odd'}">
					<%  props.eachWithIndex { p, i ->
							if (i == 0) { %>
						<td><g:link action="show" id="\${${propertyName}.id}">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</g:link></td>
					<%      } else if (i < 6) {
								if (p.type == Boolean || p.type == boolean) { %>
						<td><g:formatBoolean boolean="\${${propertyName}.${p.name}}" /></td>
					<%          } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
						<td><g:formatDate date="\${${propertyName}.${p.name}}" format="yyyy-MM-dd" /></td>
					<%          } else { %>
						<td>\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
					<%  }  } %>

					<% if (i == props.size()-1){  %>
					<td class="center">
						<g:link class="btn btn-success" action="show" id="\${${propertyName}.id}"><i class="icon-zoom-in "></i></g:link>
						<g:link class="btn btn-info" action="edit" id="\${${propertyName}.id}"><i class="icon-edit "></i>  </g:link>
					</td>
			  		<% } }  %>
					</tr>
				</g:each>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	</body>
</html>
