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
        					<li><a href="about">About us</a></li>
        					<li><a href="reportbug">Report a bug</a></li>
        					<li><a href="https://github.com/physusp/physusp/">View on GitHub</a></li>
        				</ul>
       				</div>
				</div>
			</nav>
			<div class="row body-description-row">
				<div class="col-xs-6 text-justify">
					The GEDAE-LaB is an open source software designed to estimate the energy expenditure and energy system contributions during the exercise using the measurement of oxygen uptake and the blood lactate accumulation. 
				</div>
				<div class="col-xs-6 text-center">
					<img src="<c:url value="/img/result.png"/>" class="img-responsive center-block" />
				</div>
			</div>
			<div class="row start-btn-row">
				<div class="col-xs-6 text-justify">
					<h3><u>Citing GEDAE-LaB:</u></h3>
					<a href="http://journals.plos.org/plosone/article?id=10.1371/journal.pone.0145733">Bertuzzi et al. (2016). GEDAE-LaB: A Free Software to Calculate the Energy System Contributions during Exercise. PLoS ONE 11 (1): e0145733.</a>
				</div>
				<div class="col-xs-6 text-center">
					<div class="well start-container">
						<a class="btn btn-primary btn-start btn-lg btn-block" href="<c:url value="/esc"/>">Start</a>
					</div>
				</div>
			</div>
			
		    <div class="row footer-row">
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
		<script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		
		  ga('create', 'UA-63575823-1', 'auto');
		  ga('send', 'pageview');
		
		</script>
	</body>
</html>