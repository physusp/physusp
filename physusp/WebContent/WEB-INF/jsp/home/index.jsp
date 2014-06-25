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
		<div id="top">
			<div class="container">
				<a href="<c:url value="/"/>"><img class="img-responsive" src="<c:url value="/img/logo.png"/>" alt="logo" /></a>
			</div>
		</div>
		<div class="container">
			<div id="content" class="row">
				<div class="col-xs-12 text-justify">
					The GEDAE-LaB is an open source software for mathematical analyses designed to help the exercise
					physiologists. In this first version, the GEDAE-LaB (version 1.0) allows to estimate the energy
					expenditure and energy system contributions using the measurement of the oxygen uptake and blood
					lactate accumulation during exercise. It does not require any registration or login. In addition,
					as the GEDAE-LaB is an online available software, you can use it anywhere in the world where you
					have internet access!
				</div>
			</div>
	
			<div class="row text-center">
				<a class="btn btn-primary btn-lg" href="<c:url value="/esc"/>">Energy Systems Contributions Calculator</a>
			</div>
		</div>
	</body>
</html>