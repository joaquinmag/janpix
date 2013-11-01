<% import grails.persistence.Event %>
<%=packageName%>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
	<div class="row-fluid">
			<div class="span12">
				<g:link class="btn btn-small btn-success" action="create"><i class="icon-plus "></i><g:message code="default.new.label" args="[entityName]" /></g:link>
				<g:link class="btn btn-small" action="index"><i class="icon-reorder"></i><g:message code="default.list.label" args="[entityName]" /></g:link>
			</div>
		</div>

		<div class="row-fluid">		
			<div class="box span12">
				<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			</div>
		</div>

		<g:if test="\${flash.message}">
			<div class="row-fluid">
				<div class="span12">
					<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">×</button>
							\${flash.message}
					</div>
				</div>
			</div>
		</g:if>

		<!--Contenido-->
		<div class="row-fluid">	
			<div class="box span7 show-view">
			<%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
					allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
					props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) }
					Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
					props.each { p -> %>				
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" />
							</span>
						</div>
						<div class="span9">
							<g:if test="\${${propertyName}?.${p.name}}">
							<span class="view-text">
							<%  if (p.oneToMany || p.manyToMany) { %>
								<g:link controller="${p.referencedDomainClass?.propertyName}" action="index" params="[${domainClass.propertyName}Id:${propertyName}?.id]"  class="btn btn-info">
								<g:message code="${domainClass.propertyName}.${p.name}.show.label" default="View One-to-Many" />
								</g:link>

							<%  } else if (p.type == Boolean || p.type == boolean) { %>
								<g:formatBoolean boolean="\${${propertyName}?.${p.name}}" />
							<%  } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
								<g:formatDate date="\${${propertyName}?.${p.name}}" />
							<%  } else if (!p.type.isArray()){ %>
								<g:fieldValue bean="\${${propertyName}}" field="${p.name}"/>
							<%  } %>
							</span>
							</g:if>		
						</div>
					</div>
			<%  } %>
			</div>
			<!-- Menu -->
			<div class="box span5 show-menu">
				<h3><g:message code="default.actions.label" default="Actions"/></h3>
				<g:form url="[resource:${propertyName}, action:'delete']" method="DELETE">
					<g:hiddenField name="id" value="\${${propertyName}?.id}" />
					<p>
						<g:link class="btn btn-info" action="edit" resource="\${${propertyName}}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					</p>
					<p>
						<g:actionSubmit class="btn btn-danger" action="delete" value="\${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('\${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</p>
				</g:form>
			</div>
		</div>
	</body>
</html>
