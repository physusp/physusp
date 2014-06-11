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
	<form id="data" class="form-horizontal" action="<c:url value="/calculate"/>">
		<div class="container">
			<ul id="containerTabs" class="nav nav-tabs">
				<li id="tabOptions" class="active show"><a href="#options">Options</a></li>
				<li id="tab_aerobic"><a href="#aerobic">Aerobic</a></li>
				<li id="tab_anaerobicLactic"><a href="#anaerobicLactic">Anaerobic Lactic</a></li>
				<li id="tab_anaerobicAlactic"><a href="#anaerobicAlactic">Anaerobic Alactic</a></li>
				<li id="tabResults"><a href="#results">Results</a></li>
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
				</div>

				<div id="aerobic" class="tab-pane">
					<div id="aerobicFirstRow" class="row">
						<div id="restOptions" class="col-sm-6">
							<label class="title-area">Rest VO<sub>2</sub></label>
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
							<div class="radio">
								<label class="control-label">
									<input type="radio" name="restOxygenParameters.calculateMethod" value="ignore" />
									Ignore rest VO<sub>2</sub>
								</label>
							</div>
						</div>
						<div class="col-sm-6">
							<label class="title-area">Exercise VO<sub>2</sub></label>
						</div>
					</div>
					
					<div id="aerobicSecondRow" class="row">
						<div id="restData" class="col-sm-6">
							<div id="aerobicRestTable">
								<div id="oxygenConsumptionRest" data-field="restOxygenParameters.oxygenConsumptionRest"></div>
								<input id="oxygenConsumptionRestError" name="oxygenConsumptionRestError" type="hidden" value="Table is empty." 
									data-table="oxygenConsumptionRest" />
							</div>
							<div id="aerobicRestAvg">
								<label class="control-label">Rest VO<sub>2</sub> <strong>(ml/min)</strong></label>
								<input type="text" id="restOxygenParameters.fixedValue" name="restOxygenParameters.fixedValue" class="form-control" />
							</div>
						</div>
						<div class="col-sm-6">
							<div id="oxygenConsumptionDuringExercise" data-field="aerobicParameters.oxygenConsumptionDuringExercise"></div>
							<input id="oxygenConsumptionDuringExerciseError" name="oxygenConsumptionDuringExerciseError" type="hidden" value="Table is empty." 
								data-table="oxygenConsumptionDuringExercise"/>
						</div>
					</div>
				</div>

				<div id="anaerobicLactic" class="tab-pane col-sm-12">
					<div class="col-sm-6">
						<div class="form-group">
							<label class="control-label">Peak Blood Lactate <strong>(mmol/L)</strong></label>
							<input type="text" id="anaerobicLacticParameters.maxLactateConcentration" name="anaerobicLacticParameters.maxLactateConcentration" class="form-control" />
						</div>
						<div class="form-group">
							<label class="control-label">Rest Blood Lactate <strong>(mmol/L)</strong></label>
							<input type="text" id="anaerobicLacticParameters.restLactateConcentration" name="anaerobicLacticParameters.restLactateConcentration" class="form-control" />
						</div>
						<div class="form-group">
							<label class="control-label">Body Mass <strong>(kg)</strong></label>
							<input type="text" id="anaerobicLacticParameters.weight" name="anaerobicLacticParameters.weight" class="form-control" />
						</div>
					</div>
				</div>

				<div id="anaerobicAlactic" class="tab-pane">
					<div id="anaerobicAlacticFirstRow" class="row">
						<div class="col-sm-6">
							<label class="title-area">Recovery VO<sub>2</sub></label>
							<div>
								<label class="control-label">
									<input type="radio" id="monoexponential" name="anaerobicAlacticParameters.exponentialType" value="1" checked />
									Monoexponential
								</label>
							</div>
							<div>
								<label class="control-label">
									<input type="radio" id="biexponential" name="anaerobicAlacticParameters.exponentialType" value="2" />
									Biexponential
								</label>
							</div>
	
							<div class="checkbox">
								<label>
									<input id="useTimeDelay" type="checkbox" name="anaerobicAlacticParameters.useTimeDelay" value="true" />
									Use custom time delay
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
						</div>
					</div>
					<div id="anaerobicAlacticSecondRow" class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<div id="oxygenConsumptionPostExercise" data-field="anaerobicAlacticParameters.oxygenConsumptionPostExercise" class="col-sm-12"></div>
								<input id="oxygenConsumptionPostExerciseError" name="oxygenConsumptionPostExerciseError" type="hidden" value="Table is empty." 
									data-table="oxygenConsumptionPostExercise"/>
							</div>
						</div>
					</div>
				</div>

				<div id="results" class="tab-pane">
					<div id="chart" class="col-sm-6">
						<div id="containerChart"></div>
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
						<div id="advancedResults"></div>
					</div>
				</div>
			</div>
			
			<div id="containerButtons">
				<button id="btnPrevious" type="button" class="btn btn-primary btn-lg">Previous</button>
				<button id="btnNext" type="button" class="btn btn-primary btn-lg">Next</button>
				<div class="dropdown">
					<button id="exportDropdown" type="button" data-toggle="dropdown" class="btn btn-default btn-lg">
						Export <span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li>
							<a href="#" class="exportButton" data-format="image/jpeg">To JPG</a>
						</li>
						<li>
							<a href="#" class="exportButton" data-format="image/png">To PNG</a>
						</li>
						<li>
							<a href="#" class="exportButton" data-format="image/svg+xml">To SVG</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</form>
	<div id="modal-error" class="modal">
		<div class="modal-dialog">
		    <div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">The server returned an error.</h4>
				</div>
				<div class="modal-body">
					<p id="error-content"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
		    </div>
  		</div>
	</div>
	<script type="text/javascript"
		src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script type="text/javascript"
		src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/highcharts.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/exporting.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/jquery.handsontable.full.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/jquery.validate.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/energysystems/form.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/energysystems/advancedresults.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/energysystems/oxygenconsumptiontable.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/energysystems/resultrow.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/energysystems/main.js"/>"></script>
</body>
</html>