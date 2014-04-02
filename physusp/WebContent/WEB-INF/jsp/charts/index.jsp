<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>PhysUSP</title>
		<script type="text/javascript" src="/Physusp/js/lib/highcharts.js"></script>
		<script type="text/javascript" src="/Physusp/js/chart.js"></script>
	</head>
	<body>
		<form action="/Physusp/charts/index" method="GET">
			<input type="text" name="valorx1" />
			<input type="text" name="valorx2" />
			<input type="text" name="valory1" />
			<input type="text" name="valory2" />
			<input type="submit" value="Enviar" />
		</form>
		<div id="results">
			<p><%= request.getParameter("valorx1") %></p>
			<p><%= request.getParameter("valorx2") %></p>
			<p><%= request.getParameter("valory1") %></p>
			<p><%= request.getParameter("valory2") %></p>
		</div>
	</body>
</html>