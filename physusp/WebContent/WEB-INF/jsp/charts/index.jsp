<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="<c:url value="/css/lib/jquery.handsontable.full.css"/>"/>
		<title>PhysUSP</title>
	</head>
	<body>
		<div class="container">
			<form id="data" class="form-horizontal" action="<c:url value="/charts/calculate"/>">
				<div class="row">
					<div class="col-md-6">
						<h1>Energy Systems Contributions</h1>
						<p>Fill out the fields below with... </p>
						<div class="form-group">
							<label class="col-md-7 control-label">Rest lactate concentration (mmol/L)</label> 
							<div class="col-md-5">
								<input type="text" name="parameters.restLactateConcentration" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-7 control-label">Maximum lactate concentration (mmol/L)</label>
							<div class="col-md-5">
								<input type="text" name="parameters.maxLactateConcentration" class="form-control col-md-6" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-7 control-label">Subject mass (kg)</label>
							<div class="col-md-5">
								<input type="text" name="parameters.weight" class="form-control col-md-6" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div id="results"></div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<label>Rest oxygen consumption</label>
						<div id="oxygenConsumptionRest"></div>
					</div>
					<div class="col-md-4">
						<label>Exercise oxygen consumption</label>
						<div id="oxygenConsumption"></div>
					</div>
					<div class="col-md-4">
						<label>Post-Exercise oxygen consumption</label>
						<div id="oxygenConsumptionPost"></div>
					</div>
				</div>
				<div class="row" style="margin-top: 15px">
					<div class="col-md-12">
						<button type="submit" class="btn btn-primary btn-lg pull-right">Create chart</button>
					</div>
				</div>
			</form>
		</div>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script type="text/javascript" src="<c:url value="/js/lib/highcharts.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/chart.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/lib/jquery.handsontable.full.js"/>"></script>
	</body>
</html>