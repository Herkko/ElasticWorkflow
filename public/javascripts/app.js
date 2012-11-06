

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

    RaphaelElement = Raphael(10, 10, "100%", "100%");

    App = new AppView;


};

