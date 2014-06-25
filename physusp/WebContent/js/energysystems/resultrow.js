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

var ResultRow = function(row, data){
	this.row = row;
	this.data = data;
	
	this.showInColumn = function (i, value) {
		row.find("td:nth-of-type(" + i + ")").text(parseFloat(value).toFixed(1));
	};
	
	this.showResult = function() {
		row.removeClass("hide");
		this.showInColumn(2, data.Kcal);
		this.showInColumn(3, data.KJ);
		this.showInColumn(4, data.LO2);
	};
	
	this.hide = function() {
		this.row.addClass("hide");
	};
};
