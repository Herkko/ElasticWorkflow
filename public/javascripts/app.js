

var App;
var RaphaelElement;

var ActivityElements = new ActivityList;
var StartElements = new StartList;
var EndElements = new EndList;
var SwimlaneElements = new SwimlaneList;
var GatewayElements = new GatewayList;
var RelationElements = new RelationList;

//all elementsiä ei varmaan tarvitse kollektionina
var AllElements = new AllElementsList;




window.onload = function() {

    connections = [];

    RaphaelElement = Raphael(10, 100, "100%", "100%");

    App = new AppView;


};


function updateall(){
<<<<<<< HEAD
    

	for (var i=0; i<ActivityElements.length; i++){
		var testi = ActivityElements.pop(i);
		var testinSisaltamaRaphaelElementti = testi.get("element");
		var xArvo = testinSisaltamaRaphaelElementti.attr("x");
		var yArvo = testinSisaltamaRaphaelElementti.attr("y");
		testi.set({cx: xArvo, cy: yArvo});
		alert("x " +xArvo+ " y " +yArvo+ " cx " + testi.get("cx") + " cy " + testi.get("cy"))
		testi.collection = ActivityElements;
		testi.save();
=======
	for (var i=0; i<ActivityElements.length; i++) {
		ActivityElements.at(i).save();
>>>>>>> b6f1e5fc2761bd31eeac682d3bcc6ae8b9844942
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
