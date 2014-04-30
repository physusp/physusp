<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="<c:url value="/css/lib/jquery.handsontable.full.css"/>"/>
		<link rel="stylesheet" href="<c:url value="/css/energysystemscontributions.css"/>"/>
		<title>PhysUSP - Energy Systems Contributions</title>
	</head>
	<body>
		<h1 class="text-center">Energy Systems Contributions</h1>
		<form id="data" class="form-horizontal" action="<c:url value="/esc/calculate"/>">
			<div class="container">
				<ul id="containerTabs" class="nav nav-tabs">
					<li><a href="#options" data-toggle="tab">Options</a></li>
					<li><a href="#aerobic" data-toggle="tab">Aerobic</a></li>
					<li class="active"><a href="#anaerobicLactic" data-toggle="tab">Anaerobic Lactic</a></li>
					<li><a href="#anaerobicAlactic" data-toggle="tab">Anaerobic Alactic</a></li>
					<li id="tabResults"><a href="#results" data-toggle="tab">Results</a></li>
				</ul>
				
				<div id="containerPanes" class="tab-content">
					<div id="options" class="tab-pane">
						<input type="checkbox" name="parameters.calculateAerobic" value="true" />
						<input type="checkbox" name="parameters.calculateAnaerobicLactic" value="true" checked />
						<input type="checkbox" name="parameters.calculateAnaerobicAlactic" value="true" />
					</div>
					
					<div id="aerobic" class="tab-pane">
						<div class="form-group">
							<label class="col-md-3 control-label">Rest</label>
							<div id="oxygenConsumptionRest" class="col-md-9"></div>
							<!-- opções adicionais aqui -->
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">Exercise</label>
							<div id="oxygenConsumptionDuringExercise" class="col-md-9"></div>
						</div>
					</div>
					
					<div id="anaerobicLactic" class="tab-pane active">
						<div class="col-md-3"></div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-md-4 control-label">Body Mass <strong>(kg)</strong></label>
								<div class="col-md-8">
									<input type="text" name="parameters.weight" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Rest <strong>(mmol/L)</strong></label> 
								<div class="col-md-8">
									<input type="text" name="parameters.restLactateConcentration" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Peak <strong>(mmol/L)</strong></label>
								<div class="col-md-8">
									<input type="text" name="parameters.maxLactateConcentration" class="form-control" />
								</div>
							</div>
						</div>
						<div class="col-md-3"></div>
					</div>
					
					<div id="anaerobicAlactic" class="tab-pane">
						<div class="form-group">
							<label class="col-md-3 control-label">Recovery</label>
							<div id="oxygenConsumptionPostExercise" class="col-md-9"></div>
						</div>
						
						<!-- opções avançadas -->
						<div class="form-group">
							<label class="col-md-4 control-label">Time delay <strong>(s)</strong></label>
							<div class="col-md-8">
								<input type="text" name="parameters.timeDelayPost" class="form-control" />
							</div>
						</div>
						<label class="control-label">
							<input type="radio" name="parameters.exponentialType" value="1" checked />
							Monoexponential
						</label>
						<label class="control-label">
							<input type="radio" name="parameters.exponentialType" value="2" />
							Biexponential
						</label>
					</div>
					
					<div id="results" class="tab-pane">
						<button id="exp-jpg" type="button" class="btn btn-default btn-lg" onclick="exportChart('image/jpeg')">
							Export to JPG
						</button>
						<button id="exp-png" type="button" class="btn btn-default btn-lg" onclick="exportChart('image/png')">
							Export to PNG
						</button>
						<button id="exp-svg" type="button" class="btn btn-default btn-lg" onclick="exportChart('image/svg+xml')">
							Export to SVG
						</button>
					</div>
				</div>
				
				<button type="submit" class="btn btn-primary btn-lg">Calculate</button>
			</div>
		</form>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<c:url value="/js/lib/highcharts.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/lib/exporting.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/energysystemscontributions.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/lib/jquery.handsontable.full.js"/>"></script>
	</body>
</html>