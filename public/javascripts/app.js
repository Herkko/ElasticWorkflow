

var App;
var RaphaelElement;

var ActivityElements = new ActivityList;
var StartElements = new StartList;
var EndElements = new EndList;
var SwimlaneElements = new SwimlaneList;


window.onload = function() {

    connections = [];

    RaphaelElement = Raphael(10, 10, "100%", "100%");
    var swimlane2 = RaphaelElement.rect(20, 20, 700, 230, 1);


    App = new AppView;


};

