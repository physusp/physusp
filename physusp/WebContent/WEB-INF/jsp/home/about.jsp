<%--
Copyright 2014 António Miranda, Caio Valente, Igor Topcin, Jorge Melegati, Thales Paiva, Victor Santos

This file is part of PhysUSP.

PhysUSP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PhysUSP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PhysUSP. If not, see <http://www.gnu.org/licenses/>.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width" />
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
		<link rel="stylesheet" href="<c:url value="/css/home.css"/>" />
		<title>GEDAE-LaB</title>
	</head>
	<body>
		<div class="home-header">
			<div class="container text-center">
				<a href="<c:url value="/"/>"><img class="img-responsive" src="<c:url value="/img/logo.jpg"/>" alt="logo" /></a>
			</div>
			
		</div>
		<div class="container home-body">
			<nav class="navbar navbar-default">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-1">
					    	<span class="sr-only">Toggle navigation</span>
					    	<span class="icon-bar"></span>
					    	<span class="icon-bar"></span>
					    	<span class="icon-bar"></span>
					    </button>
					</div>
					<div class="collapse navbar-collapse" id="navbar-collapse-1">
      					<ul class="nav navbar-nav navbar-right">
        					<li><a href="<c:url value="/"/>">Home</a></li>
        					<li><a href="https://github.com/physusp/physusp/">View on GitHub</a></li>
        					<li><a href="reportbug">Report a bug</a></li>
        					<li><a href="about">About us</a></li>
        				</ul>
       				</div>
				</div>
			</nav>
			<div class="row">
				<div class="col-xs-12 text-justify about">
				
					<p>This software was developed inside University of São Paulo as a joint effort from School of Physical Education and Sport and Department of Computer Science.
					
					<h2>Coordinator</h2>
					<ul>
						<li>Rômulo Bertuzzi, Associate Professor School of Physical Education and Sport</li>
					</ul>
					
					<h2>Software team</h2>
					<ul>
						<li><b>Supervisor:</b> Alfredo Goldman, Associate Professor Department of Computer Science</b>
						<li>António Miranda</li> 
						<li>Caio Valente</li>
						<li>Igor Topcin</li>
						<li>Jorge Melegati</li> 
						<li>Thales Paiva</li> 
						<li>Victor Santos</li>
					</ul>
					
					<h2>Collaboration</h2>
					<ul>
						<li>Salomão Bueno</li>
					</ul>
					
					<h2>Institutions</h2>
					<br/>
					<div class="row">
						<div class="col-xs-4">
							<img class="img-responsive center-block" src="<c:url value="/img/usp.png?v=2"/>" alt="University of São Paulo"/>
						</div>
						<div class="col-xs-4">
							<img class="img-responsive center-block" src="<c:url value="/img/eefe.png?v=2"/>" alt="Escola de Educação Física e Esportes - USP"/>
						</div>
						<div class="col-xs-4">
							<img class="img-responsive center-block" src="<c:url value="/img/ccslime.png?v=2"/>" alt="CCSL - Instituto de Matemática e Estatística - USP"/>
						</div>
					</div>
				</div>
		</div>
	</body>
</html>