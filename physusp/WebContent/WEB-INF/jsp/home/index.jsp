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
			<div class="row body-description-row">
				<div class="col-xs-12 text-justify">
					The GEDAE-LaB is an open source software for mathematical analysis designed to help the exercise physiologists. In this first version, the GEDAE-LaB (version 1.0) allows to estimate the energy expenditure and energy system contributions during the exercise using the measurement of oxygen uptake and the blood lactate accumulation. The GEDAE-LaB does not require any registration or login. In addition, as the GEDAE-LaB is an online available software, you can use it anywhere in the world if you have access to the internet!
				</div>
			</div>
	
			<div class="row text-center start-btn-row">
				<div class="well start-container">
					<a class="btn btn-primary btn-start btn-lg btn-block" href="<c:url value="/esc"/>">Start</a>
				</div>
			</div>

			<div class="row footer-row">
				<div class="col-xs-3">
					<img class="img-responsive center-block" src="<c:url value="/img/usp.png?v=2"/>" alt="University of São Paulo"/>
				</div>
				<div class="col-xs-3">
					<img class="img-responsive center-block" src="<c:url value="/img/eefe.png?v=2"/>" alt="Escola de Educação Física e Esportes - USP"/>
				</div>
				<div class="col-xs-3">
					<img class="img-responsive center-block" src="<c:url value="/img/ccslime.png?v=2"/>" alt="CCSL - Instituto de Matemática e Estatística - USP"/>
				</div>
				<div class="col-xs-3">
					<a id="git" href="https://github.com/physusp/physusp/">View on GitHub</a>
				</div>
			</div>
		</div>
	</body>
</html>