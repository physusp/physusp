<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		action="<c:url value="/calculate"/>">
		<div class="container">
			<ul id="containerTabs" class="nav nav-tabs">
				<li id="tabOptions" class="active"><a href="#options" data-toggle="tab">Options</a></li>
				<li id="tab_aerobic"><a href="#aerobic" data-toggle="tab">Aerobic</a></li>
				<li id="tab_anaerobicLactic"><a href="#anaerobicLactic" data-toggle="tab">Anaerobic
						Lactic</a></li>
				<li id="tab_anaerobicAlactic"><a href="#anaerobicAlactic" data-toggle="tab">Anaerobic
						Alactic</a></li>
				<li id="tabResults"><a href="#results" data-toggle="tab">Results</a></li>
			</ul>

			<div id="containerPanes" class="tab-content">
				<div id="options" class="tab-pane active">
					<div class="col-sm-12">
						<p class="information">What do you wish to calculate?</p>
						<div class="checkbox">
							<label class="control-label"> <input type="checkbox"
								id="parameters.calculateAerobic"
								name="parameters.calculateAerobic" value="true" 
								data-tab="aerobic" /> Aerobic System
							</label>
						</div>
						<div class="checkbox">
							<label class="control-label"> <input type="checkbox"
								id="parameters.calculateAnaerobicLactic"
								name="parameters.calculateAnaerobicLactic" value="true"
								data-tab="anaerobicLactic" /> Anaerobic Lactic System
							</label>
						</div>
						<div class="checkbox">
							<label class="control-label"> <input type="checkbox"
								id="parameters.calculateAnaerobicAlactic"
								name="parameters.calculateAnaerobicAlactic" value="true"
								data-tab="anaerobicAlactic" /> Anaerobic Alactic System
							</label>
						</div>
						<div id="validateSystems">
							<label class="control-label"></label>
						</div>
					</div>
					<button id="btnContinue" type="button" class="btn btn-lg btn-primary">Continue</button>
				</div>

				<div id="aerobic" class="tab-pane">
					<div id="restData" class="col-sm-6">
						<label class="title-area">Rest VO<sub>2</sub></label>
						<div class="radio">
							<label class="control-label">
								<input type="radio" name="restOxygenParameters.calculateMethod" value="ignore" />
								Ignore rest VO<sub>2</sub>
							</label>
						</div>
						<div class="radio">
							<label class="control-label">
								<input type="radio" name="restOxygenParameters.calculateMethod" value="series" checked />
								Measured values
							</label>
						</div>
						<div class="radio">
							<label class="control-label">
								<input type="radio" name="restOxygenParameters.calculateMethod" value="fixed" />
								Fixed value
							</label>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group" id='aerobicRestTable'>
								<div id="oxygenConsumptionRest"></div>
							</div>
							<div class="form-group" id="aerobicRestAvg">
								<label class="control-label">Rest VO<sub>2</sub> <strong>(ml/min)</strong></label>
								<input type="text" id="restOxygenParameters.fixedValue" name="restOxygenParameters.fixedValue" class="form-control" />
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<label class="title-area">Exercise VO<sub>2</sub></label>
						<div id="oxygenConsumptionDuringExercise"></div>
					</div>
				</div>

				<div id="anaerobicLactic" class="tab-pane col-sm-12">
					<div class="col-sm-6">
						<div class="form-group">
							<label class="control-label">Body Mass <strong>(kg)</strong></label>
							<input type="text" id="anaerobicLacticParameters.weight" name="anaerobicLacticParameters.weight" class="form-control" />
						</div>
						<div class="form-group">
							<label class="control-label">Peak <strong>(mmol/L)</strong></label>
							<input type="text" id="anaerobicLacticParameters.maxLactateConcentration" name="anaerobicLacticParameters.maxLactateConcentration" class="form-control" />
						</div>
						<div class="form-group">
							<label class="control-label">Rest <strong>(mmol/L)</strong></label>
							<input type="text" id="anaerobicLacticParameters.restLactateConcentration" name="anaerobicLacticParameters.restLactateConcentration" class="form-control" />
						</div>
					</div>
				</div>

				<div id="anaerobicAlactic" class="tab-pane">
					<div class="col-sm-6">
						<label class="title-area">Recovery</label>
						<div>
							<label class="control-label"> <input type="radio" id="monoexponential"
								name="anaerobicAlacticParameters.exponentialType" value="1"
								checked /> Monoexponential

							</label>
						</div>
						<div>
							<label class="control-label"> <input type="radio" id="biexponential"
								name="anaerobicAlacticParameters.exponentialType" value="2" />
								Biexponential
							</label>
						</div>

						<div class="checkbox">
							<label> <input
								id="useTimeDelay" type="checkbox" name="anaerobicAlacticParameters.useTimeDelay" value="true"> Use custom time delay
							</label>
						</div>

						<div class="col-sm-12">
							<div id="TimeDelayDiv" class="form-group" style="display: none">
								<label class="control-label">Time delay <strong>(s)</strong></label>
								<div>
									<input type="text"
										id="anaerobicAlacticParameters.timeDelayPost"
										name="anaerobicAlacticParameters.timeDelayPost"
										class="form-control" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div id="oxygenConsumptionPostExercise" class="col-sm-12"></div>
						</div>
					</div>
				</div>

				<div id="results" class="tab-pane">
					<div id="chart" class="col-sm-6">
						<div id="containerChart"></div>
						<div class="col-sm-12 text-center">
							<button id="exp-jpg" type="button" class="exportButton btn btn-default btn-lg"
								data-format="image/jpeg">Export to JPG</button>
							<button id="exp-png" type="button" class="exportButton btn btn-default btn-lg"
								data-format="image/png">Export to PNG</button>
							<button id="exp-svg" type="button" class="exportButton btn btn-default btn-lg"
								data-format="image/svg+xml">Export to SVG</button>
						</div>
					</div>
					<div class="col-sm-6">
						<div id="other-units">
							<table class="table">
								<thead>
									<tr>
										<th>Systems</th>
										<th>Kcal</th>
										<th>kJ</th>
										<th>LO<sub>2</sub></th>
									</tr>
								</thead>
								<tbody>
									<tr id="aerobicRow" class="hide">
										<td>Aerobic</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr id="anaerobicLacticRow" class="hide">
										<td>Anaerobic Lactic</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr id="anaerobicAlacticRow" class="hide">
										<td>Anaerobic Alactic</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<button id="btnAdvancedResults" type="button" class="btn btn-default">Advanced</button>
						<div id="advancedResults">
						</div>
					</div>
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
	<script type="text/javascript"
		src="<c:url value="/js/lib/jquery.validate.min.js"/>"></script>
</body>
</html>