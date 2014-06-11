var ResultRow = function(row, data){
	this.row = row;
	this.data = data;
	
	this.showInColumn = function (i, value) {
		row.find("td:nth-of-type(" + i + ")").text(parseFloat(value).toFixed(2));
	};
	
	this.showResult = function() {
		row.removeClass("hide");
		this.showInColumn(2, data.Kcal);
		this.showInColumn(3, data.KJ);
		this.showInColumn(4, data.LO2);
	};
};