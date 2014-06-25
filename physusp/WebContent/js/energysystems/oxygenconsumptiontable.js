/*
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
*/

var OxygenConsumptionTable = function(table) {
	this.table = table;
	
	this.getDataAsString = function() {
		var toSend = "";
		var rows = table.handsontable('getData');
		for(var i = 0; i < rows.length - 1; i++)
			if(rows[i][0] != null) {
				toSend += rows[i][0] + "," + rows[i][1] + "\n";
			}
		return table.data("field") + "=" + toSend;
	};
};
