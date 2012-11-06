

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

    RaphaelElement = Raphael(10, 100, "100%", "100%");

    App = new AppView;


};


function updateall(){
    
	for (var i=0; i<ActivityElements.length; i++){
		var testi = ActivityElements.pop(i);
		var testinSisaltamaRaphaelElementti = testi.get("element");
		var xArvo = testinSisaltamaRaphaelElementti.attr("x");
		var yArvo = testinSisaltamaRaphaelElementti.attr("y");
		testi.set({cx: xArvo, cy: yArvo});
		alert("x " +xArvo+ " y " +yArvo+ " cx " + testi.get("cx") + " cy " + testi.get("cy"))
		testi.collection = ActivityElements;
		testi.save();
	}
   
    
    
} 
