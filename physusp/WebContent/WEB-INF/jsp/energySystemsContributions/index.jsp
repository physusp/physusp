<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
<link rel="stylesheet" href="<c:url value="/css/lib/jquery.handsontable.full.css"/>" />
<link rel="stylesheet" href="<c:url value="/css/energysystemscontributions.css"/>" />
<title>PhysUSP - Energy Systems Contributions</title>
</head>
<body>
	<h1 class="text-center">Energy Systems Contributions</h1>
	<form id="data" class="form-horizontal"
		action="<c:url value="/esc/calculate"/>">
		<div class="container">
			<ul id="containerTabs" class="nav nav-tabs">
				<li class="active"><a href="#options" data-toggle="tab">Options</a></li>
				<li id="tab_aerobic"><a href="#aerobic" data-toggle="tab">Aerobic</a></li>
				<li id="tab_anaerobicLactic"><a href="#anaerobicLactic" data-toggle="tab">Anaerobic
						Lactic</a></li>
				<li id="tab_anaerobicAlactic"><a href="#anaerobicAlactic" data-toggle="tab">Anaerobic
						Alactic</a></li>
				<li id="tabResults"><a href="#results" data-toggle="tab">Results</a></li>
			</ul>

			<div id="containerPanes" class="tab-content">
				<div id="options" class="tab-pane col-sm-12 active">
					<p class="information">What do you wish to calculate?</p>
					<div class="checkbox">
						<label class="control-label"> <input type="checkbox"
							name="parameters.calculateAerobic" value="true" 
							data-tab="aerobic" checked /> Aerobic System
						</label>
					</div>
					<div class="checkbox">
						<label class="control-label"> <input type="checkbox"
							name="parameters.calculateAnaerobicLactic" value="true"
							data-tab="anaerobicLactic" checked /> Anaerobic Lactic System
						</label>
					</div>
					<div class="checkbox">
						<label class="control-label"> <input type="checkbox"
							name="parameters.calculateAnaerobicAlactic" value="true"
							data-tab="anaerobicAlactic" checked /> Anaerobic Alactic System
						</label>
					</div>
					<div id="validateSystems">
						<label class="control-label"></label>
					</div>
					<button id="btnContinue" type="button" class="btn btn-lg btn-primary">Continue</button>
				</div>

				<div id="aerobic" class="tab-pane">
					<div class="row">
						<div class="col-md-6">
							<label>How would you like to determine the rest O<sub>2</sub> consumption?</label>
							<div class="radio">
								<label class="control-label">
									<input type="radio" name="restOxygenParameters.calculateMethod" value="ignore" checked />
									I want to ignore the rest O<sub>2</sub> consumption
								</label>
							</div>
							<div class="radio">
								<label class="control-label">
									<input type="radio" name="restOxygenParameters.calculateMethod" value="series" checked />
									I have a table with values
								</label>
							</div>
							<div class="radio">
								<label class="control-label">
									<input type="radio" name="restOxygenParameters.calculateMethod" value="fixed" />
									I want to use a fixed value
								</label>
							</div>
							
							<div class="form-group col-md-12" id='aerobicRestTable'>
								<div id="oxygenConsumptionRest"></div>
								<!-- opções adicionais aqui -->
							</div>
							<div class="form-group col-md-12" style="display:none" id='aerobicRestAvg'>
								<label class="control-label">Rest O<sub>2</sub> consumption <strong>(ml/min)</strong></label>
								<input type="text" name="restOxygenParameters.fixedValue" class="form-control" />
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group col-md-12 ">
								<label class="control-label">O<sub>2</sub> consumption during exercise</label>
								<div id="oxygenConsumptionDuringExercise"></div>
							</div>
						</div>
					</div>
				</div>


				<div id="anaerobicLactic" class="tab-pane col-sm-12">
					<div class="col-sm-3"></div>
					<div class="col-sm-6">
						<div class="form-group">
							<label class="col-md-4 control-label">Body Mass <strong>(kg)</strong></label>
							<div class="col-md-8">
								<input type="text" name="anaerobicLacticParameters.weight" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Rest <strong>(mmol/L)</strong></label>
							<div class="col-md-8">
								<input type="text" name="anaerobicLacticParameters.restLactateConcentration" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Peak <strong>(mmol/L)</strong></label>
							<div class="col-md-8">
								<input type="text" name="anaerobicLacticParameters.maxLactateConcentration" class="form-control" />
							</div>
						</div>
					</div>
					<div class="col-sm-3"></div>
				</div>


				<div id="anaerobicLactic" class="tab-pane col-sm-12">
					<div class="col-sm-3"></div>
					<div class="col-sm-6">
						<div class="form-group">
							<label class="col-md-4 control-label">Body Mass <strong>(kg)</strong></label>
							<div class="col-md-8">
								<input type="text" name="anaerobicLacticParameters.weight"
									class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Rest <strong>(mmol/L)</strong></label>
							<div class="col-md-8">
								<input type="text"
									name="anaerobicLacticParameters.restLactateConcentration"
									class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Peak <strong>(mmol/L)</strong></label>
							<div class="col-md-8">
								<input type="text"
									name="anaerobicLacticParameters.maxLactateConcentration"
									class="form-control" />
							</div>
						</div>
					</div>
					<div class="col-sm-3"></div>
				</div>

				<div id="anaerobicAlactic" class="tab-pane">
					<div class="col-md-12">
						<label class="control-label">Recovery</label>
					</div>
					<div class="col-md-5">
						<div class="form-group">
							<div id="oxygenConsumptionPostExercise" class="col-md-12"></div>
						</div>
					</div>

					<div class="col-md-6">
						<div>
							<label class="control-label"> <input type="radio"
								name="anaerobicAlacticParameters.exponentialType" value="1"
								checked /> Monoexponential
							</label>
						</div>
						<div>
							<label class="control-label"> <input type="radio"
								name="anaerobicAlacticParameters.exponentialType" value="2" />
								Biexponential
							</label>
						</div>

						<div class="checkbox">
							<label> <input
								id="useTimeDelay" type="checkbox" name="anaerobicAlacticParameters.useTimeDelay" value="true"> Use custom time delay
							</label>
						</div>

						<!-- opções avançadas -->
						<div id="TimeDelayDiv" class="form-group" style="display: none">
							<label class="col-md-4 control-label">Time delay <strong>(s)</strong></label>
							<div class="col-md-4">
								<input type="text"
									name="anaerobicAlacticParameters.timeDelayPost"
									class="form-control" />
							</div>
						</div>

					</div>
				</div>


				<div id="results" class="tab-pane col-sm-12">
					<button id="exp-jpg" type="button" class="btn btn-default btn-lg"
						onclick="exportChart('image/jpeg')">Export to JPG</button>
					<button id="exp-png" type="button" class="btn btn-default btn-lg"
						onclick="exportChart('image/png')">Export to PNG</button>
					<button id="exp-svg" type="button" class="btn btn-default btn-lg"
						onclick="exportChart('image/svg+xml')">Export to SVG</button>
				</div>
			</div>

			<button id="btnCalculate" type="submit"
				class="btn btn-primary btn-lg">Calculate</button>
		</div>
	</form>
	<script type="text/javascript"
		src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script type="text/javascript"
		src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/highcharts.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/exporting.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/energysystemscontributions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/jquery.handsontable.full.js"/>"></script>
</body>
</html>