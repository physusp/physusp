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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width" />
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
	<link rel="stylesheet" href="<c:url value="/css/lib/handsontable.full.css"/>" />
	<link rel="stylesheet" href="<c:url value="/css/energysystemscontributions.css"/>" />
	<title>GEDAE-LaB - Energy Systems Contributions</title>
</head>
<body>

	<div id="top">
		<div class="container">
			<a href="<c:url value="/"/>"><img class="img-responsive" src="<c:url value="/img/logo.jpg"/>" alt="logo"/></a>
		</div>
	</div>

	<h1 class="text-center">Energy Systems Contributions</h1>
	<form id="data" class="form-horizontal" action="<c:url value="/esc/calculate"/>">
		<div class="container">
			<ul id="containerTabs" class="nav nav-tabs">
				<li id="tabOptions" class="active show"><a href="#options">Options</a></li>
				<li id="tab_aerobic"><a href="#aerobic">Aerobic</a></li>
				<li id="tab_anaerobicAlactic"><a href="#anaerobicAlactic">Anaerobic Alactic</a></li>
				<li id="tab_anaerobicLactic"><a href="#anaerobicLactic">Anaerobic Lactic</a></li>
				<li id="tabResults"><a href="#results">Results</a></li>
			</ul>

			<div id="containerPanes" class="tab-content">
				<div id="options" class="tab-pane active">
					<div class="col-sm-12">
						<p class="information">What do you wish to calculate?</p>
						<div class="checkbox">
							<label class="control-label">
								<input type="checkbox"
									id="parameters.calculateAerobic"
									name="parameters.calculateAerobic" value="true" 
									data-tab="aerobic" checked />
								Aerobic System
							</label>
						</div>
						<div class="checkbox">
							<label class="control-label">
								<input type="checkbox"
									id="parameters.calculateAnaerobicAlactic"
									name="parameters.calculateAnaerobicAlactic" value="true"
									data-tab="anaerobicAlactic" checked />
								Anaerobic Alactic System
							</label>
						</div>
						<div class="checkbox">
							<label class="control-label">
								<input type="checkbox"
									id="parameters.calculateAnaerobicLactic"
									name="parameters.calculateAnaerobicLactic" value="true"
									data-tab="anaerobicLactic" checked />
								Anaerobic Lactic System
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
							<div id="aerobicRestTable" class="handsontable-container">
								<div id="oxygenConsumptionRest" data-field="restOxygenParameters.oxygenConsumptionRest"></div>
								<button id="btnOxygenConsumptionRestPlot" type="button" class="btn btn-default" data-plot="oxygenConsumptionRestPlot" data-table="oxygenConsumptionRest">Plot</button>
								<button id="btnOxygenConsumptionRestClear" type="button" class="btn btn-default" data-table="oxygenConsumptionRest">Clear</button>
								<div id="oxygenConsumptionRestPlot"></div>
								
								<input id="oxygenConsumptionRestError" name="oxygenConsumptionRestError" type="hidden" value="Table is empty." 
									data-table="oxygenConsumptionRest" />
							</div>
							<div id="aerobicRestAvg">
								<label class="control-label">Rest VO<sub>2</sub> <strong>(ml/min)</strong></label>
								<input type="text" id="restOxygenParameters.fixedValue" name="restOxygenParameters.fixedValue" class="form-control" />
							</div>
						</div>
						<div class="col-sm-6">
							<div id="aerobicTable" class="handsontable-container"> 
								<div id="oxygenConsumptionDuringExercise" data-field="aerobicParameters.oxygenConsumptionDuringExercise"></div>
								<button id="btnOxygenConsumptionDuringExercisePlot" type="button" class="btn btn-default" data-plot="oxygenConsumptionDuringExercisePlot" data-table="oxygenConsumptionDuringExercise">Plot</button>
								<button id="btnOxygenConsumptionDuringExerciseClear" type="button" class="btn btn-default" data-table="oxygenConsumptionDuringExercise">Clear</button>
								<div id="oxygenConsumptionDuringExercisePlot"></div>
								
								<input id="oxygenConsumptionDuringExerciseError" name="oxygenConsumptionDuringExerciseError" type="hidden" value="Table is empty." 
									data-table="oxygenConsumptionDuringExercise"/>
							</div>
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
	
							<div class="col-sm-12">
								<div id="TimeDelayDiv" class="form-group" style="display: none">
									<label class="control-label">Time delay <strong>(s)</strong></label>
									<div>
										<input type="text"
											id="anaerobicAlacticParameters.timeDelay"
											name="anaerobicAlacticParameters.timeDelay"
											class="form-control" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="anaerobicAlacticSecondRow" class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-12">
									<div id="oxygenConsumptionPostExercise" data-field="anaerobicAlacticParameters.oxygenConsumptionPostExercise"></div>
									<input id="oxygenConsumptionPostExerciseError" name="oxygenConsumptionPostExerciseError" type="hidden" value="Table is empty." 
										data-table="oxygenConsumptionPostExercise"/>
									<button id="btnOxygenConsumptionPostExercisePlot" type="button" class="btn btn-default" data-plot="oxygenConsumptionPostExercisePlot" data-table="oxygenConsumptionPostExercise">Plot</button>
									<button id="btnOxygenConsumptionPostExerciseClear" type="button" class="btn btn-default" data-table="oxygenConsumptionPostExercise">Clear</button>
									<div id="oxygenConsumptionPostExercisePlot"></div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div id="anaerobicLactic" class="tab-pane col-sm-12">
					<div class="col-sm-6">
						<div class="form-group">
							<label class="control-label">Rest Blood Lactate <strong>(mmol/L)</strong></label>
							<input type="text" id="anaerobicLacticParameters.restLactateConcentration" name="anaerobicLacticParameters.restLactateConcentration" class="form-control" />
						</div>
						<div class="form-group">
							<label class="control-label">Peak Blood Lactate <strong>(mmol/L)</strong></label>
							<input type="text" id="anaerobicLacticParameters.peakLactateConcentration" name="anaerobicLacticParameters.peakLactateConcentration" class="form-control" />
						</div>
						<div class="form-group">
							<label class="control-label">Body Mass <strong>(kg)</strong></label>
							<input type="text" id="anaerobicLacticParameters.weight" name="anaerobicLacticParameters.weight" class="form-control" />
						</div>
					</div>
				</div>

				<div id="results" class="tab-pane">
					<div id="chart" class="col-sm-6">
						<div id="containerChart"></div>
					</div>
					<div class="col-sm-6">
						<div id="other-units">
							<table id="resultsTable" class="table">
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
									<tr id="totalsRow">
										<td>Total</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<button id="btnAdvancedResults" type="button" class="btn btn-default">Advanced</button>
						<a id="btnExportSystems" class="btn btn-default" download="export.csv">Export</a>
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
				<a id="btnNewAnalysis" href="" onclick="location.reload(true)" class="btn btn-default btn-lg">New Analysis</a>
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
		src="<c:url value="/js/lib/handsontable.full.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/jquery.validate.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/encoding.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/table2csv.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/lib/excellentexport.js"/>"></script>
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
	<script type="text/javascript"
		src="<c:url value="/js/energysystems/plot.js"/>"></script>
</body>
</html>
