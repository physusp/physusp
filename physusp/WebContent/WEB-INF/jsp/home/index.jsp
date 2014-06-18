<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width" />
	<link rel="stylesheet"
		href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
	<link rel="stylesheet" href="<c:url value="/css/home.css"/>" />	
	<title>GEDAE-LaB</title>
</head>
<body>
	<div id="top">
		<div class="container">
			<a href="<c:url value="/"/>"><img class="img-responsive" src="<c:url value="/img/logo.png"/>" alt="logo"/></a>
		</div>
	</div>
	<div class="container">
		
		<div id="content" class="row text-justify">
			The estimative of the energy system
			contributions may be useful in the evaluation of the professional and
			recreational athletes, in the determination of the energy cost in the
			daily life, and following the history of a disease. However, in order
			to determine the energy system contributions, often are required the
			use of some mathematical functions that may be considered unusual to
			many scientists. In addition, the need for specific software can
			become these physiological analyses relatively more expensive. The
			GEDAE-LaB is an open source software designed based on the concept of
			the usability to help the scientists to estimate the energy system
			contributions. In this first version, the GEDAE-LaB utilizes
			mathematical analyzes of some physiological variables (i.e. oxygen
			uptake and blood lactate accumulation) to determine the contribution
			of aerobic, anaerobic lactic, and anaerobic alactic metabolisms. It
			does not require any registration or login. In addition, as the
			GEADE-LaB (version 1.0) is online available software, you can use it
			anywhere in the world where you have internet access!
		</div>
		
		<div class="row text-center">
			<a class="btn btn-primary btn-lg" href="<c:url value="/esc"/>">Energy Systems Contributions Calculator</a>
		</div>
	</div>
</body>
</html>