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