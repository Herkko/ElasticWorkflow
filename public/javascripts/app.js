

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
    //var uusi = new activity({"modelProcessId":1,"elementTypeId":4,"relationId":4,"value":"Activity","size":0,"cx":170,"cy":110});
	//var uusi = new activity({cx: 500, cy: 250, id: 4});
    //var uusi = ActivityElements.at(1);
    
    //var testjson = uusi.toJSON();
    
    //alert(testjson);
	//uusi.save();
    ActivityElements.at(1).save();
    alert("testi");
	/*
	for (var i=0; i<ActivityElements.length; i++){
		var testi = ActivityElements.pop(i);
		var testinSisaltamaRaphaelElementti = testi.get("element");
		var xArvo = testinSisaltamaRaphaelElementti.attr("x");
		var yArvo = testinSisaltamaRaphaelElementti.attr("y");
		//testi.set({cx: xArvo, cy: yArvo});
		//alert("x " +xArvo+ " y " +yArvo+ " cx " + testi.get("cx") + " cy " + testi.get("cy"))
		//testi.collection = ActivityElements;
		testi.save({cx: xArvo, cy: yArvo});
		alert("ei vittu toimi!");
	}
   	*/
    
    
} 
