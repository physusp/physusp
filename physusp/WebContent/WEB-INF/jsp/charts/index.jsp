<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>PhysUSP</title>
	</head>
	<body>
		<form id="data">
			<input type="text" name="aerobic" />
			<input type="text" name="anaerobicLactic" />
			<input type="text" name="anaerobicAlactic" />
			<button type="submit">Send</button>
		</form>
		<div id="results">

		</div>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script type="text/javascript" src="/Physusp/js/lib/highcharts.js"></script>
		<script type="text/javascript" src="/Physusp/js/chart.js"></script>
	</body>
</html>