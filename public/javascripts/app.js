

var App;
var RaphaelElement;


var ActivityElements = new ActivityList;
var StartElements = new StartList;
var EndElements = new EndList;
var SwimlaneElements = new SwimlaneList;
var GatewayElements = new GatewayList;
var RelationElements = new RelationList;


var AllElements = new AllElementsList;


window.onload = function() {

    connections = [];

    RaphaelElement = Raphael(10, 120, "100%", "100%");

    App = new AppView;


};


function updateall(){

	for (var i=0; i<ActivityElements.length; i++) {
		ActivityElements.at(i).save();

	}
	for (var i=0; i<StartElements.length; i++) {
		StartElements.at(i).save();
	}
	for (var i=0; i<EndElements.length; i++) {
		EndElements.at(i).save();
	}
	for (var i=0; i<GatewayElements.length; i++) {
		GatewayElements.at(i).save();
	}
	
    alert("Elements hopefully saved, now refresh page.");
} 
